package shutao.pattern;

/**
 * @author Shutao
 * 
 * In Spring, you can use scope = 'singleton' instead. 
 */
public class SingletonFactory {
	private static SingletonClass instance = null;

	private SingletonFactory() {
		// Avoid instantiation.
	}

	public synchronized static SingletonClass getInstance() {
		if (instance == null) {
			instance = new SingletonClass();
		}
		return instance;
	}
}

class SingletonClass {
}