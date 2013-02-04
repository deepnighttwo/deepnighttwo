/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Mark Zang
 * 
 */
package knighttour;

public class Location {
    int x;
    int y;
    Integer identity;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        if (y >= 10000) {
            throw new IllegalArgumentException(
                    "Y couldn't be bigger than 10000");
        } else if (x >= 10000) {
            throw new IllegalArgumentException(
                    "X couldn't be bigger than 10000");
        }
        identity = new Integer(x * 10000 + y);
    }

}
