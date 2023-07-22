package com.example.controller;

import com.example.domain.Branch;
import com.example.service.BranchService;
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
@RequestMapping("branches")
@Slf4j
public class BranchController {
    private final BranchService service;

    @Autowired
    public BranchController(BranchService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "get branches")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get branches", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public List<Branch> getAll(@Min(0) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                               @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("getting all branches");
        log.debug("page:{} pageSize:{}", page, pageSize);

        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "get branch by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get branch by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "branch not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Branch getById(@PathVariable final long id) {
        log.info("getting branch by id");
        log.debug("id:{}", id);

        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create branch")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "created branch", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "existing id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Branch create(@Valid @RequestBody final Branch branch) {
        log.info("creating new branch");
        log.debug("branch:{}", branch);

        return service.create(branch);
    }

    @PutMapping("{id}")
    @Operation(summary = "update branch")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "updated branch", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "branch not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Branch update(@PathVariable final long id, @Valid @RequestBody final Branch branch) {
        log.info("updating branch");
        log.debug("id:{} branch:{}", id, branch);

        return service.update(id, branch);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete branch")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "deleted branch", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "branch not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Branch delete(@PathVariable final long id) {
        log.info("deleting branch");
        log.debug("id:{}", id);

        return service.delete(id);
    }
}
