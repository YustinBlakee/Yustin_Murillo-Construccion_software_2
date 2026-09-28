# NexusMarket Business Specification

## 1. Overview

NexusMarket is a business management system for a digital marketplace focused on product catalog management, customer registration, and order processing. The platform allows authorized users to register categories, manage products, maintain customer information, and create purchase orders while validating stock and computing totals.

## 2. Business Objectives

The system aims to:

- Organize and maintain a product catalog by category.
- Register market users with valid contact data.
- Ensure that product information remains consistent and validated.
- Control inventory levels before creating an order.
- Calculate order totals based on item quantity and unit price.
- Prevent duplicate or invalid records in the core business entities.

## 3. Main Entities

### 3.1 Category

A category represents a classification group for products.

Attributes:
- id: unique identifier
- name: category name, required and unique

Business rules:
- The category name cannot be empty.
- Category names must be unique, ignoring case.
- A category can be created, updated, consulted, and deleted.

### 3.2 Product

A product is an item sold through the marketplace.

Attributes:
- id: unique identifier
- name: required product name
- description: optional descriptive text
- price: unit price, zero or positive
- stock: available quantity, zero or positive
- category: associated category

Business rules:
- The product name is mandatory.
- The price must be zero or greater.
- The stock quantity must be zero or greater.
- Each product must belong to an existing category.
- Product information must be validated before creation or update.

### 3.3 Market User

A market user represents a customer or user registered in the system.

Attributes:
- id: unique identifier
- name: required user name
- email: required valid email, unique

Business rules:
- The name cannot be blank.
- The email must be valid.
- The email must be unique across the system.

### 3.4 Purchase Order

A purchase order is a commercial transaction created by a user and containing one or more products.

Attributes:
- id: unique identifier
- user: customer or buyer associated with the order
- status: order status
- total: products total amount
- items: list of order items

Order statuses:
- PENDING
- CONFIRMED
- CANCELLED

Business rules:
- An order must contain at least one product.
- A user must exist for the order.
- Each item must include a valid product identifier and a positive quantity.
- Product stock is checked before order creation.
- Stock is reduced when the order is created.
- The total is the sum of the product price multiplied by the requested quantity.

### 3.5 Order Item

An order item represents a product included within a purchase order.

Attributes:
- id: unique identifier
- order: associated purchase order
- product: selected product
- quantity: requested quantity
- unitPrice: price at the time of the purchase

Business rules:
- Quantity must be greater than zero.
- The product must exist in the catalog.
- The product price is stored in the line item for traceability.

## 4. Functional Requirements

### 4.1 Category Management

The system must allow:
- Creating categories.
- Updating categories.
- Viewing all categories.
- Viewing a category by identifier.
- Deleting a category.

Validation rules:
- Category names are required.
- Duplicate names are not allowed.
- Empty or blank names are rejected.

### 4.2 Product Management

The system must allow:
- Creating products associated to a category.
- Updating product data.
- Viewing all products.
- Viewing a product by identifier.
- Deleting a product.

Validation rules:
- Name is required.
- Price must be zero or positive.
- Stock must be zero or positive.
- Category must exist before assigning the product.

### 4.3 User Management

The system must allow:
- Creating users.
- Updating users.
- Viewing all users.
- Viewing a user by identifier.
- Deleting users.

Validation rules:
- Name cannot be blank.
- Email is mandatory and must be valid.
- Email addresses must be unique.

### 4.4 Purchase Order Management

The system must allow:
- Creating a purchase order for a registered user.
- Listing all orders.
- Viewing an order by identifier.

Process flow:
1. The user provides a valid user ID and one or more requested products.
2. The system validates that the list is not empty.
3. The system verifies that the user exists.
4. For each product requested:
   - The product ID must be valid.
   - The item quantity must be greater than zero.
   - The requested stock must be available.
5. If stock is sufficient, inventory is reduced.
6. Each item is added to the order with its corresponding unit price.
7. The order total is computed.
8. The order is saved.

## 5. Business Constraints

- Inventory cannot be negative.
- An order cannot be created with no items.
- Product and user identifiers must be valid when referenced.
- Duplicate emails and category names are rejected.
- Only valid numeric values are accepted for price and stock.

## 6. Error Handling

The system must handle invalid requests with clear exceptions, including:
- bad request errors for invalid data
- not found errors when a resource does not exist
- conflict errors when inventory is insufficient or a duplicate record is found

## 7. Technical Notes

This application is implemented in Java with Spring Boot and Spring Data JPA. The persistence layer uses an in-memory H2 database during execution. Business logic is centralized in service classes, which enforce the validation rules and domain constraints.

## 8. Summary

NexusMarket is designed to manage the essential operations of a marketplace: categories, products, users, and purchase orders. The system enforces data integrity, validates stock availability, and calculates totals, ensuring a reliable foundation for future REST API and UI layers.
