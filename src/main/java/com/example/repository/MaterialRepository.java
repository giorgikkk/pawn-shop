package com.example.repository;

import com.example.domain.Material;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends PagingAndSortingRepository<Material, Long> {
}
