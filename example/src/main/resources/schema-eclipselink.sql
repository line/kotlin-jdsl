create table author (author_id bigint auto_increment not null primary key, name varchar(255) not null);

create table publisher (publisher_id bigint auto_increment not null primary key, name varchar(255) not null);

create table book (isbn varchar(13) not null primary key, title varchar(255) not null, image_url varchar(255) not null, publish_date datetime(6) not null, price numeric(38, 2) not null, sale_price numeric(38, 2) not null);

create table book_author (isbn varchar(13) not null, author_id bigint not null, primary key (isbn, author_id));

create table book_publisher (isbn varchar(13) not null, publisher_id bigint not null, primary key (isbn, publisher_id));

create table department (department_id bigint auto_increment not null primary key, name varchar(255) not null);

create table employee (employee_id bigint auto_increment not null primary key, employee_type varchar(31) not null, name varchar(255) not null, nickname varchar(255), phone varchar(255) not null, zip_code varchar(255) not null, street_address_1 varchar(255) not null, street_address_2 varchar(255), city varchar(255) not null, province varchar(255) not null, annual_salary numeric(38, 2), weekly_salary numeric(38, 2));

create table employee_department (employee_id bigint not null, department_id bigint not null, primary key (employee_id, department_id));
