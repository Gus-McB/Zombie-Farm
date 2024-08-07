package students.items;

public abstract class Food extends Item{
	
	public Food(int maturationAge, int deathAge, int monetaryValue) {// Constructs to Food class.
		super(maturationAge, deathAge, monetaryValue);

	}
	
	public abstract String toString();
	
}
