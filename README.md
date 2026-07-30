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
