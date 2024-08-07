package students.items;

public abstract class Zombie extends Item{
	private static int totalKilled = 0;
	private int health;
		
	public Zombie(int maturationAge, int deathAge, int monetaryValue, int health) {
		super(maturationAge, deathAge, monetaryValue);
		this.setHealth(health);
	}

	@Override
	public abstract String toString();

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void takeDamage(int damage) {
		this.setHealth(this.getHealth() - damage);
	}
	
	public static int getTotalKilled() {
		return totalKilled;
	}
	
	@Override
	public boolean died() {
		if (this.health <= 0) {
			totalKilled += 1;
			return true;
		}
		else {
			return false;
		}
	}
	
}
