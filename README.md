Приложение-пример операций со счетами (Spring Boot + MVC + Data)

Целевой счет создается неявно в случае отсутствия операциями внесения и перевода

Запуск при помощи gradlew bootRun
Сервисы доступны в контексте http://127.0.0.1:8080/

**Account**
----
  Returns json data about a account

* **URL**
  /accounts/:accountNumber

* **Method:**
  `GET`

*  **URL Params**
   **Required:**
   `accountNumber=[integer]`

* **Data Params**
  None

* **Success Response:**
  * **Code:** 200 <br />
    **Content:** `{ number : 123, amount : "12.34" }`

**Account Deposit**
----
  Deposit funds to account

* **URL**
  /accounts/:accountNumber/deposit

* **Method:**
  `POST`

*  **URL Params**
   **Required:**
   `accountNumber=[integer]`

* **Data Params**
  { amount : [decimal] }

* **Success Response:**
  * **Code:** 200 <br />
    **Content:** `{ number : 123, amount : "15.34" }`

**Account Withdraw**
----
  Withdraw funds from account

* **URL**
  /accounts/:accountNumber/withdraw

* **Method:**
  `POST`

*  **URL Params**
   **Required:**
   `accountNumber=[integer]`

* **Data Params**
  { amount : [decimal] }

* **Success Response:**
  * **Code:** 200 <br />
    **Content:** `{ number : 123, amount : "10.34" }`

**Account Transfer**
----
  Transfer funds to another account

* **URL**
  /accounts/:accountNumber/transfer

* **Method:**
  `POST`

*  **URL Params**
   **Required:**
   `accountNumber=[integer]`

* **Data Params**
  { amount : [decimal], targetAccountNumber : [integer] }

* **Success Response:**
  * **Code:** 200 <br />
    **Content:** `{ number : 123, amount : "10.34" }`
