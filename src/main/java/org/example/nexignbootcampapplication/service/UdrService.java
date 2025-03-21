package org.example.nexignbootcampapplication.service;

import org.example.nexignbootcampapplication.dto.UdrDto;
import org.example.nexignbootcampapplication.dto.UdrDto.CallDuration;
import org.example.nexignbootcampapplication.entity.CdrRecord;
import org.example.nexignbootcampapplication.repository.CdrRecordRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Сервис для формирования UDR отчётов.
 */
@Service
public class UdrService {

    private final CdrRecordRepository cdrRecordRepository;

    public UdrService(CdrRecordRepository cdrRecordRepository) {
        this.cdrRecordRepository = cdrRecordRepository;
    }

    /**
     * Формирует UDR отчёт для конкретного абонента.
     * Если параметр month задан (формат "yyyy-MM"), отчёт составляется за указанный месяц,
     * иначе – за весь период.
     *
     * @param msisdn номер абонента.
     * @param month  период в формате "yyyy-MM" (опционально).
     * @return объект UdrDto.
     */
    public UdrDto getUdrForSubscriber(String msisdn, String month) {
        LocalDateTime start;
        LocalDateTime end;

        if (month != null && !month.isBlank()) {
            start = LocalDateTime.parse(month + "-01T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            end = start.plusMonths(1).minusSeconds(1);
        } else {
            start = LocalDateTime.MIN;
            end = LocalDateTime.now();
        }

        // Получаем все записи, где msisdn фигурирует как инициатор или получатель
        List<CdrRecord> records = cdrRecordRepository.findByCallerOrReceiverAndCallStartBetween(msisdn, msisdn, start, end);

        long incomingSeconds = 0;
        long outcomingSeconds = 0;

        for (CdrRecord record : records) {
            long duration = Duration.between(record.getCallStart(), record.getCallEnd()).getSeconds();
            // Для входящих звонков msisdn должен совпадать с получателем
            if ("02".equals(record.getCallType()) && msisdn.equals(record.getReceiver())) {
                incomingSeconds += duration;
            }
            // Для исходящих звонков msisdn должен совпадать с инициатором
            else if ("01".equals(record.getCallType()) && msisdn.equals(record.getCaller())) {
                outcomingSeconds += duration;
            }
        }

        String incomingTime = formatDuration(incomingSeconds);
        String outcomingTime = formatDuration(outcomingSeconds);
        return new UdrDto(msisdn, new CallDuration(incomingTime), new CallDuration(outcomingTime));
    }

    /**
     * Формирует UDR отчёты для всех абонентов за указанный месяц.
     *
     * @param month период в формате "yyyy-MM".
     * @return список UdrDto.
     */
    public List<UdrDto> getUdrForAllSubscribers(String month) {
        LocalDateTime start;
        LocalDateTime end;
        if (month != null && !month.isBlank()) {
            start = LocalDateTime.parse(month + "-01T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            end = start.plusMonths(1).minusSeconds(1);
        } else {
            start = LocalDateTime.MIN;
            end = LocalDateTime.now();
        }

        List<CdrRecord> records = cdrRecordRepository.findByCallStartBetween(start, end);
        // Собираем уникальные msisdn из полей caller и receiver
        var msisdnSet = records.stream()
                .flatMap(record -> List.of(record.getCaller(), record.getReceiver()).stream())
                .collect(java.util.stream.Collectors.toSet());

        return msisdnSet.stream()
                .map(msisdn -> getUdrForSubscriber(msisdn, month))
                .toList();
    }

    /**
     * Форматирует общее количество секунд в строку формата HH:mm:ss.
     */
    private String formatDuration(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
