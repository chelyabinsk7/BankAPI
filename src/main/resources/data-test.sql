INSERT INTO OWNERS (TYPE_OWNER) VALUES ('FIZ'),
                                       ('FIZ'),
                                       ('FIZ'),
                                       ('FIZ'),
                                       ('FIZ'),
                                       ('UR'),
                                       ('UR'),
                                       ('UR');

INSERT INTO PERSONS (id_person, FIRSTNAME, LASTNAME, BIRTHDAY) VALUES(1, 'Nastya', 'Morar', '1995-06-23'),
                                                                     (2, 'Stas', 'Sviridov', '1992-09-11'),
                                                                     (3, 'Sasha', 'Zhavo', '1993-11-28'),
                                                                     (4, 'Alina', 'Ismagilova', '1994-12-02'),
                                                                     (5, 'Vlad', 'Aparin', '1997-03-07');

INSERT INTO contractors (id_contractor, name, inn) VALUES (6, 'Granit', '1234567890'),
                                                          (7, 'IBS', '5674209809'),
                                                          (8, 'COFF', '1734507820');

INSERT INTO ACCOUNTS (NUMBER, ID_OWNER, BALANCE) VALUES ('12345672901234567890', 1, 1000);
INSERT INTO ACCOUNTS (NUMBER, ID_OWNER) VALUES ('12345672901234567891', 1);
INSERT INTO ACCOUNTS (NUMBER, ID_OWNER) VALUES ('12345678906204567892', 2);
INSERT INTO ACCOUNTS (NUMBER, ID_OWNER) VALUES ('12345678901234567893', 3);
INSERT INTO ACCOUNTS (NUMBER, ID_OWNER, BALANCE) VALUES ('12345678901234567894', 4, 500);
INSERT INTO ACCOUNTS (NUMBER, ID_OWNER) VALUES ('12345678901234567895', 5);
INSERT INTO ACCOUNTS (NUMBER, ID_OWNER) VALUES ('15345678901234567896', 6);
INSERT INTO ACCOUNTS (NUMBER, ID_OWNER, BALANCE) VALUES ('12635678901234567897', 7, 200000);
INSERT INTO ACCOUNTS (NUMBER, ID_OWNER, BALANCE) VALUES ('12345678901234367898', 7, 8000);
INSERT INTO ACCOUNTS (NUMBER, ID_OWNER) VALUES ('12345678961234567899', 8);

INSERT INTO CARDS (NUMBER, ID_ACCOUNT, STATUS_CARD) VALUES ('1234567812345677', 1, 'OPEN'),
                                                           ('1234567812345678', 1, 'CREATED'),
                                                           ('1234567812345679', 1, 'CLOSE'),
                                                           ('1234567887654321', 3, 'OPEN'),
                                                           ('1234567891234567', 5, 'OPEN');

INSERT INTO ACCOUNTTRANSACTIONS (ID_FROM, ID_TO, AMOUNT) VALUES (1, 2, 1000),
                                                                (1, 2, 299.9),
                                                                (8, 9, 10),
                                                                (8, 10, 500),
                                                                (8, 5, 100),
                                                                (5, 8, 499.50);


INSERT INTO test (name, age) VALUES ('John', 67),
                                    ('Ted', 43);
