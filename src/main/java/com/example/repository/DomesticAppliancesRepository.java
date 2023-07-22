package com.example.repository;

import com.example.domain.DomesticAppliance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomesticAppliancesRepository extends PagingAndSortingRepository<DomesticAppliance, Long> {
}
