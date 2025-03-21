package org.example.nexignbootcampapplication.entity;


import jakarta.persistence.*;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;

/**
 * Сущность, представляющая запись CDR (Call Data Record).
 */
@Entity
@Table(name = "cdr_records")
public class CdrRecord {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип звонка: "01" – исходящий, "02" – входящий.
     */
    @Column(nullable = false, length = 2)
    private String callType;

    /**
     * Номер абонента, инициирующего звонок.
     */
    @Column(nullable = false)
    private String caller;

    /**
     * Номер абонента, принимающего звонок.
     */
    @Column(nullable = false)
    private String receiver;

    /**
     * Дата и время начала звонка.
     */
    @Column(nullable = false)
    private LocalDateTime callStart;

    /**
     * Дата и время окончания звонка.
     */
    @Column(nullable = false)
    private LocalDateTime callEnd;

    public CdrRecord() {
    }

    public CdrRecord(String callType, String caller, String receiver, LocalDateTime callStart, LocalDateTime callEnd) {
        this.callType = callType;
        this.caller = caller;
        this.receiver = receiver;
        this.callStart = callStart;
        this.callEnd = callEnd;
    }



    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getCallStart() {
        return callStart;
    }

    public void setCallStart(LocalDateTime callStart) {
        this.callStart = callStart;
    }

    public LocalDateTime getCallEnd() {
        return callEnd;
    }

    public void setCallEnd(LocalDateTime callEnd) {
        this.callEnd = callEnd;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
