insert into user_details(id, name, date_of_birth) values (1001, 'jakson', DATEADD('YEAR', -20, CURRENT_DATE));
insert into user_details(id, name, date_of_birth) values (1002, 'sam', DATEADD('YEAR', -10, CURRENT_DATE));
insert into user_details(id, name, date_of_birth) values (1003, 'vish', DATEADD('YEAR', -30, CURRENT_DATE));

insert into post(id, description, user_id) values (uuid(), 'I want to get AWS certified', 1001);
insert into post(id, description, user_id) values (uuid(), 'I want to get Docker certified', 1001);
insert into post(id, description, user_id) values (uuid(), 'I want to explore photography', 1002);
insert into post(id, description, user_id) values (uuid(), 'I want to learn self defence', 1003);