# Brokage Firm API

This project provides a RESTful API for a brokerage firm, allowing customers to register, create orders, manage assets, and match orders as an admin.

## Endpoints

### Customer Endpoints

#### Register a Customer
Registers a new customer. If `isAdmin` is `true`, the customer will have admin privileges.

```http
POST http://localhost:9090/customers/register
```

##### Request Body
```json
{
  "username": "user1",
  "password": "password1",
  "isAdmin": false
}
```

```json
{
  "username": "user3",
  "password": "password1",
  "isAdmin": true
}
```
#### Get all the Customers
```http
GET http://localhost:9090/customers
```

Gets the list of all customers.

---

### Asset Endpoints

#### Create an Asset
Allows customers to add assets to their accounts.

```http
POST http://localhost:9090/assets
```

##### Request Body
```json
{
    "customerId": 2,
    "assetName": "TRY",
    "size": 1238,
    "usableSize": 1238
}
```

```json
{
    "customerId": 1,
    "assetName": "TRY",
    "size": 5000,
    "usableSize": 5000    
}
```
#### Get all the Assets
```http
GET http://localhost:9090/assets
```
Gets all the assets.

---

### Order Endpoints

#### Create an Order
Customers can place buy or sell orders.

```http
POST http://localhost:9090/orders
```

##### Request Body
```json
{
    "customerId": 1,
    "assetName": "Bitcoin",
    "side": "BUY",
    "size": 10,
    "price": 50
}
```

```json
{
    "customerId": 2,
    "assetName": "Bitcoin",
    "side": "BUY",
    "size": 1,
    "price": 100
}
```

#### Get all the Orders
```http
GET http://localhost:9090/orders
```
#### Cancel the Order
```http
DELETE http://localhost:9090/orders/{orderId}
```

---

### Admin Endpoints

#### Match Orders (Admin Only)
Admin users can match pending orders and update asset values accordingly.

```http
PUT http://localhost:9090/admin/match-orders/1
```

---

## Running the Application

1. Clone the repository:
   ```sh
   git clone https://github.com/fsemiz/Brokage-Firm.git
   ```
2. Navigate to the project directory:
   ```sh
   cd Brokage-Firm
   ```
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```
4. The API will be available at `http://localhost:9090/`.

---

## Notes
- Orders will not be matched against each other but will be processed sequentially by an admin.
- The system tracks both `size` and `usableSize` for each asset, ensuring correct balance management.
- Spring security default username password are admin and admin respectively
