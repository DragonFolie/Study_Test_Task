


CREATE SCHEMA IF NOT EXISTS `synergy` DEFAULT CHARACTER SET utf8 ;
USE `synergy` ;


create table air_company
(
    id           int auto_increment
        primary key,
    name         varchar(45) not null,
    company_type varchar(45) not null,
    founded_at   date        not null
);

create table airplane
(
    id                    int auto_increment
        primary key,
    name                  varchar(45) not null,
    factory_serial_number int         not null,
    number_of_flight      varchar(45) not null,
    flight_distance       int         not null,
    fuel_capacity         int         not null,
    type                  varchar(45) not null,
    created_at            date        not null,
    air_company_id        int         not null,
    constraint `fk_Airplane_Air Company`
        foreign key (air_company_id) references air_company (id)
            on update cascade on delete cascade
);

create index `fk_Airplane_Air Company_idx`
    on airplane (air_company_id);

create table flight
(
    id                    int auto_increment
        primary key,
    flight_status         varchar(45) not null,
    distance              int         not null,
    departure_country     varchar(45) not null,
    destination_country   varchar(45) not null,
    created_at            datetime    not null,
    ended_at              datetime    not null,
    estimated_flight_time time        not null,
    delay_started_at      datetime    null,
    airplane_id           int         not null,
    air_Company_id        int         not null,
    constraint `fk_Flight_Air Company1`
        foreign key (air_Company_id) references air_company (id)
            on update cascade on delete cascade,
    constraint fk_Flight_Airplane1
        foreign key (airplane_id) references airplane (id)
            on update cascade on delete cascade
);

create index `fk_Flight_Air Company1_idx`
    on flight (air_Company_id);

create index fk_Flight_Airplane1_idx
    on flight (airplane_id);

INSERT INTO synergy.air_company (id, name, company_type, founded_at) VALUES (7, 'Turkish Airlines', 'type1', '5555-07-04');
INSERT INTO synergy.air_company (id, name, company_type, founded_at) VALUES (8, 'Qatar Airways', 'type2', '2008-07-04');
INSERT INTO synergy.air_company (id, name, company_type, founded_at) VALUES (9, 'Emirates Airline', 'type3', '2005-07-04');
INSERT INTO synergy.airplane (id, name, factory_serial_number, number_of_flight, flight_distance, fuel_capacity, type, created_at, air_company_id) VALUES (7, 'Boeing 787 Dreamliner', 787001, 'EK77', 7800, 101000, 'Long Distance', '2001-05-05', 7);
INSERT INTO synergy.airplane (id, name, factory_serial_number, number_of_flight, flight_distance, fuel_capacity, type, created_at, air_company_id) VALUES (8, 'Boeing 787 Dreamliner', 787002, 'EK96', 8200, 101000, 'Long Distance', '2002-05-05', 7);
INSERT INTO synergy.airplane (id, name, factory_serial_number, number_of_flight, flight_distance, fuel_capacity, type, created_at, air_company_id) VALUES (9, 'Airbus a330', 315, 'EK64', 5600, 139000, 'Long Distance', '2003-05-05', 7);
INSERT INTO synergy.airplane (id, name, factory_serial_number, number_of_flight, flight_distance, fuel_capacity, type, created_at, air_company_id) VALUES (10, 'Airbus a321', 321, 'EK25', 3200, 24050, 'Long Distance', '2004-05-05', 7);
INSERT INTO synergy.airplane (id, name, factory_serial_number, number_of_flight, flight_distance, fuel_capacity, type, created_at, air_company_id) VALUES (11, 'TEST', 99, 'EK77', 555, 50, 'Long Distance', '2000-05-05', 9);
INSERT INTO synergy.flight (id, flight_status, distance, departure_country, destination_country, created_at, ended_at, estimated_flight_time, delay_started_at, airplane_id, air_Company_id) VALUES (4, 'ACTIVE', 505, 'UA', 'USA', '2021-12-30 04:00:00', '2021-12-28 05:00:00', '01:00:00', null, 8, 7);
INSERT INTO synergy.flight (id, flight_status, distance, departure_country, destination_country, created_at, ended_at, estimated_flight_time, delay_started_at, airplane_id, air_Company_id) VALUES (5, 'ACTIVE', 505, 'PL', 'UA', '2021-12-28 04:20:00', '2021-12-28 05:00:00', '00:40:00', null, 8, 8);
INSERT INTO synergy.flight (id, flight_status, distance, departure_country, destination_country, created_at, ended_at, estimated_flight_time, delay_started_at, airplane_id, air_Company_id) VALUES (6, 'DELAYED', 505, 'FR', 'PL', '2021-12-28 04:40:00', '2021-12-28 05:35:00', '01:00:00', '2021-12-28 04:40:00', 8, 8);
INSERT INTO synergy.flight (id, flight_status, distance, departure_country, destination_country, created_at, ended_at, estimated_flight_time, delay_started_at, airplane_id, air_Company_id) VALUES (7, 'COMPLETED', 505, 'UK', 'UK', '2021-12-28 04:40:00', '2021-12-28 05:10:00', '00:10:00', null, 10, 8);