package org.example.nexignbootcampapplication.entity;


import jakarta.persistence.*;
import org.springframework.data.annotation.Id;


/**
 * Сущность для хранения информации об абоненте.
 */
@Entity
@Table(name = "subscribers")
public class Subscriber {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Номер абонента (msisdn).
     */
    @Column(nullable = false, unique = true)
    private String msisdn;

    public Subscriber() {
    }

    public Subscriber(String msisdn) {
        this.msisdn = msisdn;
    }


    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
