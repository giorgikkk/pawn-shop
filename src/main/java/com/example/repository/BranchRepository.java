package com.example.repository;

import com.example.domain.Branch;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends PagingAndSortingRepository<Branch, Long> {
}
