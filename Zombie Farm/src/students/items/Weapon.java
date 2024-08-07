package students.items;

public abstract class Weapon extends Item{

	private int damage;
	private boolean owned = false;
	
	public Weapon(int maturationAge, int deathAge, int monetaryValue, int damage) {
		super(maturationAge, deathAge, monetaryValue);
		this.damage = damage;
	}
	
	@Override
	public  String toString() {
		if (this.isOwned() == true) {
			return "Owned";
		}
		else {
			return "Not Owned";
		}
	}

	public int getDamage() {
		return damage;
	}

	public boolean isOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}
}
