package com.example.repository;

import com.example.domain.Jewelry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JewelryRepository extends PagingAndSortingRepository<Jewelry, Long> {
}
