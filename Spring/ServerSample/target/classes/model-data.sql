insert into user (first_name, last_name, birth_date, username, password)
	values('Mirco','Bordoni','1986-10-07','mirco','mirco');
insert into user (first_name, last_name, birth_date, username, password)
	values('Gino','Pilotino','1984-07-10','gino','gino');
insert into user (first_name, last_name, birth_date, username, password)
	values('Mario','Rossi','1980-04-11','mario','mario');
	
insert into state (description)
	values('sad');
insert into state (description)
	values('happy');
insert into state (description)
	values('angry');

insert into state_details (user_id, state_id, time_date)
	values(1,2,'2013-01-11 12:04:44');
insert into state_details (user_id, state_id, time_date)
	values(1,1,'2013-01-11 15:04:44');
insert into state_details (user_id, state_id, time_date)
	values(2,1,'2013-01-04 10:54:11');