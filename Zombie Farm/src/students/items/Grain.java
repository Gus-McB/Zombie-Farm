package students.items;

public class Grain extends Food{
	private static int grainCount = 0;
	
	public Grain() {// Constructs the Grain class using preset values.
		super(2, 6, 2);
		// TODO Auto-generated constructor stub
		grainCount += 1;
	}

	public static int getGenerationCount() {// Returns the number of Grain items created.
		return grainCount;
	}
	
	@Override
	public String toString() {// Returns the string representation of Grain.
		if (this.getValue() == 2) {
			return "G";
		}
		else {
			return "g";
		} 
	}
}
