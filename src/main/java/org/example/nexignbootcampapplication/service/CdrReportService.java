package org.example.nexignbootcampapplication.service;


import org.example.nexignbootcampapplication.entity.CdrRecord;
import org.example.nexignbootcampapplication.repository.CdrRecordRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для формирования и сохранения CDR отчёта.
 */
@Service
public class CdrReportService {

    private final CdrRecordRepository cdrRecordRepository;

    /**
     * Путь для сохранения отчётов (настраивается в application.properties).
     */
    @Value("${report.directory:reports}")
    private String reportDirectory;

    public CdrReportService(CdrRecordRepository cdrRecordRepository) {
        this.cdrRecordRepository = cdrRecordRepository;
    }

    /**
     * Генерирует CDR отчёт для заданного абонента и периода.
     * Файл сохраняется в формате CSV в указанной директории.
     *
     * @param msisdn номер абонента.
     * @param start  начало периода.
     * @param end    конец периода.
     * @return UUID запроса.
     */
    public UUID generateReport(String msisdn, LocalDateTime start, LocalDateTime end) {
        List<CdrRecord> records = cdrRecordRepository.findByCallerOrReceiverAndCallStartBetween(msisdn, msisdn, start, end);
        // Сортируем записи по времени начала звонка
        records.sort((r1, r2) -> r1.getCallStart().compareTo(r2.getCallStart()));

        UUID reportId = UUID.randomUUID();
        String fileName = msisdn + "_" + reportId + ".csv";

        File directory = new File(reportDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File reportFile = new File(directory, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
            // Формируем строки в формате: тип,caller,receiver,callStart,callEnd
            for (CdrRecord record : records) {
                String line = String.join(",",
                        record.getCallType(),
                        record.getCaller(),
                        record.getReceiver(),
                        record.getCallStart().toString(),
                        record.getCallEnd().toString());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при генерации отчёта: " + e.getMessage(), e);
        }

        return reportId;
    }
}
