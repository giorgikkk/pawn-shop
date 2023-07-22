package com.example.service;

import com.example.exceptions.BranchNotFoundException;
import com.example.exceptions.IllegalBranchIdException;
import com.example.repository.BranchRepository;
import com.example.domain.Branch;
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
public class BranchService {
    private final BranchRepository repo;

    @Autowired
    public BranchService(BranchRepository repo) {
        this.repo = repo;
    }

    @Cacheable("branches")
    public List<Branch> getAll(final int page, final int pageSize) {
        log.info("getting branches on relevant page");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return repo.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    public Branch getById(final long id) {
        log.info("getting branch by id");

        return repo.findById(id)
                .orElseThrow(() -> new BranchNotFoundException(String.format("branch with id:%d not found", id)));
    }

    @CacheEvict(value = "branches", allEntries = true)
    public Branch create(final Branch branch) {
        log.info("creating new branch");

        if (repo.existsById(branch.getId())) {
            log.debug(String.format("branch with id:%d already exists", branch.getId()));
            throw new IllegalBranchIdException(String.format("branch with id:%d already exists", branch.getId()));
        }

        return repo.save(branch);
    }

    @CacheEvict(value = "branches", allEntries = true)
    public Branch update(final long id, final Branch branch) {
        log.info("updating branch");

        Optional<Branch> branchOptional = repo.findById(id);
        branchOptional.ifPresent(b -> {
            branch.setId(id);
            repo.save(branch);
        });

        return branchOptional
                .orElseThrow(() -> new BranchNotFoundException(String.format("branch with id:%d not found", id)));
    }

    @CacheEvict(value = "branches", allEntries = true)
    public Branch delete(final long id) {
        log.info("removing branch");

        Optional<Branch> branch = repo.findById(id);
        branch.ifPresent(b -> repo.deleteById(b.getId()));

        return branch
                .orElseThrow(() -> new BranchNotFoundException(String.format("branch with id:%d not found", id)));
    }
}
