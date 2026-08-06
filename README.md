# MyFlp

## 📋 O Projekcie

**MyFlp** Aplikacja służąca do obsługi zamówień dla agentek sprzedających produktu na bazie aloesu

- **Język**: Java 21
- **Framework**: Spring Boot 3.5.6
- **Baza danych**: MySQL (produkcja) / H2 (development)
- **Szablony**: Thymeleaf
- **ORM**: JPA/Hibernate
- **Migracje**: Liquibase

# 🔐 Konfiguracja email

Konfiguracja dla smtp.poczta.onet.pl

## application.yml

```yaml

app:
  email:
    login: ${EMAIL_USERNAME}   # ← przeczytana ze zmiennej środowiskowej
    password: ${EMAIL_KEY}     # ← przeczytana ze zmiennej środowiskowej
```

# 🔐 Konfiguracja DATABASE

## application.prod.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://mysql:3306/myflp
    username: ${DATABASE_USERNAME}      # ← przeczytana ze zmiennej środowiskowej
    password: ${DATABASE_KEY}           # ← przeczytana ze zmiennej środowiskowej
```
