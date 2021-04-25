CREATE TABLE USER
(
    id   int auto_increment primary key not null,
    name varchar(255)
);


CREATE TABLE DEVICE
(
    id   int auto_increment primary key not null,
    name varchar(255),
    link varchar(255)
);

CREATE TABLE GPS
(
    id            int auto_increment primary key not null,
    file_path     varchar(255),
    uploaded_date timestamp ,
    user_id       int not null,
    description   varchar,
    name          varchar(255),
    device_id     int not null,
    FOREIGN KEY (user_id) REFERENCES User (id),
    FOREIGN KEY (device_id) REFERENCES DEVICE (id)
);

CREATE TABLE TRACK
(
    id        int auto_increment primary key not null,
    latitude  real,
    longitude real,
    ele       varchar,
    gps_id    int,
    FOREIGN KEY (gps_id) references GPS (id),
    time      timestamp
);

CREATE TABLE WAYPOINT
(
    ID        INT auto_increment primary key not null,
    name      varchar(255),
    latitude  real,
    longitude real,
    sym       varchar(255),
    gps_id    int not null,
    FOREIGN KEY (gps_id) references GPS (id)
);

CREATE SEQUENCE GPS_SEQUENCE_ID START WITH (select max(ID) + 1 from GPS);
CREATE SEQUENCE USERS_SEQUENCE_ID START WITH (select max(ID) + 1 from USER);

INSERT INTO USER (name)
values ('Anonymous');
