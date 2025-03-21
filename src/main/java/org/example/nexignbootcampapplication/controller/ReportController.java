package org.example.nexignbootcampapplication.controller;


import org.example.nexignbootcampapplication.service.CdrReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * REST-контроллер для генерации CDR отчётов.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final CdrReportService cdrReportService;

    public ReportController(CdrReportService cdrReportService) {
        this.cdrReportService = cdrReportService;
    }

    /**
     * Инициирует генерацию CDR отчёта для заданного абонента и периода.
     * Параметры start и end задаются в формате ISO_LOCAL_DATE_TIME.
     *
     * @param msisdn номер абонента.
     * @param start  начало периода (например, "2025-02-10T00:00:00").
     * @param end    конец периода (например, "2025-02-10T23:59:59").
     * @return сообщение с UUID запроса.
     */
    @PostMapping("/cdr")
    public ResponseEntity<String> generateCdrReport(@RequestParam String msisdn,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        UUID reportId = cdrReportService.generateReport(msisdn, start, end);
        return ResponseEntity.ok("Отчёт сгенерирован успешно. UUID запроса: " + reportId);
    }
}
