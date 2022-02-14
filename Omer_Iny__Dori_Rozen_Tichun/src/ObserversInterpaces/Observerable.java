package ObserversInterpaces;

import java.util.ArrayList;
import java.util.HashSet;

import modelPackage.Costumer;

public interface Observerable {
	public void addObserver(Observer ob);
	public void removeObserver(Observer ob);
	public void notifyAllObservers(ArrayList<Observer> ob, Object b);
	public boolean isChanged();// return t or f 
	public void setChange();// -> true
	public void clearChange(); // -> false
	
}
