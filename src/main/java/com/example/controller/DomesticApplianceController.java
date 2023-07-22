package com.example.controller;

import com.example.domain.DomesticAppliance;
import com.example.service.DomesticAppliancesService;
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
@RequestMapping("domestic_appliances")
@Slf4j
public class DomesticApplianceController {
    private final DomesticAppliancesService service;

    @Autowired
    public DomesticApplianceController(DomesticAppliancesService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "get jewelries")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get domestic appliances", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public List<DomesticAppliance> getAll(@Min(0) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                          @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("getting all domestic appliances");
        log.debug("page:{} pageSize:{}", page, pageSize);

        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "get domestic appliance by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get domestic appliance by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "domestic appliance not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public DomesticAppliance getById(@PathVariable final long id) {
        log.info("getting domestic appliance by id");
        log.debug("id:{}", id);

        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create domestic appliance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "created domestic appliances", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public DomesticAppliance create(@Valid @RequestBody final DomesticAppliance domesticAppliance) {
        log.info("creating new domestic appliances");
        log.debug("domestic appliances:{}", domesticAppliance);

        return service.create(domesticAppliance);
    }

    @PutMapping("{id}")
    @Operation(summary = "update domestic appliances")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "updated domestic appliances", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "domestic appliances not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public DomesticAppliance update(@PathVariable final long id, @Valid @RequestBody final DomesticAppliance domesticAppliance) {
        log.info("updating domestic appliances");
        log.debug("id:{} domestic appliances:{}", id, domesticAppliance);

        return service.update(id, domesticAppliance);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete domestic appliances")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "deleted domestic appliances", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "domestic appliances not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public DomesticAppliance delete(@PathVariable final long id) {
        log.info("deleting domestic appliances");
        log.debug("id:{}", id);

        return service.delete(id);
    }
}
