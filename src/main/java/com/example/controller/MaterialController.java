package com.example.controller;

import com.example.domain.Material;
import com.example.service.MaterialService;
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
@RequestMapping("materials")
@Slf4j
public class MaterialController {
    private final MaterialService service;

    @Autowired
    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "get materials")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get materials", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public List<Material> getAll(@Min(0) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                 @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("getting all materials");
        log.debug("page:{} pageSize:{}", page, pageSize);

        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "get material by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get material by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "material not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Material getById(@PathVariable final long id) {
        log.info("getting material by id");
        log.debug("id:{}", id);

        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create material")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "created material", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "existing id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Material create(@Valid @RequestBody final Material material) {
        log.info("creating new material");
        log.debug("material:{}", material);

        return service.create(material);
    }

    @PutMapping("{id}")
    @Operation(summary = "update material")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "updated material", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "material not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Material update(@PathVariable final long id, @Valid @RequestBody final Material material) {
        log.info("updating material");
        log.debug("id:{} material:{}", id, material);

        return service.update(id, material);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete material")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "deleted material", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "material not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Material delete(@PathVariable final long id) {
        log.info("deleting material");
        log.debug("id:{}", id);

        return service.delete(id);
    }
}
