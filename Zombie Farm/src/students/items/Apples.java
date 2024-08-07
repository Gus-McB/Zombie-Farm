package students.items;

public class Apples extends Food{

	private static int appleCount = 0;
	
	public Apples() {// Constructs the Apples class.
		super(3, 5, 3);
		appleCount += 1;
	}
	
	public static int getGenerationCount() {// Returns the appleCount class variable.
		return appleCount;
	}

	@Override
	public String toString() {// Returns the string representation of Apples.
		if (this.getValue() == 3) {
			return "A";
		}
		else {
			return "a";
		} 
	}

	
}
