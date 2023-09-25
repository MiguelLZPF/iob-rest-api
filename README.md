# Spring Boot Web REST API

This is a simple example of a **Blockchain compatible API REST server** in Spring Boot. Blockchain is a distributed ledger technology that allows for secure and transparent transactions among multiple parties without intermediaries. ERC20 is a standard interface for tokens that run on the Ethereum blockchain. There are two main resources in this project: Users and Accounts.

- Users are basic username and password objects with added credentials (cryptographic keys) to be able to interact with Blockchain. They can use their keys to sign transactions and verify their identity on the network.
- Accounts represent an account resource bound to the users and to a [Simple ERC20] deployed on a Blockchain. They can perform the following actions:
    - Deposit: Users can deposit ERC20 tokens from their external wallet to their account on the server.
    - Transfer: Users can transfer ERC20 tokens from their account on the server to another user's account or to an external address.
    - Get account details: Users can check their account balance, transaction history, and other details.

## Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- MongoDB 4.4.9 or higher

## Mongo And Ganache

You can start a compatible Blockchain and Database to test it by running `docker compose up -d`.

You will need a Mongo database and set a user with the required permissions

## Build the project with Maven and run the application.

```bash
  mvn clean install
  mvn spring-boot:run

```
**You can access a Swagger UI at http://localhost:8080/swagger-ui/index.html**
