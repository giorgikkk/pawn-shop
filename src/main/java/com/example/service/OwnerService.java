package com.example.service;

import com.example.exceptions.IllegalOwnerPersonalNoException;
import com.example.exceptions.OwnerNotFoundException;
import com.example.repository.OwnerRepository;
import com.example.domain.Owner;
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
public class OwnerService {
    private final OwnerRepository repo;

    @Autowired
    public OwnerService(OwnerRepository repo) {
        this.repo = repo;
    }

    @Cacheable("owners")
    public List<Owner> getAll(final int page, final int pageSize) {
        log.info("getting owners on relevant page");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return repo.findAll(pageable).
                stream().
                collect(Collectors.toList());
    }

    public Owner getById(final long id) {
        log.info("getting owner by id");

        return repo.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException(String.format("owner with id:%d not found", id)));
    }

    @CacheEvict(value = "owners", allEntries = true)
    public Owner create(final Owner owner) {
        log.info("creating new owner");

        if (repo.findOwnerByPersonalNo(owner.getPersonalNo()).isPresent()) {
            log.debug(String.format("owner with personalNo:%s already exists", owner.getPersonalNo()));
            throw new IllegalOwnerPersonalNoException(String.format("owner with personalNo:%s already exists", owner.getPersonalNo()));
        }

        return repo.save(owner);
    }

    @CacheEvict(value = "owners", allEntries = true)
    public Owner update(final long id, final Owner owner) {
        log.info("updating owner");

        if (!repo.existsById(id)) {
            log.debug(String.format("owner with id:%d does not exist", id));
            throw new OwnerNotFoundException(String.format("owner with id:%d not found", id));
        }

        Optional<Owner> ownerOptional = repo.findOwnerByPersonalNo(owner.getPersonalNo());
        if (ownerOptional.isPresent() && ownerOptional.get().getId() != id) {
            log.debug(String.format("owner with personalNo:%s already exists", owner.getPersonalNo()));
            throw new IllegalOwnerPersonalNoException(String.format("owner with personalNo:%s already exists", owner.getPersonalNo()));
        }

        owner.setId(id);

        return repo.save(owner);
    }

    @CacheEvict(value = "owners", allEntries = true)
    public Owner delete(final long id) {
        log.info("removing owner");

        Optional<Owner> owner = repo.findById(id);
        owner.ifPresent(o -> repo.deleteById(o.getId()));

        return owner
                .orElseThrow(() -> new OwnerNotFoundException(String.format("owner with id:%d not found", id)));
    }
}
