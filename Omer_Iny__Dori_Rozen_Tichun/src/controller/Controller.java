package controller;

import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ArrayList;

import ObserversInterpaces.Observer;
import ObserversInterpaces.Observerable;
import modelPackage.AllHaircuts;
import modelPackage.Appointment;
import modelPackage.Costumer;
import modelPackage.Haircut;
import modelPackage.Model;
import modelPackage.Time;
import modelPackage.idLoggedUser;
import view.View;


public class Controller implements Observerable{
	private View view;
	private Model model;
	private boolean isChanged;
	private ArrayList<Observer> observers;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
		//Observer Funs

	@Override
	public void addObserver(Observer ob) {
		observers.add(ob);//add all coutomer to observers on start project
		
	}
	@Override
	public void removeObserver(Observer ob) {
		observers.remove(ob);
		
	}
	@Override
	public void notifyAllObservers(ArrayList<Observer> arr, Object date) {
		if(isChanged == true) {
			LocalDate d = (LocalDate)date;
			System.out.println("arr to change size: "+arr.size());
			for (int i = 0; i < arr.size(); i++) {		
				((Costumer)arr.get(i)).update(this, d);
				
			}
			clearChange();
		}
	}
	@Override
	public boolean isChanged() {
		return isChanged;
	}
	@Override
	public void setChange() {
		this.isChanged = true ; 
		
	}
	@Override
	public void clearChange() {
		this.isChanged = false ;
		
	}
		 
		 
	//////////////////////////////////////////////////////////////////////
	// General
	public Controller(View view,Model model) {
		this.model = model;
		this.view = view;
		this.isChanged = false;
		this.observers = new ArrayList<Observer>();
		System.out.println("Controller1");
	}
	
	public static Model bootUp() {
		System.out.println("Controller2");
		Model m = null;
		try {
			m = JDBController.getInstance().getModel();
			addAllCostumersThereAppointments(m.getAllCostumers());
			m.finishBoot();
			return m;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return m;
		}
	}
	
	
	private static void addAllCostumersThereAppointments(ArrayList<Costumer> allCostumers) {
		for(int i = 0 ;i<allCostumers.size();i++) {
			try {
				JDBController.getInstance().addToCostumerHisAppointment(allCostumers.get(i));
			} 
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}	
	}

	
	/////////////////////////////////////////////////////////////////////////
	///LOGIN
	public int loginToSystem(String username,String password) throws ClassNotFoundException, SQLException {
		if(this.model.costumerExistsInModel(username,password) == false||
				JDBController.getInstance().costumerExistsInDataBase(username,password)) {
			view.showmsg("login falied");
			return 0 ;
		}
		else {
			int idCoustomer;
				idCoustomer = model.getIdOfCostumer(username, password);
				idLoggedUser.getInstance().setIdOfLoggedCostumer(idCoustomer);
				
				if (idLoggedUser.getInstance().getIdOfLoggedCostumer() == 1) {
					return 1;
				}
				else {
					
					Costumer temp = model.findCostumerFromId(idCoustomer) ; 
					if(temp != null && !(temp.getMsg().equals(""))) {
						view.showmsg(temp.getMsg());
						model.findCostumerFromId(idCoustomer).clearMsg();
						JDBController.clearMsg(idCoustomer);
					}
					return 2;
				}
			
		}
			
	}
	public void userSignOut() {
		idLoggedUser.getInstance().setIdOfLoggedCostumer(0);
		
		System.out.println("Controller5");
	}


	
	public String getLoggedInCostumerAppointmentString() {
		return model.getAppointmentCostumerToString(idLoggedUser.getInstance().getIdOfLoggedCostumer());
	}


