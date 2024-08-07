package students.items;

public class UntilledSoil extends Item{
	//New logic
	private boolean contaminated = false;
	private int contamTime = 2;
	
	public UntilledSoil(boolean contaminated) {// Constructs the UntilledSoil Class using preset values.
		super(0, 0, -1);
		this.contaminated = contaminated;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {// Returns the string representation of the UntilledSoil class.
		if (this.contaminated == true) {
			return "~"; // New Logic
		}
		else {
			return "/";
		}
	}
// New logic
	public boolean isContaminated() {
		return contaminated;
	}

	public void setContaminated(boolean contaminated) {
		this.contaminated = contaminated;
	}

	public int getContamTime() {
		return contamTime;
	}

	public void contamTime() {
		this.contamTime -= 1;
		if (contamTime == 0) {
			this.contaminated = false;
			this.contamTime = 2;
		}
	}
	
}
