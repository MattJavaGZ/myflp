# MyFlp

## 📋 O Projekcie

**MyFlp**   Aplikacja służąca do obsługi zamówień dla agentek sprzedających produktu na bazie aloesu.
            Aplikacja oferuje:

            - rejestrację nowych agentek oraz panel logowania,
            - panel administracyjny umożliwiający zarządzanie użytkownikami,
            - listę produktów wraz z cenami zakupu uzależnionymi od stanowiska agentki,
            - możliwość dodawania klientów i obsługiwania ich zamówień,
            - możliwość tworzenia i zarządzania zamówieniami,
            - generowanie różnego rodzaju podsumowań i zestawień dotyczących zamówień agentki, klienta lub konkretnego produktu.


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

## 🧪 Konta demonstracyjne

Aplikacja udostępnia testowe konta demonstracyjne, dzięki którym można zapoznać się z jej funkcjonalnościami.

### 👤 Użytkownik

**Login:** `user@user.pl`  
**Hasło:** `user`

Konto umożliwia korzystanie z funkcjonalności dostępnych dla standardowego użytkownika.

### 🔐 Administrator

**Login:** `admin@admin.pl`  
**Hasło:** `admin`

Konto posiada pełne uprawnienia administratora i umożliwia przetestowanie panelu administracyjnego.

Konta służą wyłącznie do celów demonstracyjnych!
