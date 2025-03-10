-- drop if exists database expensetrackerdb;
-- drop user if exists expensetracker_owner;
create user expensetracker_owner with password 'et_passwd';
create database expensetrackerdb with template=template0 owner=expensetracker_owner;
\connect expensetrackerdb;

create table et_users(
user_id serial primary key not null,
first_name varchar(20) not null,
last_name varchar(20) not null,
email varchar(30) not null,
password text not null
);

create table et_categories(
category_id serial primary key not null,
user_id integer not null,
title varchar(30) not null,
description varchar(255) not null
);
alter table et_categories add constraint cat_users_fk
foreign key (user_id) references et_users(user_id);

create table et_transactions(
transaction_id serial primary key not null,
category_id integer not null,
user_id integer not null,
amount numeric(11,2) not null,
note varchar(255) not null,
transaction_date timestamp without time zone not null
);
alter table et_transactions add constraint trans_cat_fk
foreign key (category_id) references et_categories(category_id);
alter table et_transactions add constraint trans_users_fk
foreign key (user_id) references et_users(user_id);


GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO expensetracker_owner;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO expensetracker_owner;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO expensetracker_owner;