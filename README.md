## Create database script

```sql
create table ACCOUNTS
(
  USER_NAME VARCHAR(20) not null,
  ACTIVE    BOOLEAN not null,
  ENCRYTED_PASSWORD  VARCHAR(128) not null,
  USER_ROLE VARCHAR(20) not null
) ;

alter table ACCOUNTS
  add primary key (USER_NAME) ;
---------------------------------------
create table PRODUCTS
(
  CODE        VARCHAR(20) not null,
  IMAGE       bigint,
  NAME        VARCHAR(255) not null,
  PRICE       double precision not null,
  CREATE_DATE Timestamp without time zone not null
) ;

alter table PRODUCTS
  add primary key (CODE) ;
---------------------------------------
create table ORDERS
(
  ID               VARCHAR(50) not null,
  AMOUNT           double precision not null,
  CUSTOMER_ADDRESS VARCHAR(255) not null,
  CUSTOMER_EMAIL   VARCHAR(128) not null,
  CUSTOMER_NAME    VARCHAR(255) not null,
  CUSTOMER_PHONE   VARCHAR(128) not null,
  ORDER_DATE       Timestamp without time zone not null,
  ORDER_NUM        INT not null
) ;
alter table ORDERS
  add primary key (ID) ;
alter table ORDERS
  add constraint ORDER_UK unique (ORDER_NUM) ;
---------------------------------------
create table ORDER_DETAILS
(
  ID         VARCHAR(50) not null,
  AMOUNT     double precision not null,
  PRICE      double precision not null,
  QUANITY    INT not null,
  ORDER_ID   VARCHAR(50) not null,
  PRODUCT_ID VARCHAR(20) not null
) ;
alter table ORDER_DETAILS
  add primary key (ID) ;
alter table ORDER_DETAILS
  add constraint ORDER_DETAIL_ORD_FK foreign key (ORDER_ID)
  references ORDERS (ID);
alter table ORDER_DETAILS
  add constraint ORDER_DETAIL_PROD_FK foreign key (PRODUCT_ID)
  references PRODUCTS (CODE);
---------------------------------------  
insert into Accounts (USER_NAME, ACTIVE, ENCRYTED_PASSWORD, USER_ROLE)
values ('employee', true,
'$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_EMPLOYEE');

insert into Accounts (USER_NAME, ACTIVE, ENCRYTED_PASSWORD, USER_ROLE)
values ('admin', true,
'$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_MANAGER');
----------------
INSERT INTO products
(code, image, "name", price, create_date)
VALUES('S002', NULL, 'GTX 750 Ti', 50.0, '2022-11-26 12:14:25.445');
INSERT INTO products
(code, image, "name", price, create_date)
VALUES('S003', NULL, 'GTX 1080', 120.0, '2022-11-26 12:14:25.450');
INSERT INTO products
(code, image, "name", price, create_date)
VALUES('S004', NULL, 'RTX 3060 Ti', 120.0, '2022-11-26 12:14:25.457');
INSERT INTO products
(code, image, "name", price, create_date)
VALUES('S005', NULL, 'RTX 2080', 110.0, '2022-11-26 12:14:25.462');
INSERT INTO products
(code, image, "name", price, create_date)
VALUES('S001', NULL, 'RTX 2060 Super', 100.0, '2022-11-26 12:14:25.430');
INSERT INTO products
(code, image, "name", price, create_date)
VALUES('12231', NULL, 'RTX 3090', 300.0, '2022-11-26 12:20:20.533');