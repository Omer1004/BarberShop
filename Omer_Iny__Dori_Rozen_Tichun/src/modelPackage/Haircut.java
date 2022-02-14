package modelPackage;

public class Haircut {
	private int price;
	private String name;
	
	public Haircut(String name,int price) {
		this.name = name;
		this.price = price;
		
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Haircut) {
			if(((Haircut)obj).getName().equals(this.name))
				return true;
			else
				return false;
		}
		else {
			return false;
		}
	}

	public String toString() {
		return "Name of Haircut: "+this.name+"\nPrice:"+this.price;
	}
		
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
