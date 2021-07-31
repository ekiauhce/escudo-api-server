# escudo-api-server
[![Build Status](https://travis-ci.com/ekiauhce/escudo-api-server.svg?branch=master)](https://travis-ci.com/ekiauhce/escudo-api-server)

Application helps manage and analyze your repetitive expenses.

App loads very slowly, because frontend and backend are deployed on different heroku dynos. When a request comes to the frontend, it needs time to wake up. The frontend then makes a request to the backend, which is also asleep.

## User story

1. User can register.
2. User can log in to the application.
3. User can see product list.
4. User can create new product.
5. User can see list of product purchases.
6. User can add new purchase for the product.
7. User can delete a purchase.
8. User can see cost per day (CPD) between last purchase and new purchase, if he will add new purchase right now.
9. User can see averge CPD for the particular product.
10. User can see average lifespan for the particular product. Lifespan is a time period between two adjacent purchases.

## API

Method | Path | Response status codes | Auth
| --- | --- | --- | --- |
`POST` | `/register` | `CREATED` or `CONFLICT` | No
`POST` |`/login` | `OK` or `UNAUTHORIZED` | No
`POST` | `/products` | `CREATED` or `CONFLICT` | require
`GET` | `/products` | `OK` | require |
`POST` | `/products/:id/purchases` | `CREATED` or `NOT_FOUND` or `FORBIDDEN` | require
`DELETE` | `products/:id/purchases/:id` | `NO_CONTENT` or `NOT_FOUND` or `FORBIDDEN` | require

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
    "id": 1,
    "name": "t-shirt",
    "purchases": []
}
```

#### GET `/products` response body
```json
[
    {
        "id": 1,
        "name": "t-shirt",
        "purchases": [
            {
                "id": 1,
                "madeAt": "2021-06-15T11:59:19.273625",
                "price": 950.0
            }
        ]
    },
    {
        "id": 2,
        "name": "cell phone plan",
        "purchases": [
            {
                "id": 2,
                "madeAt": "2021-06-20T13:28:30.350744",
                "price": 350.0
            },
            {
                "id": 3,
                "madeAt": "2021-07-20T15:28:30.350744",
                "price": 350.0
            }
        ]
    }
]
```

#### POST `/products/:id/purchases` request body
```json
{ "price" : 954.3 }
```

#### POST `/products/:id/purchases` response body
```json
{
    "id": 1,
    "madeAt": "2021-07-31T11:59:19.273625",
    "price": 954.3
}
```
