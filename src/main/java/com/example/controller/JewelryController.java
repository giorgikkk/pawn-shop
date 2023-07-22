package com.example.controller;

import com.example.domain.Jewelry;
import com.example.service.JewelryService;
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
@RequestMapping("jewelries")
@Slf4j
public class JewelryController {

    private final JewelryService service;

    @Autowired
    public JewelryController(JewelryService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "get jewelries")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get jewelries", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public List<Jewelry> getAll(@Min(0) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("getting all jewelries");
        log.debug("page:{} pageSize:{}", page, pageSize);

        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "get jewelry by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get jewelry by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "jewelry not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Jewelry getById(@PathVariable final long id) {
        log.info("getting jewelry by id");
        log.debug("id:{}", id);

        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create jewelry")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "created jewelry", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Jewelry create(@Valid @RequestBody final Jewelry jewelry) {
        log.info("creating new jewelry");
        log.debug("jewelry:{}", jewelry);

        return service.create(jewelry);
    }

    @PutMapping("{id}")
    @Operation(summary = "update jewelry")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "updated jewelry", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "jewelry not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Jewelry update(@PathVariable final long id, @Valid @RequestBody final Jewelry jewelry) {
        log.info("updating jewelry");
        log.debug("id:{} jewelry:{}", id, jewelry);

        return service.update(id, jewelry);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete jewelry")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "deleted jewelry", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "jewelry not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Jewelry delete(@PathVariable final long id) {
        log.info("deleting jewelry");
        log.debug("id:{}", id);

        return service.delete(id);
    }
}
