shop_test
=========

A Symfony project created on November 5, 2017, 2:30 pm.


## Database
#### configure database connection
```
# app/config/parameters.yml
parameters:
    database_host:     localhost
    database_name:     test_project
    database_user:     root
    database_password: password
```

#### Initial Database
```
php bin/console doctrine:database:create
```

```
php bin/console doctrine:schema:update --force
```

Then add some data in the commodity table.

## Stripe 
#### create a new stripe account

#### get the public key & private key from https://dashboard.stripe.com/account/apikeys
- Set the privare key in ChargeController.php 
- Set the public key in shoppingcart.html.twig

## Test
Start server (In production environment, please don't use this)
```
php bin/console server:run
```
Go to http://localhost:8000/shop
- Add items
- click 'check shopping cart'
- key in test card as 4111111111111111,  11/19, 111
- get the payment result
- check the Stripe dashboard to see the payment record.
- check the database trx_order table for order information
