package students.items;

public abstract class Item {

	private int age = 0;
	private int maturationAge, deathAge, monetaryValue;

	public Item(int maturationAge, int deathAge, int monetaryValue) {
	this.maturationAge = maturationAge;
	this.deathAge = deathAge;
	this.monetaryValue = monetaryValue;
	}

	public void tick() {// Increases the age of the item.
		this.age += 1;
	}

	public int getValue() {// Returns the monetary value an item based on age.
		if (this.age >= this.maturationAge) {
			return this.monetaryValue;
		}
		else {
			return 0;
		}
	}
	
	public boolean died() {// Returns the living status of an item.
		if (this.age >= this.deathAge) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setAge(int age) {// Sets the age of an item.
		this.age = age;
	}
	
	@Override
	public boolean equals(Object obj) {// Returns whether an object is the same as another.
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return age == other.age && deathAge == other.deathAge && maturationAge == other.maturationAge
				&& monetaryValue == other.monetaryValue;
	}

	@Override
	public abstract String toString();
	
}
