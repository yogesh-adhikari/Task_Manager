USE task_manager;

insert into user(email, first_name,last_name, password,profile_picture,user_name)
values ('yogeshadhikari@gmail.com', 'yogeshadhikari','Yogesh','Adhikari','$2a$10$GsOi9SscCwtgCSgf0D1AVeIlEORlgr8AEsUHoeFLkvv2qg849ZdIy','https://ca.slack-edge.com/T029BRGN0-U03QV9Q0ZM1-8feba9612cf0-512');

insert into categories( id, CATEGORY_NAME, CATEGORY_ID)
VALUES ('1','GROCERY','1'),
       ('2','APPOINTMENTS','2');

insert into task(id,CREATED_AT, DUE_DATE, DESCRIPTION, CATEGORY_ID, USER_ID)
values ( '1',062123,062223,'NEEDS TO GET FOOD FROM SAMS BANANA, ORANGES, MILK, EGGS','1');
