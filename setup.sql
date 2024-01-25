CREATE DATABASE IF NOT EXISTS demo;
create table IF NOT EXISTS demo.product (
    id bigint not null primary key,
    barcode varchar(255) null,
    description varchar(255) null,
    price decimal(38, 2) null
);
create table demo.client_order (
    id bigint auto_increment primary key,
    client varchar(255) null
);
create table demo.order_product (
    id bigint auto_increment primary key,
    quantity int null,
    order_id bigint null,
    product_id bigint null,
    constraint client_order_fk foreign key (order_id) references demo.client_order (id),
    constraint product_fk foreign key (product_id) references demo.product (id)
);

INSERT INTO demo.product (id, barcode, description, price)
VALUES (1, '1000000001', 'Apple iPhone 13', 799.99),
    (2, '1000000002', 'Samsung Galaxy S21', 699.99),
    (
        3,
        '1000000003',
        'Sony WH-1000XM4 Headphones',
        349.99
    ),
    (
        4,
        '1000000004',
        'Apple MacBook Pro 14-inch',
        1999.99
    ),
    (5, '1000000005', 'Dell XPS 13 Laptop', 999.99),
    (
        6,
        '1000000006',
        'Logitech MX Master 3 Mouse',
        99.99
    ),
    (7, '1000000007', 'Anker USB-C Hub', 45.99),
    (
        8,
        '1000000008',
        'Amazon Echo Dot (4th Gen)',
        49.99
    ),
    (
        9,
        '1000000009',
        'Google Nest Wifi Router',
        169.99
    ),
    (
        10,
        '1000000010',
        'Samsung T7 Portable SSD - 1TB',
        109.99
    ) ON DUPLICATE KEY
UPDATE barcode =
VALUES(barcode),
    description =
VALUES(description),
    price =
VALUES(price);