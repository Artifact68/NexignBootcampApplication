package org.example.nexignbootcampapplication.repository;


import org.example.nexignbootcampapplication.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Subscriber.
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByMsisdn(String msisdn);
}
