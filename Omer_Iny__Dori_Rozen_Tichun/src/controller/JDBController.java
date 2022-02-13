package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;


import javax.swing.JOptionPane;

import modelPackage.AllHaircuts;
import modelPackage.Appointment;
import modelPackage.Costumer;
import modelPackage.Model;
import modelPackage.Time;

public class JDBController {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/Tichun";
	private static String username = "root";
	private static String password = "omer55555";//must be
	private static Statement statement ;
	private Connection DBconnection;
	private static JDBController jdbController;

	
	
	private JDBController() throws SQLException, ClassNotFoundException {
		DBconnection = DriverManager.getConnection(url, username, password);
		Class.forName(driver);
		statement = DBconnection.createStatement();
	}

	public static JDBController getInstance() throws SQLException, ClassNotFoundException {
		if (jdbController == null)
			jdbController = new JDBController();
		return jdbController;
	}
	
	
	public boolean costumerExistsInDataBase(String username,String password) throws SQLException{
		//User_name
		//Pass_Word
		String theQuery = "select User_name,Pass_word from CustomerUser WHERE User_name = '"+username+"' AND Pass_word = '"+password+"'";
		ResultSet result = statement.executeQuery(theQuery);
		if(result.next()) {
			String user_name = result.getString("User_name");
			String pass_word = result.getString("Pass_word");
			if(user_name ==null || pass_word == null)
				return false;
			if(user_name ==username || pass_word == password)
				return true;
			return false;
		}
		return false;
		
		
	}
	
	public boolean checkIfUserNameExistsInDataBase(String username) throws SQLException {
	String theQuery = "SELECT User_name from CustomerUser WHERE User_name = '"+username+"'";
	ResultSet result = statement.executeQuery(theQuery);
	if(!result.next())
		return false ;	
	return true;

}
	
	
	
	public void insertUserIntoSqlDataBase(Costumer c) throws Exception {
		String s = "insert into CustomerUser(Full_name,User_name,Pass_word,Id_user)values('"+
	c.getName()+"','"+c.getUsername()+"','"+c.getPassword()+"',"+c.getId()+")";
		statement.executeUpdate(s);
	}
	

		public void insertUserIntoSqlDataBaseAndListInJava(String thefullname,String theusername,String thepassword) throws SQLException {//get from java into sql
			
			String theQuery = "select * from CustomerUser where full_name = '"+thefullname+"' AND user_name = '"+theusername+"' "
					+ "AND pass_word = '"+thepassword+"'";
			ResultSet result = statement.executeQuery(theQuery);
			
			String userFullName = result.getString("full_name");
			String UserName = result.getString("user_name");
			String password = result.getString("pass_word");
			String insertQuery = "insert into CustomerUser(full_name,user_name,pass_word)values"
					+ "('"+thefullname+"','"+theusername+"','"+thepassword+"')";

			if(userFullName == null && UserName == null && password == null) {
				statement.executeUpdate(insertQuery);//for sql
			}
			else {
				JOptionPane.showMessageDialog(null, "Username exist,try another one");
			}
		}




		


		public boolean DeleteAppointmentForLoggedInCostumer(int idOfLoggedCostumer) throws SQLException {
			String t_idreq = "SELECT t_id FROM appointment where Id_user = "+idOfLoggedCostumer+"";
			String theQuery = "delete from appointment where Id_user = "+idOfLoggedCostumer+"";
			ResultSet r = statement.executeQuery(t_idreq);
			if(r.next()) {
				int t_id = r.getInt("t_id");
				String timeDelete = "delete from time where t_id = "+t_id+"";
				statement.executeUpdate(theQuery);
				statement.executeUpdate(timeDelete);
			}
			return true;
		}


		public boolean isDateBlocked(LocalDate date) throws Exception {
			String blockedDaysQ = "SELECT * FROM blockedDays";
			ResultSet blockedDaysRes = statement.executeQuery(blockedDaysQ);
			int year,month,day;
			while(blockedDaysRes.next()) {
				year = blockedDaysRes.getInt("year");
				month = blockedDaysRes.getInt("month");
				day = blockedDaysRes.getInt("day");
				if(LocalDate.of(year, month, day).equals(date)) {
					throw new Exception("This day is blocked by the barber");
					
				}
			}
			return false;
		}


		public ArrayList<String> setAvilableTimesFromDateToArraylist(LocalDate date,ArrayList<String> times) throws Exception {
			if(isDateBlocked(date)) {
				times.clear();;
				return times;
			}
			String theQuery = "SELECT * FROM Time WHERE year = "+date.getYear()+" AND month = "+
					date.getMonthValue()+" AND day = "+date.getDayOfMonth()+" ";
			ResultSet result = statement.executeQuery(theQuery);
			int hour,min;
			String t;
			while(result.next()) {
				hour = result.getInt("hour");
				min = result.getInt("min");
				t = assembleTimeString(hour,min);
				times.remove(t);
			}
			return times;
	
		}
	

		
		public static String assembleTimeString(int hour,int min) {
			String t;
			if(hour>=10) {
				t= hour+":";
			}
			else {
				t = "0"+hour+":";
			}
			if(min<10) {
				t = t+"0"+min;
			}
			else {
				t = t+min;
			}
			return t;
		}


