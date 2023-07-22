package com.example.service;

import com.example.exceptions.IllegalMaterialIdException;
import com.example.exceptions.MaterialNotFoundException;
import com.example.repository.MaterialRepository;
import com.example.domain.Material;
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
public class MaterialService {
    private final MaterialRepository repo;

    @Autowired
    public MaterialService(MaterialRepository repo) {
        this.repo = repo;
    }

    @Cacheable("materials")
    public List<Material> getAll(final int page, final int pageSize) {
        log.info("getting materials on relevant page");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return repo.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    public Material getById(final long id) {
        log.info("getting material by id");

        return repo.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(String.format("material with id:%d not found", id)));
    }

    @CacheEvict(value = "materials", allEntries = true)
    public Material create(final Material material) {
        log.info("creating new material");

        if (repo.existsById(material.getId())) {
            log.debug(String.format("material with id:%d already exists", material.getId()));
            throw new IllegalMaterialIdException(String.format("material with id:%d already exists", material.getId()));
        }

        return repo.save(material);
    }

    @CacheEvict(value = "materials", allEntries = true)
    public Material update(final long id, final Material material) {
        log.info("updating material");

        Optional<Material> materialOptional = repo.findById(id);
        materialOptional.ifPresent(m -> {
            material.setId(id);
            repo.save(material);
        });

        return materialOptional
                .orElseThrow(() -> new MaterialNotFoundException(String.format("material with id:%d not found", id)));
    }

    @CacheEvict(value = "materials", allEntries = true)
    public Material delete(final long id) {
        log.info("removing material");

        Optional<Material> material = repo.findById(id);
        material.ifPresent(m -> repo.deleteById(m.getId()));

        return material
                .orElseThrow(() -> new MaterialNotFoundException(String.format("material with id:%d not found", id)));
    }
}
