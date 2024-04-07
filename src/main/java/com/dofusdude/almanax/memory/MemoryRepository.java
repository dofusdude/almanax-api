package com.dofusdude.almanax.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import com.dofusdude.almanax.dto.BonusTypeMapDTOV2;
import com.dofusdude.almanax.dto.OfferingDTOV2;
import com.dofusdude.almanax.gateway.BonusTypeRepository;
import com.dofusdude.almanax.gateway.OfferingRepository;

@RequestScoped
public class MemoryRepository {
    private OfferingRepository offeringRepository;

    private BonusTypeRepository bonusTypeRepository;

    @Inject
    public MemoryRepository(OfferingRepository offeringRepository,
            BonusTypeRepository bonusTypeRepository) {
        this.offeringRepository = offeringRepository;
        this.bonusTypeRepository = bonusTypeRepository;
    }

    public Collection<BonusTypeMapDTOV2> getBonuses(String language) {
        return bonusTypeRepository.bonusTypesDtoV2(language);
    }

    public Optional<OfferingDTOV2> getSingleDate(LocalDate date, String language) {
        return offeringRepository.singleDTOV2FromDate(date, language);
    }

}
