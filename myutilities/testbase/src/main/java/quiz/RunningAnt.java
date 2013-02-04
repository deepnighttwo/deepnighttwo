package quiz;

public class RunningAnt {

	// be 1 or -1 means go forward or backward
	private int delta = 0;

	private int location = -1;

	private String name;

	public RunningAnt(boolean forward, int location, String name) {
		if (forward) {
			delta = 1;
		} else {
			delta = -1;
		}
		System.out.println(forward);
		this.location = location;
		this.name = name;
	}

	public void changeDirection() {
		delta *= -1;
	}

	public int getLocation() {
		return location;
	}

	public void moveOneStep() {
		location += delta;
	}

	public String toString() {
		return new StringBuilder()
				.append(name + "\t" + delta + "\t" + location).toString();
	}
}
