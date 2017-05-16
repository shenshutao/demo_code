package shutao.patterns;

import java.util.ArrayList;

/**
 * One class (MySubject) can have many observers. (such as addListener())
 * Similar pattern as callback, but this pattern is one-to-many.
 * 
 * @author Shutao
 */
public class ObserverPatternTest {

	public static void main(String[] args) {
		MySubject sub = new MySubject();
		Observer observer1 = new Observer("A");
		Observer observer2 = new Observer("B");
		Observer observer3 = new Observer("C");
		sub.registerObserver(observer1);
		sub.registerObserver(observer2);
		sub.registerObserver(observer3);

		sub.setMsg("Hello World");
	}
}

class MySubject implements Subject {
	private ArrayList<Observer> observers = new ArrayList<>();
	private String msg;

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		int index = observers.indexOf(observer);
		if (index >= 0) {
			observers.remove(index);
		}
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(msg);
		}
	}

	/**
	 * Update all observers
	 */
	public void setMsg(String msg) {
		this.msg = msg;
		notifyObservers();
	}
}

interface Subject {
	/**
	 * Add an observer
	 */
	public void registerObserver(Observer observer);

	/**
	 * Remove an observer
	 */
	public void removeObserver(Observer observer);

	/**
	 * Notify all observers
	 */
	public void notifyObservers();
}

/**
 * Observer class
 */
class Observer {

	private String name;

	public Observer(String name) {
		this.name = name;
	}

	public void update(String msg) {
		System.out.println("Observer " + this.name + " received: " + msg);
	}
}