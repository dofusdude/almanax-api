package de.dofusdu.memory;

import de.dofusdu.dto.BonusTypeMapDTOV2;
import de.dofusdu.dto.OfferingDTOV2;
import de.dofusdu.gateway.BonusTypeRepository;
import de.dofusdu.gateway.OfferingRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import redis.clients.jedis.Jedis;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class MemoryRepository {

    @ConfigProperty(name = "redis.host")
    String redisHost;

    @ConfigProperty(name = "redis.password")
    String redisPassword;

    private OfferingRepository offeringRepository;

    private BonusTypeRepository bonusTypeRepository;

    @Inject
    public MemoryRepository(OfferingRepository offeringRepository,
                            BonusTypeRepository bonusTypeRepository) {
        this.offeringRepository = offeringRepository;
        this.bonusTypeRepository = bonusTypeRepository;
    }

    public Jedis redis() {
        Jedis jedis = new Jedis(redisHost, 6379);
        jedis.auth(redisPassword);
        return jedis;
    }

    public Collection<BonusTypeMapDTOV2> getBonuses(String language) {
            return bonusTypeRepository.bonusTypesDtoV2(language);
    }

    public Collection<BonusTypeMapDTOV2> getBonuses(String language, Jedis jedis, Jsonb jsonb) {
        String bonusKey = "alm/bonus/" + language;

        Collection<String> members = jedis.smembers(bonusKey);
        if (members == null || members.isEmpty()) {
            return bonusTypeRepository.bonusTypesDtoV2(language);
        }

        return members.stream().map(bonus -> jsonb.fromJson(bonus, BonusTypeMapDTOV2.class)).collect(Collectors.toList());
    }

    public Optional<OfferingDTOV2> getSingleDate(LocalDate date, String language) {
        return offeringRepository.singleDTOV2FromDate(date, language);
    }

    public Optional<OfferingDTOV2> getSingleDate(LocalDate date, String language, Jedis jedis, Jsonb jsonb) {
        String key = "alm/" + language + "/" + date;
        String offering = jedis.get(key);
        if (offering == null) {
            return offeringRepository.singleDTOV2FromDate(date, language);
        }

        OfferingDTOV2 cachedOffering = jsonb.fromJson(offering, OfferingDTOV2.class);
        return Optional.of(cachedOffering);
    }

}
