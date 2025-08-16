CREATE SEQUENCE IF NOT EXISTS db_product_quantity_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE db_order
(
    id     UUID    NOT NULL,
    name   VARCHAR(255),
    picked BOOLEAN NOT NULL,
    CONSTRAINT pk_dborder PRIMARY KEY (id)
);

CREATE TABLE db_product
(
    id          UUID NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_dbproduct PRIMARY KEY (id)
);

CREATE TABLE db_product_quantity
(
    id         BIGINT           NOT NULL,
    order_id   UUID             NOT NULL,
    product_id UUID             NOT NULL,
    quantity   DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_dbproductquantity PRIMARY KEY (id)
);

ALTER TABLE db_product_quantity
    ADD CONSTRAINT FK_DBPRODUCTQUANTITY_ON_ORDER FOREIGN KEY (order_id) REFERENCES db_order (id);

ALTER TABLE db_product_quantity
    ADD CONSTRAINT FK_DBPRODUCTQUANTITY_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES db_product (id);