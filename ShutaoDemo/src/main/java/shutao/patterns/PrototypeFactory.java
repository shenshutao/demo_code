package shutao.patterns;

import java.util.HashMap;
import java.util.Map;

/**
 * When it is complicate to instance an class.
 * 
 * Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.
 * Co-opt one instance of a class for use as a breeder of all future instances.
 * The new operator considered harmful.
 * 
 * @author Shutao
 *
 */
interface Prototype {
	Prototype clone();
}

class Cat implements Prototype {
	private final String NAME = "Cat";

	@Override
	public Prototype clone() {
		return new Cat();
	}

	@Override
	public String toString() {
		return NAME;
	}
}

class Dog implements Prototype {
	private final String NAME = "Dog";

	@Override
	public Prototype clone() {
		return new Dog();
	}

	@Override
	public String toString() {
		return NAME;
	}
}

public class PrototypeFactory {
	private static final Map<String, Prototype> prototypes = new HashMap<>();

	static {
		prototypes.put("cat", new Cat());
		prototypes.put("dog", new Dog());
	}

	public static Prototype getPrototype(String type) {
		try {
			return prototypes.get(type).clone();
		} catch (NullPointerException ex) {
			System.out.println("Prototype with name: " + type + ", doesn't exist");
			return null;
		}
	}
}
