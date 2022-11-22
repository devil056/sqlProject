create database murari;

use murari;

create table users(
	email varchar(100) primary key,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	login_password varchar(20) not null,
	user_status varchar(15) not null default 'user',
	account_created datetime not null default getdate(),
);

insert into users(first_name,last_name,email,login_password) values('Khatroth','Kireet','kireet0272@gmail.com','validP@ssword')

select * from users;

create or alter function userValidation(@user varchar(100),@pass varchar(20),@stat varchar(15))
returns table
as
return
select first_name,last_name,user_status from users
where email=@user and login_password=@pass and user_status=@stat

create procedure userInsert(@email varchar(100),@first varchar(30),@last varchar(30),@pass varchar(20))
as
insert into users(email,first_name,last_name,login_password) values(@email,@first,@last,@pass);


select * from users;

select * from murari.dbo.userValidation('kireet0272@gmail.com','validP@ssword','user')

exec userInsert @email='sandeepsuji@gmail.com',@first='Santhanam',@last='Sandeep',@pass='CapAmeric@'


delete from users where email='sandeep_suji@gmail.com'

create table trains(
train_code varchar(10) primary key,
train_name varchar(50) not null
);

create table stations(
	station_id varchar(10) primary key,
	station_name varchar(50) not null,
	station_addr varchar(100),
);

create table schedule(
	train_code varchar(10) references trains(train_code),
	station_id varchar(10) references stations(station_id),
	start_time time,
	stop_time time
);

insert into trains(train_code,train_name) values('17602','Sathapdhi Express'),
('23684','Konaseema Express');

insert into stations(station_id,station_name,station_addr) 
values ('SEC','Secunderabad','South Central Railway hub.'),
('TPT','Tirupathi','Sri Vekanna temple Station'),
('RJY','Rajamundry',''),
('VJY','Vijayawada','Lakulu Station.');

select * from stations;
select * from trains;

insert into schedule(train_code,station_id,start_time,stop_time)
values('17602','SEC','06:00AM','06:10AM'),
('17602','VJY','10:05AM','10:20AM'),
('17602','TPT','04:00PM','04:30PM'),
('17602','RJY','05:30PM',''),
('23684','RJY','06:00PM','06:20PM'),
('23684','TPT','07:20PM','07:30PM'),
('23684','VJY','01:30AM','01:40AM'),
('23684','SEC','05:30AM','');


select * from schedule
where station_id='SEC';

create procedure getTrains
as
select * from trains;

create procedure getStations
as
select * from stations;

exec getTrains

exec getStations

create procedure getTrainData(@trainCode varchar(10))
as
select * from schedule where train_code=@trainCode

exec getTrainData @trainCode='17602'

create procedure getStationData(@stationCode varchar(10))
as
select * from schedule where station_id=@stationCode

exec getStationData @stationCode='SEC';


create procedure trainInsert(@trainCode varchar(10),@trainName varchar(50))
as
insert into trains(train_code,train_name) values(@trainCode,@trainName);

exec trainInsert @trainCode='14343',@trainName='Murari Exprss'

create procedure stationInsert(@station_id varchar(10),@station_name varchar(50),@station_addr varchar(100))
as
insert into stations(station_id,station_name,station_addr) values(@station_id,@station_name,@station_addr);

select * from trains;

select * from stations;

select * from schedule;

create procedure updateTrainCode(@trainCode varchar(10),@newTrainCode varchar(10),@newTrainName varchar(50))
as
update trains set train_code=@newTrainCode,train_name=@newTrainName where train_code=@trainCode
update schedule set train_code=@newTrainCode where train_code=@trainCode

create procedure updateStation(@stationId varchar(10),@newStationID varchar(10),@newStationName varchar(50),@newStationAddr varchar(100))
as 
update stations set station_id=@newStationID,station_name=@newStationName,station_addr=@newStationAddr where station_id=@stationId
update schedule set station_id=@newStationID where station_id=@stationId

create procedure dropTrain(@trainCode varchar(10))
as
delete from trains where train_code=@trainCode;
delete from schedule where train_code=@trainCode;


create procedure dropStation(@stationId varchar(10))
as
delete from stations where station_id=@stationId;
delete from schedule where station_id=@stationId;