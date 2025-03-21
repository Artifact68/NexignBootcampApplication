package org.example.nexignbootcampapplication.repository;


import org.example.nexignbootcampapplication.entity.CdrRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью CdrRecord.
 */
@Repository
public interface CdrRecordRepository extends JpaRepository<CdrRecord, Long> {

    /**
     * Находит CDR записи по номеру абонента (как caller или receiver) и периоду времени.
     */
    List<CdrRecord> findByCallerOrReceiverAndCallStartBetween(String caller, String receiver,
                                                              LocalDateTime start, LocalDateTime end);

    /**
     * Находит CDR записи за указанный период времени.
     */
    List<CdrRecord> findByCallStartBetween(LocalDateTime start, LocalDateTime end);
}
