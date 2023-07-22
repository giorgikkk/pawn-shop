package com.example.controller;

import com.example.domain.Owner;
import com.example.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("owners")
@Slf4j
public class OwnerController {
    private final OwnerService service;

    @Autowired
    public OwnerController(OwnerService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "get owners")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get owners", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public List<Owner> getAll(@Min(0) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                              @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("getting all owners");
        log.debug("page:{} pageSize:{}", page, pageSize);

        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "get owner by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get owner by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "owner not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Owner getById(@PathVariable final long id) {
        log.info("getting owner by id");
        log.debug("id:{}", id);

        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create owner")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "created owner", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "existing personalNo", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Owner create(@Valid @RequestBody final Owner owner) {
        log.info("creating new owner");
        log.debug("owner:{}", owner);

        return service.create(owner);
    }

    @PutMapping("{id}")
    @Operation(summary = "update owner")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "updated owner", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "owner not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "existing peronalNo", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Owner update(@PathVariable final long id, @Valid @RequestBody final Owner owner) {
        log.info("updating owner");
        log.debug("id:{} owner:{}", id, owner);

        return service.update(id, owner);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete owner")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "deleted owner", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "owner not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Owner delete(@PathVariable final long id) {
        log.info("deleting owner");
        log.debug("id:{}", id);

        return service.delete(id);
    }
}
