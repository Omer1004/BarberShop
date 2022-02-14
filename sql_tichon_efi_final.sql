
use Tichun;

create table CustomerUser(
Full_name varchar(50) not null,
User_name varchar(50) not null,
Pass_word varchar(50) not null,
Id_user int not null,
msg varchar(500) default "" , -- needs to drop -> start again
primary key(Id_user));

create table Appointment(
Id_of_appointment int not null,
Name_of_haircut varchar(50),
t_id int not null,
Id_user int not null,
CONSTRAINT t_fk foreign key(t_id) references Time(t_id) ON update CASCADE,
CONSTRAINT h_fk foreign key(Name_of_haircut) references haircut(Name_of_haircut)on update cascade,
CONSTRAINT u_fk foreign key (Id_user) references CustomerUser(Id_user)on update cascade,
primary key(Id_of_appointment));

create table Time(
t_id int not null auto_increment,
year int not null,
month int not null,
day int not null ,
hour int not null,
min int not null,
primary key(t_id));

create table Haircut(
Name_of_haircut varchar(50) not null,
Price int not null,
primary key(Name_of_haircut));

create table blockedDays(
year int not null,
month int not null,
day int not null ,
primary key(day,month,year));


insert into customeruser (Full_name,User_name,Pass_word,Id_user) values
('admin', 'izik','1234',1),
('shlomi','shlomi','1145695252',2),
('shlomi','shlomi1','121212',3),
('yeal shemesh','shemeshhh','71777',4),
('avi','ram','23827',5);

insert into haircut (Name_of_haircut,Price)values("Long Hair", 80),("Medium Hair", 70),("Short Hair", 60)
,("Shave Beard", 30),("Haircut With Shave", 100);







select * from haircut;
select * from time;
select * from appointment ;
select * from customeruser;
select * from blockeddays;

select * from appointment join customeruser on appointment.Id_user = customeruser.Id_user;








delete from appointment where Id_of_appointment =1;
delete from Time where t_id<20 ;

drop table blockeddays;
drop table appointment;



drop table customeruser;

drop table haircut;

drop table time;
 