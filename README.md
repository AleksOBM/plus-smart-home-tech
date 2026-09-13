# smart-home-tech

![Static Badge](https://img.shields.io/badge/Java-21-green)
![Static Badge](https://img.shields.io/badge/Spring_Boot-3.3.2-green)
![Static Badge](https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white)
![Static Badge](https://img.shields.io/badge/gRPC-8A2BE2)
![Static Badge](https://img.shields.io/badge/Kafka-aaabaf)
![Static Badge](https://img.shields.io/badge/OpenFeign-72a664)
![Static Badge](https://img.shields.io/badge/Micro--servises-8A2BE2)
![Static Badge](https://img.shields.io/badge/Lombok-red)
![Static Badge](https://img.shields.io/badge/Maven-orange)

**Проект умного дома** - система сбора и анализа данных от датчиков умного дома, 
для последующего выполнения устройствами необходимых действий (например включение света).

<img alt="img.png" src="/.img/img.png" width="500"/>

### Microservice map

<img alt="image" src="/.img/smart-home-tech.png" width="500"/>

### Http API
```mermaid
mindmap
  root((API))
    🌐/api/categories
        GET /api/categories
        GET /api/categories/:id
        POST /api/categories
    🌐/api/inventory
        GET /api/inventory
        GET /api/inventory/:productId
        POST /api/inventory
        POST /api/inventory/reserve
        POST /api/inventory/release
        PUT /api/inventory
    🌐/api/orders
        GET /api/orders
        GET /api/orders/:id
        GET /api/orders/by-email
        POST /api/orders
    🌐/api/products
        GET /api/products
        GET /api/products/:id
        GET /api/products/category/:categoryId
        GET /api/products/search
        PATCH /api/products/:id
        POST /api/products
```

### Database map
```mermaid
erDiagram
    %% --- МИКРОСЕРВИС СЦЕНАРИЕВ ---
    SCENARIOS {
        bigint id PK
        varchar hub_id
        varchar name
    }

    SENSORS {
        varchar id PK
        varchar hub_id
    }

    CONDITIONS {
        bigint id PK
        varchar type
        varchar operation
        integer value
    }

    ACTIONS {
        bigint id PK
        varchar type
        integer value
    }

    SCENARIO_CONDITIONS {
        bigint scenario_id FK
        varchar sensor_id FK
        bigint condition_id FK
    }

    SCENARIO_ACTIONS {
        bigint scenario_id FK
        varchar sensor_id FK
        bigint action_id FK
    }

    %% --- МИКРОСЕРВИС МАГАЗИНА ---
    CATEGORIES {
        bigint id PK
        varchar name
        varchar description
    }

    PRODUCTS {
        bigint id PK
        varchar name
        varchar description
        numeric price
        varchar image_url
        boolean active
        bigint category_id FK
    }

    ORDERS {
        bigint id PK
        varchar customer_name
        varchar customer_email
        varchar status
        numeric total_price
        varchar status_details
        timestamp created_at
    }

    ORDER_ITEMS {
        bigint id PK
        bigint order_id FK
        bigint product_id FK
        varchar product_name
        integer quantity
        numeric price
    }

    %% --- СВЯЗИ ---
    SCENARIOS ||--o{ SCENARIO_CONDITIONS : "has"
    SENSORS ||--o{ SCENARIO_CONDITIONS : "used in"
    CONDITIONS ||--o{ SCENARIO_CONDITIONS : "triggers"
    
    SCENARIOS ||--o{ SCENARIO_ACTIONS : "has"
    SENSORS ||--o{ SCENARIO_ACTIONS : "used in"
    ACTIONS ||--o{ SCENARIO_ACTIONS : "executes"

    CATEGORIES ||--o{ PRODUCTS : "contains"
    ORDERS ||--o{ ORDER_ITEMS : "includes"
    PRODUCTS ||--o{ ORDER_ITEMS : "ordered in"
```
