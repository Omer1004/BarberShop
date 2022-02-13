package modelPackage;

import java.util.ArrayList;

public class AllHaircuts {
	
	private static AllHaircuts allHaircuts;
	private  ArrayList<Haircut> allHaircutsArr ;
	public static final int allHaircutsLen = 5;
	
	public static AllHaircuts getInstance() {

		if (allHaircuts == null) {
			
			allHaircuts = new AllHaircuts();
			allHaircuts.setHaircuts();
		}
			
		return allHaircuts;
}

	private void setHaircuts() {
		this.allHaircutsArr = new ArrayList<Haircut>();
		this.allHaircutsArr.add(new Haircut("Long Hair", 80));
		this.allHaircutsArr.add(new Haircut("Medium Hair", 70));
		this.allHaircutsArr.add(new Haircut("Short Hair", 60)); 
		this.allHaircutsArr.add(new Haircut("Shave Beard", 30));
		this.allHaircutsArr.add(new Haircut("Haircut With Shave", 100));			
	}
	
	
	public Haircut getHaircutFromString(String nameOfH) {
		for(int i=0;i<allHaircutsLen;i++) {
			if(this.allHaircutsArr.get(i).getName().equals(nameOfH))
				return this.allHaircutsArr.get(i);
		}
		return null;
	}

	public ArrayList<String> getAllHaircutsByString() {
		 ArrayList<String> s = new ArrayList<String>();
		 for(int i=0;i<allHaircutsLen;i++) {
				s.add(this.allHaircutsArr.get(i).getName());
		}
		 return s;
	}

	public int getPriceOfHaircut(String nameOfHaircut) {
		if(getHaircutFromString(nameOfHaircut)==null)
			return 0;
		return getHaircutFromString(nameOfHaircut).getPrice();
	}
	
}
