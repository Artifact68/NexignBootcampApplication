package org.example.nexignbootcampapplication.controller;


import org.example.nexignbootcampapplication.dto.UdrDto;
import org.example.nexignbootcampapplication.service.UdrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для работы с UDR отчётами.
 */
@RestController
@RequestMapping("/api/udr")
public class UdrController {

    private final UdrService udrService;

    public UdrController(UdrService udrService) {
        this.udrService = udrService;
    }

    /**
     * Возвращает UDR отчёт для конкретного абонента.
     * Параметр month (формат "yyyy-MM") указывает период отчёта (опционально).
     *
     * @param msisdn номер абонента.
     * @param month  период (например, "2025-02").
     * @return объект UdrDto.
     */
    @GetMapping("/{msisdn}")
    public ResponseEntity<UdrDto> getUdrForSubscriber(@PathVariable String msisdn,
                                                      @RequestParam(required = false) String month) {
        UdrDto udrDto = udrService.getUdrForSubscriber(msisdn, month);
        return ResponseEntity.ok(udrDto);
    }

    /**
     * Возвращает UDR отчёты для всех абонентов за заданный месяц.
     *
     * @param month период в формате "yyyy-MM".
     * @return список UdrDto.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UdrDto>> getUdrForAllSubscribers(@RequestParam String month) {
        List<UdrDto> udrList = udrService.getUdrForAllSubscribers(month);
        return ResponseEntity.ok(udrList);
    }
}
