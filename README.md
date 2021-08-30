# escudo-api-server
[![Build Status](https://travis-ci.com/ekiauhce/escudo-api-server.svg?branch=master)](https://travis-ci.com/ekiauhce/escudo-api-server)

Application helps manage and analyze your repetitive expenses.

## User story

1. User can register.
2. User can log in to the application.
3. User can see product list.
4. User can create new product.
5. User can see list of product purchases.
6. User can add new purchase for the product.
7. User can delete a purchase.
8. User can see averge CPD for the particular product.
9. User can see average lifespan for the particular product. Lifespan is a time period between two adjacent purchases.
10. User can change product's name.

## API

Method | Path | Response status codes | Auth
| --- | --- | --- | --- |
`POST` | `/register` | `CREATED` or `CONFLICT` | No
`POST` |`/login` | `OK` or `UNAUTHORIZED` | No
`POST` | `/products` | `CREATED` or `CONFLICT` | require
`GET` | `/products` | `OK` | require |
`PATCH` | `products/:name` | `NO_COUNTENT` or `CONFLICT` | require
`POST` | `/products/:name/purchases` | `CREATED` or `NOT_FOUND` | require
`GET` | `/products/:name/purchases/summary` | `OK or `NOT_FOUND` | require
`DELETE` | `/products/:name/purchases/:id` | `NO_CONTENT` or `NOT_FOUND` | require

### Example payloads

#### POST `/register` request body

```json
{ "username": "user", "password": "pass" }
```

#### POST `/login` request body
```json
{ "username": "user", "password": "pass" }
```

#### POST `/products` request body 
```json
{ "name": "t-shirt" }
```

#### POST `/products` response body
```json
{
    "latestPurchaseMadeAt": null,
    "name": "t-shirt",
    "purchases": null,
    "summary": null
}

```

#### GET `/products` response body
```json
[
    {
        "latestPurchaseMadeAt": 1630175153540,
        "name": "t-shirt",
        "purchases": null,
        "summary": null
    },
    {
        "latestPurchaseMadeAt": null,
        "name": "cell-phone-plan",
        "purchases": null,
        "summary": null
    }
]
```

#### PATCH `/products/:name/` request body
```json
{ "name" : "t-shirt! 👕"} 
```

#### POST `/products/:name/purchases` request body
```json
{ "price" : 954.3 }
```

#### POST `/products/:id/purchases` response body
```json
{
    "latestPurchaseMadeAt": 1630348864884,
    "purchase": {
        "id": 1,
        "madeAt": 1630348864884,
        "price": 954.0
    },
    "summary": {
        "avgCpd": 0.0,
        "avgLifespan": 0.0
    }
}
```

#### GET `/products/:name/purchases/summary` response body
```json
{
    "avgCpd": 52.7,
    "avgLifespan": 3.23
}
```
