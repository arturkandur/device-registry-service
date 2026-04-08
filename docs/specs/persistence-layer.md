# Device Registry Service Persistence

## DeviceEntity

JPA entity mapped to the `devices` table.

| Column | Type | Notes |
|--------|------|-------|
| `id` | `BIGINT` | PK, auto-increment |
| `name` | `VARCHAR(255)` | not null |
| `brand` | `VARCHAR(255)` | not null |
| `state` | `ENUM` | `AVAILABLE`, `IN_USE`, `INACTIVE` |
| `creation_time` | `TIMESTAMP` | not null |

## Database Schema

```sql
CREATE TABLE devices (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  name          VARCHAR(255) NOT NULL,
  brand         VARCHAR(255) NOT NULL,
  state         ENUM('AVAILABLE', 'IN_USE', 'INACTIVE') NOT NULL,
  creation_time TIMESTAMP    NOT NULL
);
```

## Datasource Configuration

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/device_registry
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
```
