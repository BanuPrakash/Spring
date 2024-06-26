insert into  users (username, password, enabled) values ('anna','$2a$12$GISM0YzpKZvy59q4CbntPusmr3o0oXds3nJwtkuekX.Jh1e9EYUFC',1);
insert into  users (username, password, enabled) values ('roger','$2a$12$W0ZyXlWFPzuFO..UE1Ko5euu/lSkjz/uKGN9ZomQK4nT16Eiyf8XW',1);

insert into authorities(username, authority) values ('roger', 'ROLE_ADMIN');
insert into authorities(username, authority) values ('roger', 'ROLE_USER');

insert into authorities(username, authority) values ('anna', 'ROLE_USER');
