package modelPackage;
import java.time.LocalDate;
import java.util.ArrayList;

import ObserversInterpaces.Observer;
import ObserversInterpaces.Observerable;


public class BlockedDates {
	private static ArrayList<LocalDate> blockedDatesArr;
	private static BlockedDates blockedDates;
	
	public static BlockedDates  getInstance() {

			if (blockedDates == null)
				blockedDates = new BlockedDates();
			return blockedDates;
	}
	
	public ArrayList<LocalDate> getBlockedDateArr(){
		return blockedDatesArr;
	}
	
	public void addBlockDate(LocalDate theDate) {
		this.blockedDatesArr.add(theDate);
	}


	
	

}
