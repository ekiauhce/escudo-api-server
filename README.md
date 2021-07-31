# escudo-api-server
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
