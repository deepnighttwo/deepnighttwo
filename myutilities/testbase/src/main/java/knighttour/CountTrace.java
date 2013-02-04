/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Mark Zang
 * 
 */
package knighttour;


public class CountTrace {

    private int startX;
    private int startY;
    private int curStep = 0;
    private Step[] stepTrace;
    private int steps;
    
    static boolean[][] takenLocs;

    public CountTrace(int x, int y, int startX, int startY) {
        steps = x * y;
        stepTrace = new Step[steps];
        Step.boundX = x - 1;
        Step.boundY = y - 1;
        this.startX = startX - 1;
        this.startY = startY - 1;
        initSystem();
    }

    private void initSystem() {
        Step.locs = new Location[Step.boundX + 1][Step.boundY + 1];
        for (int col = 0; col < Step.boundX + 1; col++) {
            for (int row = 0; row < Step.boundY + 1; row++) {
                Step.locs[col][row] = new Location(col, row);
            }
        }
        takenLocs = new boolean[Step.boundX + 1][Step.boundY + 1];
        for (int col = 0; col < Step.boundX + 1; col++) {
            for (int row = 0; row < Step.boundY + 1; row++) {
            	takenLocs[col][row] = false;
            }
        }
        Step.takenLocs = takenLocs;
        
        Location startLoc = Step.locs[startX][startY];
        
        for (int i = 0; i < steps; i++) {
            stepTrace[i] = new Step();
        }
        
        stepTrace[0].updateLocation(startLoc);
        
        takenLocs[startX][startY] = true;
        curStep = 0;
    }

    public void countSteps() {
        while (true) {
            // printHistory();
            if (curStep < 0) {
                System.out.println("No solution");
                break;
            }
            Step curStepInst = stepTrace[curStep];

            Location next = curStepInst.getNextPossibleLocation();
            if (next == null) {
                if (curStep == (steps - 1)) {
                    break;
                }
                // no next, not reached, step back
                takenLocs[curStepInst.loc.x][curStepInst.loc.y] = false;
                curStepInst.loc = null;
                curStep--;
                continue;
            }
            curStep++;
            takenLocs[next.x][next.y] = true;
            stepTrace[curStep].updateLocation(next);
        }
        if (curStep == (steps - 1)) {
            printSteps();
        }
    }

    private void printSteps() {
        int[][] result = new int[Step.boundX + 1][Step.boundY + 1];
        for (int i = 0; i < steps; i++) {
            Step step = stepTrace[i];
            result[step.loc.x][step.loc.y] = i;
        }
        int width = String.valueOf(steps + 1).length() + 2;
        String format = "%" + width + "d";
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.printf(format, (result[i][j] + 1));
            }
            System.out.println();
        }

    }
    
    
    public void printStatus() {
        for (int i = 0; i < takenLocs.length; i++) {
            for (int j = 0; j < takenLocs[0].length; j++) {
                System.out.print(" "+takenLocs[i][j]);
            }
            System.out.println();
        }

    }
}
