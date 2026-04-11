--liquibase formatted sql

--changeset akv:001-create-devices-table
CREATE TABLE devices
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NOT NULL,
    brand         VARCHAR(255) NOT NULL,
    state         VARCHAR(50)  NOT NULL,
    creation_time TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);
--rollback DROP TABLE devices;