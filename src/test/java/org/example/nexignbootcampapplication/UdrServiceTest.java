package org.example.nexignbootcampapplication;


import org.example.nexignbootcampapplication.dto.UdrDto;
import org.example.nexignbootcampapplication.entity.CdrRecord;
import org.example.nexignbootcampapplication.repository.CdrRecordRepository;
import org.example.nexignbootcampapplication.service.UdrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест для сервиса формирования UDR отчётов.
 */
public class UdrServiceTest {

    private CdrRecordRepository cdrRecordRepository;
    private UdrService udrService;

    @BeforeEach
    public void setup() {
        cdrRecordRepository = Mockito.mock(CdrRecordRepository.class);
        udrService = new UdrService(cdrRecordRepository);
    }

    @Test
    public void testGetUdrForSubscriber() {
        String msisdn = "79990000001";
        LocalDateTime start = LocalDateTime.parse("2025-02-01T00:00:00");
        LocalDateTime end = LocalDateTime.parse("2025-02-28T23:59:59");

        // Исходящий вызов: длительность 5 минут
        CdrRecord record1 = new CdrRecord("01", msisdn, "79990000002", start.plusMinutes(10), start.plusMinutes(15));
        // Входящий вызов: длительность 5 минут
        CdrRecord record2 = new CdrRecord("02", "79990000003", msisdn, start.plusMinutes(20), start.plusMinutes(25));

        Mockito.when(cdrRecordRepository.findByCallerOrReceiverAndCallStartBetween(
                        Mockito.eq(msisdn), Mockito.eq(msisdn), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(record1, record2));

        UdrDto udrDto = udrService.getUdrForSubscriber(msisdn, "2025-02");

        // 5 минут = 300 секунд, формат "00:05:00"
        assertEquals("00:05:00", udrDto.getOutcomingCall().getTotalTime());
        assertEquals("00:05:00", udrDto.getIncomingCall().getTotalTime());
    }
}
