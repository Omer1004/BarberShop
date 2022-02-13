package modelPackage;

public class idLoggedUser {
	private int idOfLoggedCostumer;
	private static idLoggedUser theId; 
	
	
	public static idLoggedUser getInstance() {

		if (theId == null)
			theId = new idLoggedUser();
		return theId;
}
	public int getIdOfLoggedCostumer() {
		return idOfLoggedCostumer;
	}

	public void setIdOfLoggedCostumer(int idOfLoggedCostumer) {
		this.idOfLoggedCostumer = idOfLoggedCostumer;
	}
	
	
	
	
	
}
