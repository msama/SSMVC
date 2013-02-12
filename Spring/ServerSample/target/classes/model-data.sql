insert into user (first_name, last_name, birth_date, username, password)
	values('Mirco','Bordoni','1987-03-07','mirco','mirco');
insert into user (first_name, last_name, birth_date, username, password)
	values('Gino','Pilotino','1984-07-10','gino','gino');
insert into user (first_name, last_name, birth_date, username, password)
	values('Mario','Rossi','1980-04-11','mario','mario');
	
insert into state (id,description,time_stamp)
	values('id1','sad','2013-01-20 12:04:44');
insert into state (id,description,time_stamp)
	values('id2','happy','2013-01-21 14:44:44');
insert into state (id,description,time_stamp)
	values('id3','angry','2013-01-20 9:04:44');

insert into state_details (user_id, state_id, time_date, time_stamp)
	values(1,'id1','2013-01-11 12:04:44','2013-01-11 12:04:44');
insert into state_details (user_id, state_id, time_date, time_stamp)
	values(1,'id2','2013-01-11 15:04:44','2013-01-11 12:04:44');
insert into state_details (user_id, state_id, time_date, time_stamp)
	values(2,'id1','2013-01-04 10:54:11','2013-01-11 12:04:44');
	
insert into role (description) values ('Administrator');
insert into role (description) values ('User');

insert into user_role (user_id,role_id,from_date,to_date) values (3,1,'2013-01-01','2014-01-01');
insert into user_role (user_id,role_id,from_date,to_date) values (1,2,'2013-01-01','2014-01-01');