/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Mark Zang
 * 
 */
package knighttour;

public class ComparableLocation implements Comparable<ComparableLocation> {
    Location loc;
    int possible;

    public ComparableLocation(Location loc, int possible) {
        this.loc = loc;
        this.possible = possible;
    }

    @Override
    public int compareTo(ComparableLocation o) {
        return this.possible - o.possible;
    }
}
