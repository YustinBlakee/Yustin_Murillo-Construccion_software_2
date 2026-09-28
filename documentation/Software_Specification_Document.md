# NexusMarket Software Specification Document

## 1. Document Purpose

This Software Specification Document (SSD) defines the software scope, actors, functional and non-functional requirements, data requirements, interfaces, constraints, and acceptance criteria for NexusMarket. It complements the project's Business Specification by describing requirements for the software solution rather than repeating only its business rules.

## 2. Product Overview

NexusMarket is a marketplace business application for managing product categories, products, market users, and purchase orders. The current implementation is a Java 17 and Spring Boot application using Spring Data JPA and an in-memory H2 database. Business operations are implemented in service classes.

The current project does not include REST controllers or a graphical user interface. Therefore, the requirements below describe the service-layer capabilities that exist in the project and identify the missing external interface as a project limitation, not as an already available feature.

## 3. Scope

### 3.1 In Scope

- Create, update, retrieve, and delete categories.
- Create, update, retrieve, and delete products associated with categories.
- Create, update, retrieve, and delete market users.
- Create purchase orders and retrieve orders by identifier or as a list.
- Validate business data and references before persistence.
- Check and decrease product stock during successful order creation.
- Calculate and store order totals and the unit price used for each order item.
- Persist domain data through Spring Data JPA.

### 3.2 Out of Scope

- REST endpoints, web pages, or another end-user interface.
- Authentication, authorization, and role management.
- Payment processing, shipping, and delivery tracking.
- Order update, cancellation, or deletion operations.
- Reporting, notifications, and external integrations.

## 4. Stakeholders and Actors

- **Administrator/operator:** manages categories, products, and market user records through the application services.
- **Customer/market user:** is associated with a purchase order. Direct customer interaction with the software is not currently provided.
- **Application:** validates requests, applies marketplace rules, and persists data.

## 5. Functional Requirements

### 5.1 Category Management

- **FR-CAT-01:** The application shall create a category with a non-blank name.
- **FR-CAT-02:** The application shall reject category names that duplicate an existing name, ignoring case.
- **FR-CAT-03:** The application shall update a category by identifier and apply the same name validation as creation.
- **FR-CAT-04:** The application shall retrieve a category by identifier and retrieve all categories.
- **FR-CAT-05:** The application shall delete a category by identifier.
- **FR-CAT-06:** The application shall report a not-found error when an operation references a category identifier that does not exist.

### 5.2 Product Management

- **FR-PROD-01:** The application shall create a product with a non-blank name, a non-negative price, a non-negative stock quantity, and an existing category.
- **FR-PROD-02:** The application shall update a product's name, description, price, stock, and category after validating the supplied values and category reference.
- **FR-PROD-03:** The application shall retrieve a product by identifier and retrieve all products.
- **FR-PROD-04:** The application shall delete a product by identifier.
- **FR-PROD-05:** The application shall reject a missing or non-existent category reference and report a not-found error for an unknown product identifier.

### 5.3 Market User Management

- **FR-USER-01:** The application shall create a market user with a non-blank name and a non-blank email address.
- **FR-USER-02:** The application shall reject an email address that is already assigned to another user, ignoring case.
- **FR-USER-03:** The application shall normalize stored user names by trimming surrounding whitespace and email addresses by trimming whitespace and converting them to lowercase.
- **FR-USER-04:** The application shall update a user's name and email after applying the same validation and uniqueness checks as creation.
- **FR-USER-05:** The application shall retrieve a user by identifier and retrieve all users.
- **FR-USER-06:** The application shall delete a user by identifier and report a not-found error for an unknown user identifier.

### 5.4 Purchase Order Management

- **FR-ORDER-01:** The application shall create an order for an existing user and require at least one order item.
- **FR-ORDER-02:** Each requested order item shall contain an existing product identifier and a quantity greater than zero.
- **FR-ORDER-03:** The application shall reject an order if any requested quantity exceeds the corresponding available stock.
- **FR-ORDER-04:** When order creation succeeds, the application shall decrease each product's stock by its ordered quantity.
- **FR-ORDER-05:** The application shall store each order item's product, quantity, and unit price at the time of order creation.
- **FR-ORDER-06:** The application shall calculate the order total as the sum of each unit price multiplied by its quantity.
- **FR-ORDER-07:** The application shall retrieve an order by identifier and retrieve all orders.
- **FR-ORDER-08:** The application shall report a not-found error when the requested user, product, or order does not exist.
- **FR-ORDER-09:** Order creation shall execute transactionally so a failed operation does not leave partially applied order and inventory changes.

