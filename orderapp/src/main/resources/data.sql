INSERT INTO users (username, password, enabled)
  values ('banu', '$2y$10$dOlxTxXi3tLwGYZS2fH2HO0k.EcxzgACHrJPsrjrv7KYmQjZcyjam', 1);

INSERT INTO users (username, password, enabled)
  values ('admin', '$2y$10$dOlxTxXi3tLwGYZS2fH2HO0k.EcxzgACHrJPsrjrv7KYmQjZcyjam', 1);
    
INSERT INTO authorities (username, authority)
  values ('banu', 'ROLE_USER');
  
INSERT INTO authorities (username, authority)
  values ('admin', 'ROLE_ADMIN');
  
INSERT INTO authorities (username, authority)
  values ('admin', 'ROLE_USER');
  
  