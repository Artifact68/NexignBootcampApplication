 Nexign Bootcamp 2025

 Описание
Данный микросервис эмулирует работу коммутатора:
- Генерирует CDR записи звонков и сохраняет их в базу данных H2.
- Формирует UDR отчёты – сводку длительности входящих и исходящих звонков для одного абонента или для всех абонентов за заданный период.
- Позволяет инициировать генерацию CDR отчёта в формате CSV с сохранением файла в директории `reports`.

 Технологии
- Java 17 (OpenJDK 17)
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven


 REST API эндпоинты

 UDR отчёты
- Получить UDR отчёт для конкретного абонента:
  GET /api/udr/{msisdn}?month=yyyy-MM

- Получить UDR отчёты для всех абонентов за указанный месяц:
GET /api/udr/all?month=yyyy-MM


 Генерация CDR отчёта
- Сгенерировать CDR отчёт для абонента за заданный период:
POST /api/reports/cdr?msisdn={msisdn}&start={ISO_DATE_TIME}&end={ISO_DATE_TIME}
