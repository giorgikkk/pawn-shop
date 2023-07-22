package com.example.service;

import com.example.exceptions.ItemNotFoundException;
import com.example.repository.DomesticAppliancesRepository;
import com.example.domain.DomesticAppliance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DomesticAppliancesService {
    private final DomesticAppliancesRepository repo;

    @Autowired
    public DomesticAppliancesService(DomesticAppliancesRepository repo) {
        this.repo = repo;
    }

    @Cacheable("domestic_appliances")
    public List<DomesticAppliance> getAll(final int page, final int pageSize) {
        log.info("getting domestic appliances on relevant page");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return repo.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    public DomesticAppliance getById(final long id) {
        log.info("getting domestic appliance by id");

        return repo.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("domestic appliance with id:%d not found", id)));
    }

    @CacheEvict(value = "domestic_appliances", allEntries = true)
    public DomesticAppliance create(final DomesticAppliance domesticAppliance) {
        log.info("creating new domestic appliance");

        return repo.save(domesticAppliance);
    }

    @CacheEvict(value = "domestic_appliances", allEntries = true)
    public DomesticAppliance update(final long id, final DomesticAppliance domesticAppliance) {
        log.info("updating domestic appliance");

        Optional<DomesticAppliance> domesticApplianceOptional = repo.findById(id);
        domesticApplianceOptional.ifPresent(d -> {
            domesticAppliance.setId(id);
            repo.save(domesticAppliance);
        });

        return domesticApplianceOptional
                .orElseThrow(() -> new ItemNotFoundException(String.format("domestic appliance with id:%d not found", id)));
    }

    @CacheEvict(value = "domestic_appliance", allEntries = true)
    public DomesticAppliance delete(final long id) {
        log.info("removing domestic appliance");

        Optional<DomesticAppliance> domesticAppliance = repo.findById(id);
        domesticAppliance.ifPresent(d -> repo.deleteById(d.getId()));

        return domesticAppliance
                .orElseThrow(() -> new ItemNotFoundException(String.format("domestic appliance with id:%d not found", id)));
    }
}
