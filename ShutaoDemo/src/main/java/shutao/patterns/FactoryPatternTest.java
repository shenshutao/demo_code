package shutao.patterns;

public class FactoryPatternTest {

	public static void main(String[] args) {
		Animal animal1 = AnimalFactory.getAnimal("tiger");
		animal1.name();
		Animal animal2 = AnimalFactory.getAnimal("lion");
		animal2.name();
	}
}

class AnimalFactory {
	public static Animal getAnimal(String animalType) {
		if ("tiger".equals(animalType)) {
			return new Tiger();
		} else if ("lion".equals(animalType)) {
			return new Lion();
		} else {
			return null;
		}
	}
}

interface Animal {
	public void name();
}

class Tiger implements Animal {

	@Override
	public void name() {
		System.out.println("This is tiger.");
	}
}

class Lion implements Animal {

	@Override
	public void name() {
		System.out.println("This is lion.");
	}
}