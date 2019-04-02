package base;

public class Tile {
    private int currentState;
    private int nextState;
    private int i;
    private int j;

    public Tile(int i, int j) {
        setLocation(i, j);
        setCurrentState(0);
    }

    public void setLocation(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public void setNextState(int nextState) {
        this.nextState = nextState;
    }

    public int getCurrentState() {
        return currentState;
    }

    public int getNextState() {
        return nextState;
    }
}