	public boolean deleteAppointmentForLoggedInUser() {
		System.out.println();
		if(!model.deleteAppointmentForLoggedInCostumer(idLoggedUser.getInstance().getIdOfLoggedCostumer())) {
			return false;
		}
		 
		try {
			if(!JDBController.getInstance().DeleteAppointmentForLoggedInCostumer
					(idLoggedUser.getInstance().getIdOfLoggedCostumer())) {
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			view.showmsg(e.getMessage());
			return false;
		}
		return true;
		
	}

	public boolean signUpUser(String name, String password, String username) {
		try {
			if(model.isUsernameExists(username)||JDBController.getInstance().checkIfUserNameExistsInDataBase(username)) {
				System.out.println(model.isUsernameExists(username));
				System.out.println(JDBController.getInstance().checkIfUserNameExistsInDataBase(username));
				return false;			
			}
			else {
				Costumer c = new Costumer(username, password,name);
				if(!model.addCostumer(c)) {
					model.decreaseC_Id_FromCostumer();
					return false;
				}
				try {
					JDBController.getInstance().insertUserIntoSqlDataBase(c);
					return true;
				}
				catch (SQLException ex) {
					while (ex != null) {
						System.out.println("SQL exception: "+ ex.getMessage());
						ex = ex.getNextException();
					}
					model.decreaseC_Id_FromCostumer();
					model.removeCostumer(c);
					return false;
				}	
				catch (Exception e) {
					view.showmsg(e.getMessage());
					model.decreaseC_Id_FromCostumer();
					model.removeCostumer(c);
					return false;
				}
					
				
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: "+ ex.getMessage());
				ex = ex.getNextException();
			}
		}
		catch(Exception e) {
			view.showmsg(e.getMessage());
		}
		return false;
	}


	public boolean setAppointmentForLoggedInUser(LocalDate date, String time, String haircut) {
		try {
			Time t = getTimeFromString(date,time);
			Haircut h = AllHaircuts.getInstance().getHaircutFromString(haircut);
			Appointment a = new Appointment(t, h);
			model.addAppointmentToCostumer(idLoggedUser.getInstance().getIdOfLoggedCostumer(),a);
			JDBController.getInstance().setAppointmentToLoggedInUser(idLoggedUser.getInstance().getIdOfLoggedCostumer(),a);
		}
		catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: "+ ex.getMessage());
				ex = ex.getNextException();
			}
		}	
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}


	private Time getTimeFromString(LocalDate d,String time){
		int hour,min;
		hour = (time.charAt(0)-'0')*10+time.charAt(1)-'0';
		min =  (time.charAt(3)-'0')*10+time.charAt(4)-'0';
		Time t;
		System.out.println(min);
		try {
			t = new Time(d,hour,min);
			return t;
		} catch (Exception e) {
			view.showmsg(e.getMessage());
			return null;
		}
		
	}

	//synchronized methud
	public synchronized ArrayList<String> getAvilableAppointmentsHours(LocalDate date) {
		if(date==null)
			return null;
		if(date.isBefore(LocalDate.now())) {
			view.showmsg("Please select a date in the future");
			return null;
		}
		if(date.getDayOfWeek().getValue()==6) {
			view.showmsg("satarday is not good");
			return null;
		}
		try {
			if(JDBController.getInstance().isDateBlocked(date)) {
				view.showmsg("day is blocked by barber");
				return null;
			}
			else {
				try {
					ArrayList<String> times = model.getAllTimesToSetAppointments();
					times = JDBController.getInstance().setAvilableTimesFromDateToArraylist(date,times);
					return times;
				}
				catch (SQLException ex) {
					while (ex != null) {
						System.out.println("SQL exception: "+ ex.getMessage());
						ex = ex.getNextException();
					}
					return null;
				}	
				catch (Exception e) {
					view.showmsg(e.getMessage());
					return null;
				}
			}
		} catch (Exception e) {
			view.showmsg(e.getMessage());
			return null;
		}
	}

	//synchronized methud
	public synchronized ArrayList<String> getTimesOfAppointments(LocalDate d) {
		ArrayList<String> times;
		try {
			System.out.println(d);
			times = JDBController.getInstance().getTimesOfAppointmentsTimesInDate(d);
			System.out.println(times.toString());
			return times;
		} catch (Exception e) {
			view.showmsg(e.getMessage());
			return null;
		}
		
	}


	public static ArrayList<String> getAllHaircuts() {
		return AllHaircuts.getInstance().getAllHaircutsByString();
	}


	public String getAppointmentFromTime(LocalDate d, String hour) {
		try {
			Time t = getTimeFromString(d, hour);
			int idOfUser = JDBController.getInstance().getIdOfUserWhoHasAppointmentInDate(t);
			return model.getAppointmentCostumerToString(idOfUser);
		} catch (Exception e) {
			view.showmsg(e.getMessage());
			return "";
		}
	}


	public void deleteAppointmentInTime(LocalDate date, String hour) {
		Time t = getTimeFromString(date, hour);
		try {
			JDBController.getInstance().deleteAppointmentInTime(t);
			
			model.deleteAppointmentInTime(getTimeFromString(date, hour));
			view.showmsg("appointment was deleted");
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: "+ ex.getMessage());
				ex = ex.getNextException();
			}
		}
		catch(Exception e) {
			view.showmsg(e.getMessage());
		}
		
	}

	//synchronized methud
	public synchronized boolean deleteAllAppointmentsInDate(LocalDate date) {
		try {
			JDBController.getInstance().DeleteAppointmentInDate(date);
			JDBController.getInstance().blockDay(date);
			isChanged = true ;
			ArrayList<Observer> currectUserToMsg = model.getCurrectUsersToMsg(date);
			notifyAllObservers(currectUserToMsg,date);
			model.deleteAppointmentInDate(date);
			return true;
			
		}
		catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: "+ ex.getMessage());
				ex = ex.getNextException();
			}
			return false;
		}	
		
		catch (ClassNotFoundException e) {
			view.showmsg(e.getMessage());
			return false;
		}
	}

	
	public int getPriceOfHaircut(String nameOfHaircut) {
		return AllHaircuts.getInstance().getPriceOfHaircut(nameOfHaircut);
	}
	
	public void unblockDay(LocalDate date) {
		if(date.isBefore(LocalDate.now())) {
			view.showmsg("This day is in the past, cant unblock");
			return;
		}
		try {
			JDBController.getInstance().unblockDay(date);
			view.showmsg("Unblock successful");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	


	
}
