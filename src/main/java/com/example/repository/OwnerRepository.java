package com.example.repository;

import com.example.domain.Owner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends PagingAndSortingRepository<Owner, Long> {
    Optional<Owner> findOwnerByPersonalNo(String personalNo);
}