		public int getT_idFromTime(Time t) throws Exception {
			String gett_id = "SELECT t_id FROM time WHERE year = "+t.getDate().getYear()+" AND month = "+t.getDate().getMonthValue()+
					" AND day = "+t.getDate().getDayOfMonth()+" AND hour = "+t.getHour()+" AND min = "+t.getMin()+" ";
			int t_id ;
			ResultSet r = statement.executeQuery(gett_id);
			if(!r.next()) {
				throw new Exception("time sql fail there is no such time");
			}
			t_id = r.getInt("t_id");
			return t_id;
		}
		
		

		public void setAppointmentToLoggedInUser(int idOfLoggedCostumer,Appointment a) throws Exception {
			DeleteAppointmentForLoggedInCostumer(idOfLoggedCostumer);
			String addTime = "insert into time (year,month,day,hour,min)values ("+a.getTimeOfApp().getDate().getYear()+", "
			+a.getTimeOfApp().getDate().getMonthValue()
					+", "+a.getTimeOfApp().getDate().getDayOfMonth()+", "+a.getTimeOfApp().getHour()+", "+a.getTimeOfApp().getMin()+")";
			
			statement.executeUpdate(addTime);	
			int t_id = getT_idFromTime(a.getTimeOfApp());
			String theQuery = "insert into appointment (Name_of_haircut,t_id,Id_of_appointment,Id_user) values('"+
					a.getHaircutOfChoice().getName()+"',"+t_id+","+a.getAppointment_id()+","+idOfLoggedCostumer+")";	
			statement.executeUpdate(theQuery);	
		}




		public ArrayList<String> getTimesOfAppointmentsTimesInDate(LocalDate d) throws Exception {
			ArrayList<String> times = new ArrayList<String>();			
			if(!isDateBlocked(d)) {
			String s = "select hour,min from Time where year = "+d.getYear()+" AND month = "+
			d.getMonthValue()+" AND day = "+d.getDayOfMonth()+" ";
			ResultSet result = statement.executeQuery(s);
				int hour,min;
				String t;
				while(result.next()) {
					hour = result.getInt("hour");
					min = result.getInt("min");
					t = assembleTimeString(hour,min);
					times.add(t);
					
				}	
			System.out.println(times.toString());
			}
			return times;
			
		}




		public int getIdOfUserWhoHasAppointmentInDate(Time t) throws Exception {
			String s = "select id_user from Appointment where t_id = "+getT_idFromTime(t)+"";
			ResultSet result = statement.executeQuery(s);
			if(result.next())
				return Integer.parseInt(result.getString("id_user"));
			throw new Exception("there is not an appointment for id user ");
		}

		
		

		public void deleteAppointmentInTime(Time t) throws Exception {
			int t_id = getT_idFromTime(t);
			String deleteTime = "DELETE FROM time WHERE t_id = "+t_id+" ";
			String theQuery = "DELETE from appointment WHERE t_id = "+t_id+"";
			statement.executeUpdate(theQuery);
			statement.executeUpdate(deleteTime);
		}

		/*
		 * theQuery = "Update CustomerUser set msg =   'Attention ! ,"
							+"your appointment on date "+date.toString()+" has been canceled' WHERE"
		 * 					(select msg from CustomerUser join Time join appointment on "
						+ "Time.t_id = appointment.t_id AND CustomerUser.Id_user = appointment.Id_user) 
		 */


		public void DeleteAppointmentInDate(LocalDate date) throws SQLException {
			String gett_id = "SELECT t_id FROM time WHERE year = "+date.getYear()+" AND month = "+date.getMonthValue()+
					" AND day = "+date.getDayOfMonth()+" "; 
			ResultSet r = statement.executeQuery(gett_id);
			String theQuery;
			ArrayList<Integer> arr = new ArrayList<Integer>();
			while(r.next()) {
				arr.add(r.getInt("t_id"));
			}
			for(int i=0;i<arr.size();i++) {
				theQuery = "UPDATE CustomerUser,Time,appointment SET CustomerUser.msg  =  'Attention ! , "
					+ "your appointment on date "+date.toString()+" has been canceled' "
					+ "WHERE Time.t_id = appointment.t_id AND CustomerUser.Id_user = appointment.Id_user AND Time.year = "+date.getYear()+
					" AND Time.month = "+date.getMonthValue()+" AND Time.day = "+date.getDayOfMonth();
				statement.executeUpdate(theQuery);
				theQuery = "DELETE from appointment WHERE t_id = "+arr.get(i)+"";
				statement.executeUpdate(theQuery);
				theQuery = "DELETE from time WHERE t_id = "+arr.get(i)+"";
				statement.executeUpdate(theQuery);				
			}
			//UpdateMsg(date);//added Here added the quiery up
		}
		
		
		public void UpdateMsg(LocalDate date) throws SQLException {
			String theQuery = "UPDATE CustomerUser SET msg  =  'Attention ! , "
				+ "your appointment on date "+date.toString()+" has been canceled' "
				+ " Where CustomerUser.Id_user = (select CustomerUser.Id_user from CustomerUser, appointment, Time"
				+ " WHERE Time.t_id = appointment.t_id AND CustomerUser.Id_user = appointment.Id_user"
				+ " AND Time.year = "+date.getYear()+" AND Time.month = "+date.getMonthValue()+""
				+ " AND Time.day = "+date.getDayOfMonth()+" )";				
				statement.executeUpdate(theQuery);	
		}
		
