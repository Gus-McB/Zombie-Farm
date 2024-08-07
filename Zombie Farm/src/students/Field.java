package students;

import java.util.Arrays;
import java.util.Random;

import students.items.*;

public class Field {
	
	private int height, width;
	private Item[][] field;
	private int day = 1;
	
	public Field(int width,int height){// Constructs the Field class using user values.
		this.height = height;
		this.width = width;
		this.field = new Item[height][width];
		for (int b = 0; b < height; b++) {// Creates the 2D array which contains the Item classes.
			for(int i = 0; i < width; i++) {
				this.field[b][i]= new Soil();
			}
		}
	}
	
	public void tick() {// calls the tick() method for all Items in the field.
		
		for (int b = 0; b < height; b++) {
			for(int i = 0; i < width; i++) {// Randomly generates weeds in the field.
				if (this.field[b][i] instanceof Food != true && this.field[b][i] instanceof Soil == true) {
					Random random = new Random();
					int chance = random.nextInt(6 - 1 + 1) + 1;
					if (chance == 1) {
						this.field[b][i] = new Weed();
					}
				}
				else if (this.field[b][i] instanceof Food == true) {// Kills food that is over the death age and replaces it with UntilledSoil.
					if (this.field[b][i].died() == true) {
						this.field[b][i] = new UntilledSoil(false);
					}
					// new game logic
					if (this.field[b][i].getValue() == 0) {// checks if the plant is below maturation age.
						Random random = new Random();
						int chance = random.nextInt(5 - 1 + 1) + 1;
						if (chance == 1) { // if the random number is 1 then a crawler will spawn on the plant.
							this.field[b][i] = new Crawler();
						}
					}
				}
					if (this.field[b][i].getValue() >= 2) {// Checks if plant has reached maturation age.
						Random random = new Random();
						int chance = random.nextInt(5 - 1 + 1) + 1;
						if (chance == 1) {// if the random number is 1 then a super zombie will spawn on the plant.
							this.field[b][i] = new SuperZombie();
						}
					}
					if (this.field[b][i] instanceof UntilledSoil) {
						if (this.field[b][i].toString() == "~") {
							((UntilledSoil) this.field[b][i]).contamTime();
						}
					}
					
				// end new game logic
				this.field[b][i].tick();
			}
		}
		
	}
	
	@Override
	public String toString() {// Returns a string representation of the Field.
		String top = "";
		String side = "";
		
		int index = 1;
		while (index <= this.width) {// Counts and stores the x-axis of the Field.
			top += "  " + index;
			index++;
		}
		int b = 0;
		for (int i = 1; i <= this.height; i++) {// Counts the y-axis and stores the Item within the index of the Field.
			side += "\n" + i + " " + Arrays.deepToString(this.field[b]).replace("[", "").replace("]", "").replace(",", " ");
			b++;
		}
		
		return top + side;
	}
	
	public void till(int x, int y) {// Removes the item from the index in Field and replaces it with a new Soil.
		this.field[x][y] = new Soil();
	}
	
	public Item get(int x, int y) {// Returns what is currently stored in that index of Field.
		return this.field[x][y];
	}
	
	public void plant(int x, int y, Item item) {// Plants an object in where there is Soil in the Field.
			this.field[x][y] = item;
	}
	
	public int getValue() {// Returns the total value of each Item in the Field.
		int total = 0;
		for(Item[] row : this.field) {
			for(Item item : row) {
				total += item.getValue();
			}
		}
		return total;
	}
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	public void attackZombie(int x, int y, int damage) {
		((Zombie) this.field[x][y]).takeDamage(damage);
	}

	public boolean checkContam(int x, int y) {
		return ((UntilledSoil) this.field[x][y]).isContaminated();
	}
	
	public String getSummary() {// Returns the game summary totaling all the major classes and values in the Field.
		int appleCount = 0;
		int grainCount = 0;
		int soilCount = 0;
		int untilledCount = 0;
		int weedCount = 0;
		
		for (Item[] row : this.field) {
			for (Item item : row) {
				if(item instanceof Soil == true) {
					soilCount +=1;
				}
				else if(item instanceof UntilledSoil == true) {
					untilledCount +=1;
				}
				else if(item instanceof Weed == true) {
					weedCount +=1;
				}
				else if(item instanceof Apples == true) {
					appleCount +=1;
				}
				else if(item instanceof Grain == true) {
					grainCount +=1;
				}
			}
		}
		
		return "   Apples:         " + appleCount +
				"\n   Grain:          " + grainCount +
				"\n   Soil:           " + soilCount + 
				"\n   Untilled:       " + untilledCount +
				"\n   Weed:           " + weedCount +
				"\n   For a total of $" + this.getValue() +
				"\n   Total apples created: " + Apples.getGenerationCount() +
				"\n   Total grain created:  " + Grain.getGenerationCount() +
				"\n   Total zombies killed: " + Zombie.getTotalKilled() + "\n";
	}
	
}
