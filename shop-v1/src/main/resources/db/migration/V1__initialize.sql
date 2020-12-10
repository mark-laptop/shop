CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    version  BIGINT                NOT NULL,
    username VARCHAR(255)          NOT NULL,
    password VARCHAR(255)          NOT NULL
);
CREATE INDEX IX_users_username ON users (username);

CREATE TABLE roles
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    version  BIGINT                NOT NULL,
    name VARCHAR(255)          NOT NULL
);
CREATE INDEX IX_roles_name ON roles (name);

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    UNIQUE (user_id, role_id)
);

CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    version     BIGINT                NOT NULL,
    title       VARCHAR(255)          NOT NULL,
    description VARCHAR(255),
    price       DECIMAL(15, 2)
);
CREATE INDEX IX_products_title ON products (title);

CREATE TABLE categories
(
    id      BIGSERIAL PRIMARY KEY NOT NULL,
    version BIGINT                NOT NULL,
    title   VARCHAR(255)          NOT NULL
);
CREATE INDEX IX_categories_title ON categories (title);

CREATE TABLE categories_products
(
    product_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (category_id) REFERENCES categories (id),
    PRIMARY KEY (category_id, product_id)
);

CREATE TABLE customers
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    version    BIGINT                NOT NULL,
    first_name VARCHAR(255)          NOT NULL,
    last_name  VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL
);
CREATE INDEX IX_customers_first_name ON customers (first_name);
CREATE INDEX IX_customers_last_name ON customers (last_name);
CREATE INDEX IX_customers_first_name_last_name ON customers (first_name, last_name);
CREATE INDEX IX_customers_email ON customers (email);

CREATE TABLE orders
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    version     BIGINT                NOT NULL,
    customer_id BIGINT                NOT NULL,
    recipient   VARCHAR(255),
    address     VARCHAR(255),
    sum         DECIMAL(15, 2),
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);
CREATE INDEX IX_orders_recipient ON orders (recipient);
CREATE INDEX IX_orders_address ON orders (address);

CREATE TABLE order_items
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    version    BIGINT                NOT NULL,
    order_id   BIGINT                NOT NULL,
    product_id BIGINT                NOT NULL,
    quantity   DECIMAL(15, 3)        NOT NULL,
    price      DECIMAL(15, 2)        NOT NULL,
    sum        DECIMAL(15, 2)        NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

INSERT INTO users (version, username, password)
VALUES
       (0, 'user', '$2y$12$m0reT42iKtR8v/BoS4hykuZpQI9dFx.cj1u2t/R4sAEA7Qqoygqfe'),
       (0, 'admin', '$2y$12$m0reT42iKtR8v/BoS4hykuZpQI9dFx.cj1u2t/R4sAEA7Qqoygqfe');

INSERT INTO roles (version, name)
VALUES
       (0, 'ROLE_USER'),
       (0, 'ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id)
VALUES
       (1, 1),
       (2, 2);

INSERT INTO products (version, title, description, price)
VALUES (0, 'Product 1', 'Product 1', 1000),
       (0, 'Product 2', 'Product 2', 2000),
       (0, 'Product 3', 'Product 3', 3000),
       (0, 'Product 4', 'Product 4', 4000),
       (0, 'Product 5', 'Product 5', 5000),
       (0, 'Product 6', 'Product 6', 6000),
       (0, 'Product 7', 'Product 7', 7000),
       (0, 'Product 8', 'Product 8', 8000),
       (0, 'Product 9', 'Product 9', 9000),
       (0, 'Product 10', 'Product 10', 10000),
       (0, 'Product 11', 'Product 11', 11000);

INSERT INTO categories (version, title)
VALUES (0, 'Category 1'),
       (0, 'Category 2');

INSERT INTO categories_products (product_id, category_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 2),
       (7, 2),
       (8, 2),
       (9, 2),
       (10, 1),
       (11, 2);

INSERT INTO customers (version, first_name, last_name, email)
VALUES (0, 'Customer 1', 'Customer 1', 'email@customer1.ru'),
       (0, 'Customer 2', 'Customer 2', 'email@customer2.ru'),
       (0, 'Customer 3', 'Customer 3', 'email@customer3.ru'),
       (0, 'Customer 4', 'Customer 4', 'email@customer4.ru'),
       (0, 'Customer 5', 'Customer 5', 'email@customer5.ru'),
       (0, 'Customer 6', 'Customer 6', 'email@customer6.ru'),
       (0, 'Customer 7', 'Customer 7', 'email@customer7.ru'),
       (0, 'Customer 8', 'Customer 8', 'email@customer8.ru'),
       (0, 'Customer 9', 'Customer 9', 'email@customer9.ru'),
       (0, 'Customer 10', 'Customer 10', 'email@customer10.ru');