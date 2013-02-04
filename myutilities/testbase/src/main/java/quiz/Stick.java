package quiz;

import java.util.ArrayList;
import java.util.List;

public class Stick {

	private int pointCount = 0;

	// index 0 means going out, index 27 means going out
	private RunningAnt[] stick = null;

	private List<RunningAnt> ants = new ArrayList<RunningAnt>();

	public Stick(int p_pointCount) {
		pointCount = p_pointCount;
		stick = new RunningAnt[pointCount];
	}

	public void putAntAt(RunningAnt ant) {
		stick[ant.getLocation()] = ant;
		ants.add(ant);
	}

	public int keepGoing() {
		int time = 0;
		while (true) {
			time++;

			// ants are leaving old location
			for (int i = 0; i < pointCount; i++) {
				stick[i] = null;
			}

			// ants are arriving at new location
			for (RunningAnt ant : ants) {
				ant.moveOneStep();
			}

			// handle going out or go across another ant condition
			List<RunningAnt> antLeft = new ArrayList<RunningAnt>();

			for (RunningAnt ant : ants) {
				int location = ant.getLocation();

				if (location == 0 || location == (pointCount - 1)) {
					antLeft.add(ant);
					// System.out.println("One Ant lefted:\t" + ant.toString());
					stick[location] = null;
					continue;
				}
				// System.out.println(ant.toString());

				if (stick[location] == null) {
					stick[location] = ant;
				} else {
					RunningAnt anotherAnt = stick[location];
					ant.changeDirection();
					anotherAnt.changeDirection();
				}
			}

			ants.removeAll(antLeft);

			if (ants.size() == 0) {
				System.out.println("All ants left! Time used:" + time);
				return time;
			}
		}
	}
}
