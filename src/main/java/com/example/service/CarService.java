package com.example.service;

import com.example.exceptions.ItemNotFoundException;
import com.example.repository.CarRepository;
import com.example.domain.Car;
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
public class CarService {
    private final CarRepository repo;

    @Autowired
    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    @Cacheable("cars")
    public List<Car> getAll(final int page, final int pageSize) {
        log.info("getting car on relevant page");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return repo.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    public Car getById(final long id) {
        log.info("getting car by id");

        return repo.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("car with id:%d not found", id)));
    }

    @CacheEvict(value = "cars", allEntries = true)
    public Car create(final Car car) {
        log.info("creating new car");

        return repo.save(car);
    }

    @CacheEvict(value = "cars", allEntries = true)
    public Car update(final long id, final Car car) {
        log.info("updating car");

        Optional<Car> carOptional = repo.findById(id);
        carOptional.ifPresent(j -> {
            car.setId(id);
            repo.save(car);
        });

        return carOptional
                .orElseThrow(() -> new ItemNotFoundException(String.format("car with id:%d not found", id)));
    }

    @CacheEvict(value = "cars", allEntries = true)
    public Car delete(final long id) {
        log.info("removing car");

        Optional<Car> car = repo.findById(id);
        car.ifPresent(c -> repo.deleteById(c.getId()));

        return car
                .orElseThrow(() -> new ItemNotFoundException(String.format("car with id:%d not found", id)));
    }
}
