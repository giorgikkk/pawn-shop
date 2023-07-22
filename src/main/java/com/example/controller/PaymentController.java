package com.example.controller;

import com.example.domain.Payment;
import com.example.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("payments")
@Slf4j
public class PaymentController {
    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "get payments")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get payments", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public List<Payment> getAll(@Min(0) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("getting all payments");
        log.debug("page:{} pageSize:{}", page, pageSize);

        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "get payment by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get payment by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "payment not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Payment getById(@PathVariable final long id) {
        log.info("getting payment by id");
        log.debug("id:{}", id);

        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create payment")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "created payment", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Payment create(@Valid @RequestBody final Payment payment) {
        log.info("creating new payment");
        log.debug("payment:{}", payment);

        return service.create(payment);
    }

    @PutMapping("{id}")
    @Operation(summary = "update payment")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "updated payment", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "payment not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Payment update(@PathVariable final long id, @Valid @RequestBody final Payment payment) {
        log.info("updating payment");
        log.debug("id:{} payment:{}", id, payment);

        return service.update(id, payment);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete payment")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "deleted payment", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "payment not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Payment delete(@PathVariable final long id) {
        log.info("deleting payment");
        log.debug("id:{}", id);

        return service.delete(id);
    }

    @GetMapping("/money/{itemId}")
    @Operation(summary = "get amount of payment during the month")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get payment amount by item id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "item not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public BigDecimal getPaymentAmountInMonthForItem(@PathVariable final long itemId,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate startOfTheMonth,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate endOfTheMonth) {
        log.info("getting amount of payment during the month");
        log.debug("item_id:{} start_of_the_month:{} end_of_the_month:{}", itemId, startOfTheMonth, endOfTheMonth);

        return service.getPaymentAmountInMonthForItem(itemId, startOfTheMonth, endOfTheMonth);
    }
}
