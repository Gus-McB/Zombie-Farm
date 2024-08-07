package students;

import java.util.Scanner;

import students.items.*;

public class Farm {
	
	int fieldWidth, fieldHeight, balance, x, y;
	Field field;
	Bat bat = new Bat();
	Pistol pistol = new Pistol();
	MachineGun machineGun = new MachineGun();
	char choice;
	String play = "p";
	
	public Farm(int fieldWidth, int fieldHeight, int startingFunds){// Constructs the Farm class using user values.
		this.fieldWidth = fieldWidth;
		this.fieldHeight = fieldHeight;
		this.balance = startingFunds;
		this.field = new Field(fieldWidth, fieldHeight);
	}
	
	public void display() { // Displays the game menu and field map.
		System.out.println("Day "+ this.field.getDay() + "\n");
		System.out.println(this.field);
		System.out.println("\nBank Balance: " + this.balance + "\n");
		System.out.println("Weapons available:\n"
				+ "- bat: " + this.bat + "\n"
				+ "- pistol: " + this.pistol + "\n"
				+ "- machine gun: " + this.machineGun + "\n");
		
		System.out.println("Enter your next action:");
		System.out.println("  t x y: till\n"
				+ "  h x y: harvest\n"
				+ "  p x y: plant\n"
				+ "  a x y: attack\n"
				+ "  b: buy weapon\n"
				+ "  s: field summary\n"
				+ "  w: wait\n"
				+ "  q: quit");
	}
	
	public void userInput() { // Logic necessary to identify what menu operation and coordinates the user would like to execute.
		Scanner playerObj = new Scanner(System.in);
		playerObj.useDelimiter("[\\s,]+");
		
		String input = playerObj.nextLine();
		String[] inputList = input.split(" ", 3);
		if (inputList[0].length() > 1) {
			throw new RuntimeException("");
		}
		this.choice = inputList[0].charAt(0);
		
		if (inputList.length == 3) {// Checks if there are x and y coordinates.
			this.x = Integer.parseInt(inputList[2]) - 1;
			while (this.x > this.fieldHeight - 1 || this.x < 0) {// Checks that a valid x coordinate has
				System.out.println("This is not a valid y coordinate! \nTry a new one: ");
				this.x = Integer.parseInt(playerObj.nextLine()) - 1;
			}
			
			this.y = Integer.parseInt(inputList[1]) - 1;
			while (this.y > this.fieldWidth - 1 || this.y < 0) {
				System.out.println("This is not a valid x coordinate! \nTry a new one: ");
				this.y = Integer.parseInt(playerObj.nextLine()) - 1;
			}
		}
	}
	
