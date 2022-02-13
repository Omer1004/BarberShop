package controller;

public class Notes {
/*
 * 
 * use Tichon;

create table CustomerUser(
Full_name varchar(50) not null,
User_name varchar(50) not null,
Pass_word varchar(50) not null,
Id_user int not null,
primary key(Id_user)
);

create table Appointment(
Id_of_appointment int not null,
Name_of_haircut varchar(50),
year int not null,
month int not null,
day int not null ,
hour int not null,
min int not null,
Id_user int not null,
foreign key(year,month,day,hour,min) references Time(year,month,day,hour,min),
foreign key(Name_of_haircut) references haircut(Name_of_haircut),
foreign key (Id_user) references CustomerUser(Id_user),
primary key(Id_of_appointment)
);

create table Time(
year int not null,
month int not null,
day int not null ,
hour int not null,
min int not null,
primary key(year,month,day,hour,min)
);



create table Haircut(
Name_of_haircut varchar(50) not null,
Price int not null,
primary key(Name_of_haircut)
);



create table blockedDays(
-- Id_blocked_day int auto_increment,
year int not null,
month int not null,
day int not null ,
primary key(day,month,year)
);
 * 
 * 
 * 
 * 	//private static HashSet<modelPackage.Costumer> usersList;
	//C:\Users\OMER\Desktop\לימודים\בסיסי נתונים\thicunProject.sql
	
	/*
	private boolean addCostumerToListOfUsers(Costumer c) {
		if(!this.usersList.add(c))
			return false;	
		return true;
	}
	public void getUsersFromSql() throws SQLException  {//load users from sql
		String theQuery = "select * from users";
		ResultSet result = statement.executeQuery(theQuery);
		while (result.next()) {
		String userFullName = result.getString("full_name");
		String theUserName = result.getString("user_name");
		String thepassword = result.getString("pass_word");
		if(userFullName != null && theUserName != null && thepassword != null) {
		addCostumerToListOfUsers(new Costumer(userFullName, theUserName, thepassword));
				}
			}
		}
	
 * 
 * 
 */
}
