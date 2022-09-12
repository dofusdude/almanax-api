package de.dofusdu.memory;

import de.dofusdu.dto.BonusTypeMapDTOV2;
import de.dofusdu.dto.OfferingDTOV2;
import de.dofusdu.gateway.BonusTypeRepository;
import de.dofusdu.gateway.OfferingRepository;
import de.dofusdu.util.LanguageHelper;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;

@ApplicationScoped
public class Populate {

    @Inject
    @ConfigProperty(name = "redis.host")
    String redisHost;

    @Inject
    @ConfigProperty(name = "redis.password")
    String redisPassword;

    @Inject
    @ConfigProperty(name = "almanax.first.date")
    String firstDate;

    @Inject
    @ConfigProperty(name = "almanax.max.ahead")
    int maxAhead;

    private OfferingRepository offeringRepository;

    private BonusTypeRepository bonusTypeRepository;

    @Inject
    public Populate(OfferingRepository offeringRepository,
                    BonusTypeRepository bonusTypeRepository) {
        this.offeringRepository = offeringRepository;
        this.bonusTypeRepository = bonusTypeRepository;
    }

    void onStart(@Observes StartupEvent ev) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                populateRedis();
            }
        });
        t1.start();
    }

    private void populateRedis() {
        LocalDate firstAlmDate = LocalDate.parse(firstDate);
        LocalDate maxAheadDate = LocalDate.now().plusDays(maxAhead);

        try (JedisPool pool = new JedisPool(new JedisPoolConfig(), redisHost, 6379, 2000, redisPassword)) {
            try (Jedis jedis = pool.getResource()) {
                Jsonb jsonb = JsonbBuilder.create();

                for (LanguageHelper.Language language : LanguageHelper.Language.values()) {
                    if (language == LanguageHelper.Language.NOT_FOUND) {
                        continue;
                    }

                    String langCode = LanguageHelper.getString(language);

                    String bonusKey = "alm/bonus/" + langCode;
                    Collection<BonusTypeMapDTOV2> bonusTypeMapDTOV2s = bonusTypeRepository.bonusTypesDtoV2(langCode);
                    long setCard = jedis.scard(bonusKey);
                    if (setCard != bonusTypeMapDTOV2s.size()) {
                        if (setCard > 0) {
                            jedis.del(bonusKey);
                        }

                        bonusTypeMapDTOV2s.forEach(bonusType -> {
                            jedis.sadd(bonusKey, jsonb.toJson(bonusType));
                        });
                    }

                    for (LocalDate date = firstAlmDate; date.isBefore(maxAheadDate); date = date.plusDays(1)) {
                        offeringRepository.singleDTOV2FromDate(date, langCode).ifPresent(offering -> {
                            String key = "alm/" + langCode + "/" + offering.date;

                            if (jedis.exists(key)) {
                                OfferingDTOV2 cachedOffering = jsonb.fromJson(jedis.get(key), OfferingDTOV2.class);
                                if (!cachedOffering.isSameByContent(offering)) {
                                    jedis.set(key, JsonbBuilder.create().toJson(offering));
                                }
                            } else {
                                jedis.set(key, JsonbBuilder.create().toJson(offering));
                            }
                        });
                    }
                }
            }
        }
    }

    @Scheduled(cron="0 5 0/2 * * ?")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    void populateRedis(ScheduledExecution execution) {
        populateRedis();
    }

}
