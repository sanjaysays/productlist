create table if not exists product (
    id int AUTO_INCREMENT  PRIMARY KEY,
    name varchar(300) not null,
    description varchar(1000),
    price double not null,
    primary key (id)
);