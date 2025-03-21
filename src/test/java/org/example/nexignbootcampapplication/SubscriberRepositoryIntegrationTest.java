package org.example.nexignbootcampapplication;

import jakarta.transaction.Transactional;
import org.example.nexignbootcampapplication.entity.Subscriber;
import org.example.nexignbootcampapplication.repository.SubscriberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class SubscriberRepositoryIntegrationTest {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Test
    void testCreateAndFindSubscriber() {
        Subscriber subscriber = new Subscriber("79990000123");
        subscriberRepository.save(subscriber);

        Optional<Subscriber> found = subscriberRepository.findByMsisdn("79990000123");
        assertTrue(found.isPresent());
    }
}
