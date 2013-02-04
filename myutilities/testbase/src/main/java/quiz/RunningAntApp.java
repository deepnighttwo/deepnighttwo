package quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunningAntApp {

	public static void main(String[] args) {
		List<Integer> time = new ArrayList<Integer>();
		for (int i = 0; i < 32; i++) {
			Stick stick = new Stick(28);
			stick.putAntAt(new RunningAnt((i & 1) == 1, 3, "3"));
			stick.putAntAt(new RunningAnt((i & 2) == 2, 7, "7"));
			stick.putAntAt(new RunningAnt((i & 4) == 4, 11, "11"));
			stick.putAntAt(new RunningAnt((i & 8) == 8, 17, "17"));
			stick.putAntAt(new RunningAnt((i & 16) == 16, 23, "23"));
			time.add(stick.keepGoing());
		}
		Collections.sort(time);
		System.out.println("Shortest time:" + time.get(0));
		System.out.println("Longest time:" + time.get(time.size() - 1));

	}

}
