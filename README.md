# Order Management Service

Spring Boot 3 + Java 21 + MySQL REST API.

## Requirements
- Java 21
- Maven 3.9+
- MySQL 8.x

## 1. Create MySQL database

Run:

```sql
CREATE DATABASE order_db;
```

The application uses:

- Host: localhost
- Port: 3306
- Database: order_db
- Username: root
- Password: root

Change these values in `src/main/resources/application.properties` if your MySQL setup is different.

Hibernate is configured with `ddl-auto=update`, so the `orders` table is created/updated automatically when the application starts.

## 2. Run

```bash
mvn clean install
mvn spring-boot:run
```

Or:

```bash
java -jar target/order-management-service-0.0.1-SNAPSHOT.jar
```

Application:
`http://localhost:8080`

## REST APIs

### Create order
POST `/api/orders`

```json
{
  "customerName": "Anoop",
  "productName": "Laptop",
  "quantity": 2,
  "price": 65000.00,
  "status": "CREATED"
}
```

### Get all orders
GET `/api/orders`

### Get one order
GET `/api/orders/1`

### Full update
PUT `/api/orders/1`

```json
{
  "customerName": "Anoop Kumar",
  "productName": "MacBook",
  "quantity": 1,
  "price": 120000.00,
  "status": "CONFIRMED"
}
```

### Partial update
PATCH `/api/orders/1`

Only send fields that need to change:

```json
{
  "status": "SHIPPED"
}
```

### Delete
DELETE `/api/orders/1`

## Important PUT vs PATCH

PUT replaces the complete order representation. PATCH changes only the fields supplied in the request.

## MySQL data flow

POST/PUT/PATCH -> Controller -> Service -> JPA Repository -> Hibernate -> MySQL

DELETE -> Controller -> Service -> JPA Repository -> MySQL

## Docker

Build image:

```bash
docker build -t order-management-service:latest .
```

Run:

```bash
docker run -p 8080:8080 order-management-service:latest
```

### Docker + MySQL

The application container must be able to reach MySQL. For local MySQL running on the host, update the datasource URL appropriately for your Docker environment.

For a fully containerized setup, use a Docker Compose file with MySQL and the Spring Boot application on the same Docker network.

## Jenkins

The included `Jenkinsfile` performs:

1. Git checkout
2. Maven build
3. Unit tests
4. Docker image build
5. Docker image verification

### Jenkins tools

In Jenkins Global Tool Configuration, configure:

- Maven name: `Maven3`
- JDK name: `JDK21`

If your Jenkins tool names are different, change them in the Jenkinsfile.

### Windows Jenkins

The pipeline uses `bat` because it is designed for Jenkins running on Windows.
