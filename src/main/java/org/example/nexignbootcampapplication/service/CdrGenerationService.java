package org.example.nexignbootcampapplication.service;


import org.example.nexignbootcampapplication.entity.CdrRecord;
import org.example.nexignbootcampapplication.entity.Subscriber;
import org.example.nexignbootcampapplication.repository.CdrRecordRepository;
import org.example.nexignbootcampapplication.repository.SubscriberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Сервис для эмуляции работы коммутатора – генерация CDR-записей за один год.
 */
@Service
public class CdrGenerationService {

    private final CdrRecordRepository cdrRecordRepository;
    private final SubscriberRepository subscriberRepository;
    private final Random random = new Random();

    public CdrGenerationService(CdrRecordRepository cdrRecordRepository, SubscriberRepository subscriberRepository) {
        this.cdrRecordRepository = cdrRecordRepository;
        this.subscriberRepository = subscriberRepository;
    }

    /**
     * Генерирует CDR-записи для всех абонентов за один год.
     */
    @Transactional
    public void generateCdrRecords() {
        List<Subscriber> subscribers = subscriberRepository.findAll();

        // Если таблица абонентов пуста – создаём дефолтный список (не менее 10)
        if (subscribers.isEmpty()) {
            subscribers = createDefaultSubscribers();
        }

        // Начало периода – 1 год назад от текущего момента.
        LocalDateTime startDate = LocalDateTime.now().minusYears(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        // Генерация 1000 CDR записей для имитации звонков.
        for (int i = 0; i < 1000; i++) {
            Subscriber subscriber = subscribers.get(random.nextInt(subscribers.size()));
            // Случайно выбираем тип звонка: "01" (исходящий) или "02" (входящий)
            String callType = random.nextBoolean() ? "01" : "02";
            String caller = subscriber.getMsisdn();
            // Выбор случайного получателя (может совпадать с инициатором, если список небольшой)
            String receiver = subscribers.get(random.nextInt(subscribers.size())).getMsisdn();

            // Генерация случайного времени звонка в пределах года
            LocalDateTime callStart = startDate.plusMinutes(random.nextInt(525600)); // 525600 минут = 1 год
            // Длительность звонка от 1 до 5 минут
            LocalDateTime callEnd = callStart.plusSeconds(60L * (1 + random.nextInt(5)));

            CdrRecord record = new CdrRecord(callType, caller, receiver, callStart, callEnd);
            cdrRecordRepository.save(record);
        }
    }

    /**
     * Создаёт список дефолтных абонентов.
     */
    private List<Subscriber> createDefaultSubscribers() {
        List<Subscriber> defaultSubscribers = List.of(
                new Subscriber("79990000001"),
                new Subscriber("79990000002"),
                new Subscriber("79990000003"),
                new Subscriber("79990000004"),
                new Subscriber("79990000005"),
                new Subscriber("79990000006"),
                new Subscriber("79990000007"),
                new Subscriber("79990000008"),
                new Subscriber("79990000009"),
                new Subscriber("79990000010")
        );
        return subscriberRepository.saveAll(defaultSubscribers);
    }
}
