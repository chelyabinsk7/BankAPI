DROP TABLE IF EXISTS accountTransactions;
DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS contractors;
DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS owners;
DROP TABLE IF EXISTS test;


CREATE TABLE owners(
    id IDENTITY NOT NULL PRIMARY KEY,
    type_owner VARCHAR(3) NOT NULL,
    CHECK (type_owner IN ('FIZ', 'UR'))
);

CREATE TABLE persons(
    id_person INT PRIMARY KEY,
    firstName VARCHAR(70) NOT NULL,
    lastName VARCHAR(70) NOT NULL,
    birthday DATE NOT NULL,
    FOREIGN KEY (id_person) REFERENCES owners (id) ON UPDATE CASCADE
);

CREATE TABLE contractors(
    id_contractor INT PRIMARY KEY,
    name VARCHAR(70) NOT NULL,
    inn VARCHAR(10) NOT NULL UNIQUE,
    FOREIGN KEY (id_contractor) REFERENCES owners (id) ON UPDATE CASCADE
);

CREATE TABLE accounts(
    id IDENTITY NOT NULL PRIMARY KEY,
    number VARCHAR(20) NOT NULL UNIQUE,
    id_owner INT,
    balance DECIMAL DEFAULT 0,
    status_account VARCHAR(5) DEFAULT 'OPEN',
    CHECK (status_account IN ('OPEN', 'CLOSE')),
    FOREIGN KEY (id_owner) REFERENCES owners (id) ON UPDATE CASCADE
);

CREATE TABLE cards(
    id IDENTITY NOT NULL PRIMARY KEY,
    number VARCHAR(16) NOT NULL UNIQUE,
    id_account INT,
    status_card VARCHAR(5) DEFAULT 'OPEN',
    CHECK (status_card IN ('OPEN', 'CLOSE')),
    FOREIGN KEY (id_account) REFERENCES accounts (id) ON UPDATE CASCADE
);

CREATE TABLE accountTransactions(
    id IDENTITY NOT NULL PRIMARY KEY,
    id_from INT NOT NULL,
    id_to INT NOT NULL,
    amount DECIMAL,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL,
    status_transaction VARCHAR(3) DEFAULT NULL,
    message VARCHAR(255) DEFAULT NULL,
    CHECK (status_transaction IN ('YES', 'NO', NULL)),
    FOREIGN KEY (id_from) REFERENCES accounts(id) ON UPDATE CASCADE,
    FOREIGN KEY (id_to) REFERENCES accounts(id) ON UPDATE CASCADE
);

CREATE TABLE test(
    id IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    age INT
);