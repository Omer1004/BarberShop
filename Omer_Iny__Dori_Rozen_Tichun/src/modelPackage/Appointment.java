package modelPackage;


public class Appointment {
	private Time timeOfApp;
	private Haircut haircutOfChoice;
	private static int a_id = 1;
	private int appointment_id;
	
	public static void setA_id(int aid) {
		a_id = aid;
	}
	
	
	public int getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}

	public Appointment(Time t,Haircut h) {
		this.timeOfApp = t;
		this.haircutOfChoice = h;
		this.appointment_id = a_id;
		a_id++;
	}
	
	public Appointment(Time t,Haircut h,int aId) {
		this.timeOfApp = t;
		this.haircutOfChoice = h;
		this.appointment_id = aId;
	}
	
	public Time getTimeOfApp() {
		return timeOfApp;
	}

	public void setTimeOfApp(Time timeOfApp) {
		this.timeOfApp = timeOfApp;
	}

	public Haircut getHaircutOfChoice() {
		return haircutOfChoice;
	}

	public void setHaircutOfChoice(Haircut haircutOfChoice) {
		this.haircutOfChoice = haircutOfChoice;
	}
	

	@Override
	public String toString() {
		return "Appointment :\n\n" + timeOfApp + "\n" + haircutOfChoice.toString() ;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		return this.haircutOfChoice.equals(other.haircutOfChoice) && this.timeOfApp.equals(other.timeOfApp);
	}
	
}