	public void menuSelection() {// Uses the user input to identify the action and the coordinates.
		if (this.choice == 't') {// Calls the fields till method at the specific index.
			if (this.field.get(this.x, this.y) instanceof Weed == true) {
				this.field.till(this.x, this.y);
			}
			else if (this.field.get(this.x, this.y) instanceof UntilledSoil == true && this.field.checkContam(this.x, this.y) == false) { // Checks if the soil is contaminated and if it is UntilledSoil.
				this.field.till(this.x, this.y);
			}
			else if (this.field.get(this.x, this.y) instanceof UntilledSoil == false) {// If the coordinate is not UntilledSoil it will skip a turn and display a message.
				System.out.println("\nCannot till here.\n");
			}
			else if (this.field.checkContam(this.x, this.y) == true) {// If the soil is contaminated it will stop you from tilling the soil until the contaminated time is 0.
				int daysLeft = ((UntilledSoil) this.field.get(this.x, this.y)).getContamTime();
				System.out.println("This area is covered in zombie guts, dont plant here "
						+ "till day " + (this.field.getDay()));
			}
		}
		
		else if (this.choice == 'h') {// Adds the value of the crop to the players balance and replaces it with UntilledSoil.
			Item plant = this.field.get(this.x, this.y);
			if (plant.toString() == "A" || plant.toString() == "G") {// Checks if the crop can be harvested.
				this.balance += plant.getValue();
				UntilledSoil untilledSoil = new UntilledSoil(false);
				this.field.plant(this.x, this.y, untilledSoil);
			}
			else {
				System.out.println("Cannot harvest here.");
			}
		}
		else if (this.choice == 'p') {// Plants either a Grain or Apples, subtracting the cost from the balance.
			if (this.field.get(this.x, this.y) instanceof Soil == true) {// Checks if the this coordinate is Soil.
				Scanner plantObj = new Scanner(System.in);
				System.out.println("Enter:\n"
						+ "- 'a' to buy an apple for $2\n"
						+ "- 'g' to buy grain for $1");
				String plantInput = plantObj.next();
				char plantType = plantInput.charAt(0);
				if (plantType == 'g' && this.balance >= 1) {// Checks if you can afford the grain.
					this.balance -= 1;
					Grain grain = new Grain();
					this.field.plant(this.x, this.y, grain);
				}
				else if(plantType == 'a' && this.balance >= 2) {// Checks if you can afford an apple.
					this.balance -= 2;
					Apples apple = new Apples();
					this.field.plant(this.x, this.y, apple);
				}
				else if (plantType == 'a' && this.balance < 2){// Skips turn if there is not enough balance.
					System.out.println(" You have gone to the shops without enough money.\n"
							+ "Turn around and go home, you've lost a turn.");
				}
				else if (plantType == 'g' && this.balance < 1){// Skips turn if there is not enough balance.
					System.out.println(" You have gone to the shops without enough money.\n"
							+ "Turn around and go home, you've lost a turn.");
				}
				else {
					System.out.println("Not a valid input");
				}
			}
			else if (this.field.get(this.x, this.y) instanceof UntilledSoil == true){// Will not plant a new crop unless it is on Soil.
				System.out.println(" You cannot plant on untilled soil.\n "
						+ "You've forfeited a turn.");
			}
			else {
				System.out.println("Something is stopping you from planting here.");
			}
		}
		//New logic
		else if (this.choice == 'a') {// Use one of your weapons to attack and try to kill a zombie. 
			Scanner attackObj = new Scanner(System.in);
			System.out.println("Enter:\n"
					+ "Which weapon would you like to use?\n"
					+ "- 'b' to use a bat\n"
					+ "- 'p' to use a pistol\n"
					+ "- 'm' to use a machine gun");
			String weaponChoice = attackObj.next();
			char weapon = weaponChoice.charAt(0);
			if (weapon == 'b') {
				if (this.bat.isOwned() == true) {// Checks if bat is owned.
					if (this.field.get(this.x, this.y) instanceof Zombie == true) {// Checks if a zombie is located at these coordinates.
						this.field.attackZombie(this.x, this.y, this.bat.getDamage());
						if (this.field.get(this.x, this.y).died() == true) {// checks if the zombie has died and untills the soil and adds the contam effect.
							this.field.plant(this.x, this.y, new UntilledSoil(true));
						}
						this.bat.setOwned(false);
					}
					else {// Will alert the user there is nothing to aim at at these coordinates.
						System.out.println("\nDon't aim there!\n");
					}
				}
			}
			else if (weapon == 'p') {
				if (this.pistol.isOwned() == true) {// Checks if pistol is owned.
					if (this.field.get(this.x, this.y) instanceof Zombie == true) {// Checks if zombie is at the coordinates.
						this.field.attackZombie(this.x, this.y, this.pistol.getDamage());
						if (this.field.get(this.x, this.y).died() == true) {// Checks if the zombie is dead.
							this.field.plant(this.x, this.y, new UntilledSoil(true));
						}
						this.pistol.setOwned(false);
					}
					else {
						System.out.println("\nDon't aim there!\n");
					}
				}
			}
			else if (weapon == 'm') {
				if (this.machineGun.isOwned() == true) {// Checks if machine gun is owned.
					if (this.field.get(this.x, this.y) instanceof Zombie == true) {// Checks if zombie is located at these coordinates.
						this.field.attackZombie(this.x, this.y, this.machineGun.getDamage());
						if (this.field.get(this.x, this.y).died() == true) {// Checks if the zombie is dead.
							this.field.plant(this.x, this.y, new UntilledSoil(true));
						}
						this.machineGun.setOwned(false);
					}
					else {
						System.out.println("\nDon't aim there!\n");
					}
				}
			}
		}
		
		else if (this.choice == 'b') {
			Scanner weaponObj = new Scanner(System.in);
			System.out.println("Enter:\n"
					+ "- 'b' to buy a bat for $2 (2 damage)\n"
					+ "- 'p' to buy a pistol for $4 (4 damage)\n"
					+ "- 'm' to buy a machine gun for $5 (6 damage)");
			String weaponInput = weaponObj.next();
			char weaponType = weaponInput.charAt(0);
			if (weaponType == 'b') {
				if (this.bat.isOwned() == false) {
					this.bat.setOwned(true);
					this.balance -= 2;
				}
				else if (this.bat.isOwned() == true){
					System.out.println("\nYou don't need a bat you've already got one.\n");
				}
			}
			
			else if (weaponType == 'p') {// Checks if owned.
				if (this.pistol.isOwned() == false) {
					this.pistol.setOwned(true);;
					this.balance -= 4;
				}
				else if (this.pistol.isOwned() == true){
					System.out.println("\nYou don't need a pistol you've already got one.\n");
				}
			}
			
			else if (weaponType == 'm') {// Checks if owned.
				if (this.machineGun.isOwned() == false) {
					this.machineGun.setOwned(true);
					this.balance -= 5;
				}
				
				else if (this.machineGun.isOwned() == true){ //Checks if owned.
					System.out.println("\nYou don't need a machine gun you've already got one.\n");
				}
			}
			
			else {
				System.out.println("\nNot a valid input.\n");
			}
		}
		// End new Logic
		
		else if (this.choice == 's') {// Prints the Field summary.
			System.out.println(this.field.getSummary());
		}
		else if (this.choice == 'q') { // Quits and ends the game.
			play = "q";
			System.out.println("Thankyou for playing!\n"
					+ "Better luck next time!");
		}
		
		else if (this.choice == 'w'){// Skips the turn, causing everything to tick.
		}
		
		else {// If the command is not a valid input then the turn is skipped.
			System.out.println("\nThe command you just entered didn't match any"
					+ " actions.\nyou have forfeited a turn due to this mistake.\n");
		}
	}
	
	public void run(){// Runs the game logic.
		while (play == "p") {
			this.display();
			try {
				this.userInput();
				this.menuSelection();
			}
			catch (Exception e){
				System.out.println("Not a valid input, turn skipped.");
			}
			this.field.tick();
			this.field.setDay(this.field.getDay() + 1);
			}
		}	
	}