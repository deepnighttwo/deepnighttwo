/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Mark Zang
 * 
 */

package knighttour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Step {

    static int boundX;
    static int boundY;
    static Location[][] locs;

    private static int[] deltaX = { -2, -2, -1, -1, 2, 2, 1, 1 };
    private static int[] deltaY = { 1, -1, 2, -2, 1, -1, 2, -2 };

    static boolean[][] takenLocs;

    int curPosition;
    Location loc;

    Location[] possibleArr;

    public void updateLocation(Location loc) {
        this.loc = loc;
        curPosition = 0;
        List<Location> allPossiblLoc = getValidatedSteps(loc, null);
        List<Location> temp = new ArrayList<Location>();
        List<ComparableLocation> sorted = new ArrayList<ComparableLocation>();
        for (Location nextLoc : allPossiblLoc) {
            temp.clear();
            int nextPoss = getValidatedSteps(nextLoc, temp).size();
            sorted.add(new ComparableLocation(nextLoc, nextPoss));
        }
        Collections.sort(sorted);
        possibleArr = new Location[sorted.size()];
        for (int i = 0; i < possibleArr.length; i++) {
            possibleArr[i] = sorted.get(i).loc;
        }
    }

    private List<Location> getValidatedSteps(Location start, List<Location> list) {
        if (list == null) {
            list = new ArrayList<Location>();
        }

        for (int index = 0; index < 8;) {
            int newX = start.x + deltaX[index];
            int newY = start.y + deltaY[index];
            index++;
            if (newX >= 0 && newX <= boundX && newY >= 0 && newY <= boundY) {
                Location newLoc = locs[newX][newY];
                if (takenLocs[newX][newY] == true) {
                    continue;
                }
                list.add(newLoc);
            }
        }
        return list;
    }

    public Location getNextPossibleLocation() {
        if (curPosition < possibleArr.length) {
            Location loc = possibleArr[curPosition];
            curPosition++;
            return loc;
        }
        return null;
    }
}
