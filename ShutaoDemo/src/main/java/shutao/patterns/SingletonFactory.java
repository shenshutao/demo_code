package shutao.patterns;

/**
 * @author Shutao
 * 
 *         In Spring, you can use scope = 'singleton' instead.
 */
public class SingletonFactory {
	private static SingletonClass instance = null;

	private SingletonFactory() {
		// Avoid instantiation.
	}

	// Lazy init + thread safe.
	public static SingletonClass getInstance() {
		if (instance == null) {
			synchronized (SingletonFactory.class) {
				if (instance == null) {
					instance = new SingletonClass();
				}
			}
		}
		return instance;
	}
}

class SingletonClass {
}