## 6. Data Requirements

The system shall persist the following core entities:

- **Category:** generated identifier and unique, non-blank name.
- **Product:** generated identifier, name, optional description, non-negative decimal price, non-negative stock quantity, and category reference.
- **Market user:** generated identifier, name, and unique email address.
- **Purchase order:** generated identifier, user reference, status, total, and one or more order items. New orders have `PENDING` status.
- **Order item:** generated identifier, order reference, product reference, positive quantity, and unit price captured at order creation.

The order total uses decimal arithmetic. Product and order references must point to existing records.

## 7. External Interface Requirements

### 7.1 User Interface

No graphical user interface is included in the current project.

### 7.2 Software Interface

The application is implemented with Java 17 and Spring Boot 3.5.6. Its business operations are exposed through Spring service classes and use Spring Data JPA repositories for persistence. No REST controllers are currently included, so HTTP endpoints and request/response contracts are not defined by this version of the project.

### 7.3 Database Interface

The application uses an in-memory H2 database with Hibernate-managed schema updates. Data is not intended to persist after the application process stops. The H2 console is enabled in the current configuration.

## 8. Error Handling Requirements

The application shall distinguish among:

- **Bad request:** missing, malformed, or invalid input.
- **Not found:** a referenced category, product, user, or order does not exist.
- **Conflict:** duplicate category or email data, insufficient stock, or a persistence integrity conflict.

The project includes centralized exception mapping to HTTP `ProblemDetail` responses. Since REST controllers are not present, HTTP behavior is not yet available as a complete external interface.

## 9. Non-Functional Requirements

- **NFR-01 Data integrity:** invalid data and references shall be rejected before they are persisted.
- **NFR-02 Transaction consistency:** order creation and associated stock changes shall be handled within a transaction.
- **NFR-03 Maintainability:** business rules shall remain in service classes and persistence access shall remain in repository classes.
- **NFR-04 Portability:** the application shall run on Java 17 or later with Maven and the configured Spring Boot dependencies.
- **NFR-05 Error clarity:** business failures shall be represented by specific exception categories and useful error details.
- **NFR-06 Persistence scope:** the current H2 in-memory configuration is suitable for development and demonstration; durable production storage is not included in this version.

## 10. Constraints and Known Gaps

- The application has no REST controllers or graphical interface; service methods are the current interaction boundary.
- Authentication and authorization are not implemented.
- The H2 database is in memory and loses its data when the application stops.
- Current email validation checks that the value is non-blank and contains `@`; full email-format validation remains a gap against the broader requirement that emails be valid.
- Category, product, and user services provide deletion operations, but behavior when deleting records referenced by other entities depends on persistence constraints and is not specified as a user-facing workflow.

## 11. Acceptance Criteria

The software meets its current functional scope when:

1. Category, product, and user service operations create, update, retrieve, and delete valid records.
2. Duplicate category names and user emails are rejected without regard to letter case.
3. Products cannot be stored with a blank name, negative price, negative stock, or missing category.
4. An order cannot be created without an existing user and at least one valid item.
5. Invalid item quantities, missing products, and insufficient stock prevent order creation.
6. A successful order stores item prices, computes the correct total, and reduces stock by the requested quantities.
7. Failed order creation does not leave partial inventory changes.
8. Missing resources and conflicting data are reported using the appropriate exception category.
9. The documented interface limitation is respected: no HTTP endpoint or user interface is assumed to exist in the current version.

## 12. Traceability

This SSD complements the business rules and entity descriptions in `Business_Specification_Professor_English.md`. Functional requirements map to the current service classes: `CategoryService`, `ProductService`, `MarketUserService`, and `PurchaseOrderService`. Requirements concerning REST endpoints, a graphical interface, authentication, and durable production storage are outside the current implementation.
