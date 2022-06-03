INSERT INTO customer (CUSTOMERID, NAME) VALUES (1, 'jack'),
       (2, 'rose'),
       (3, 'peter parker'),
       (4, 'tony stark'),
       (5, 'steve rogers'),
       (6, 'barry allen');

INSERT INTO purchase (PURCHASEID, AMOUNT, CUSTOMERID, CREATETIMESTAMP)
VALUES (1, 110, 1, CURRENT_TIMESTAMP - 1 DAY),
       (2, 10, 1, CURRENT_TIMESTAMP - 1 MONTH),
       (3, 90, 1, CURRENT_TIMESTAMP - 2 MONTH),
       (4, 40, 1, CURRENT_TIMESTAMP - 3 MONTH),
       (5, 50, 1, CURRENT_TIMESTAMP - 4 MONTH),
       (6, 10, 2, CURRENT_TIMESTAMP),
       (7, 50, 3, CURRENT_TIMESTAMP),
       (8, 90, 5, CURRENT_TIMESTAMP),
       (9, 110, 6, CURRENT_TIMESTAMP);