		public void UpdateMsgForSingleCostumer(LocalDate date,int a_id) throws SQLException {
			String theQuery = "UPDATE CustomerUser SET msg  =  Attention ! , "
					+ "your appointment on date "+date.toString()+" has been canceled "
				+ " Where CustomerUser.Id_user = (select t_id from CustomerUser, appointment, Time"
				+ " WHERE Time.t_id = appointment.t_id AND CustomerUser.Id_user = appointment.Id_user"
				+ "AND Time.year = "+date.getYear()+" AND Time.month = "+date.getMonth()+""
				+ " AND Time.day = "+date.getDayOfMonth()+" ) set msg = Attention ! , "
				+ "your appointment on date "+date.toString()+" has been canceled ";				
				statement.executeUpdate(theQuery);	
		}
		 


		public Model getModel() throws Exception {
			Model model = new Model("Izik barbershop","izik");
			Costumer c ;
			String userFullName,UserName,passwordUser,msg;
			int idUser =-1;
			String theQuery = "select * from CustomerUser";
			ResultSet result = statement.executeQuery(theQuery);
			int i=0;

			while(result.next()) {
				i++;
				System.out.println(i);
				userFullName = result.getString("Full_name");
				UserName = result.getString("User_name");
				System.out.println(UserName);
				passwordUser = result.getString("Pass_word");
				idUser = result.getInt("Id_user");
				msg = result.getString("msg");
				c = new Costumer(UserName,passwordUser,userFullName,idUser,msg);
				System.out.println( model.addCostumer(c));
			}
			Costumer.setC_id(idUser+1);
			
			return model;
	
		}

		public void resultSetCleanUp(ResultSet rs,Statement stmt) {
			if (rs != null) { 
				try { rs.close();
				} catch (SQLException sqlEx) { } 
				rs = null; } 
			if (stmt != null) { 
				try { stmt.close(); 
			}
			
				catch (SQLException sqlEx) { }
			}
			stmt = null;
		} 


		

		public void addToCostumerHisAppointment(Costumer c) throws Exception {
			String appointmentQ = "select * from Appointment where Id_user = "+c.getId()+"";
			ResultSet resultAppointment = statement.executeQuery(appointmentQ);
			int year,month,day,hour,min;
			String TimeQ = "select * from time where t_id = ";
			ResultSet tQ ;
			if(resultAppointment.next()) {
				int Id_of_appointment;
				Id_of_appointment = resultAppointment.getInt("Id_of_appointment");
				String Name_of_haircut = resultAppointment.getString("Name_of_haircut");
				TimeQ = TimeQ+resultAppointment.getInt("t_id");
				tQ = statement.executeQuery(TimeQ);
				if(tQ.next()) {
					year = tQ.getInt("year");
					month = tQ.getInt("month");
					day = tQ.getInt("day");
					hour = tQ.getInt("hour");
					min = tQ.getInt("min");
					Time t = new Time(LocalDate.of(year, month, day),hour,min);
					Appointment a = new Appointment(t,AllHaircuts.getInstance().getHaircutFromString(Name_of_haircut) , Id_of_appointment);
					c.setAppointment(a);
				}
					
			}
		}

		public void blockDay(LocalDate date) throws SQLException {
			String s = "insert into blockedDays(year,month,day)values("+
					date.getYear()+","+date.getMonthValue()+", "+date.getDayOfMonth()+" )";
			statement.executeUpdate(s);
		}

		public String getMsgSql(Costumer c) {
			//LocalDate b = (LocalDate)j;
			String s ="select msg from CustomerUser where Id_user = '"+c.getId()+"' "; 
			return s;
		}

		public static void clearMsg(int idCoustomer) throws SQLException {
			String theQuery = "UPDATE CustomerUser SET msg = '' WHERE Id_user = "+idCoustomer;
			statement.executeUpdate(theQuery);
		}
		public void unblockDay(LocalDate date) throws SQLException {
			String quiery = "DELETE FROM blockedDays WHERE blockedDays.year = "+date.getYear()+
					" AND blockedDays.month = "+date.getMonthValue()+" AND blockedDays.day = "+date.getDayOfMonth();
			statement.executeUpdate(quiery);
		}


}
