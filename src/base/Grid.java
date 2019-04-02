package base;

import java.util.ArrayList;

public class Grid {
    private ArrayList<ArrayList<Tile>> matrix;
    private int height;
    private int width;

    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
        setMatrix(height, width);
    }


    private void setMatrix(int height, int width) {
        matrix = new ArrayList<ArrayList<Tile>>();

        for(int i = 0; i < height; i++) {
            matrix.add(new ArrayList<Tile>());
            for(int j = 0; j < width; j++) {
                matrix.get(i).add(new Tile(i, j));
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<ArrayList<Tile>> getMatrix() {
        return matrix;
    }

    public Tile getTile(int i, int j) {
        return matrix.get(i).get(j);
    }

    public int getTileCurrentState(int i, int j) {
        return matrix.get(i).get(j).getCurrentState();
    }

    public int getTileNextState(int i, int j) {
        return matrix.get(i).get(j).getNextState();
    }

    public void setCurrentTileBlack(int i, int j) { matrix.get(i).get(j).setCurrentState(0); }

    public void setCurrentTileRed(int i, int j) {
        matrix.get(i).get(j).setCurrentState(2);
    }

    public void setCurrentTileYellow(int i, int j) {
        matrix.get(i).get(j).setCurrentState(3);
    }

    public void setCurrentTileBlue(int i, int j) {
        matrix.get(i).get(j).setCurrentState(1);
    }

    public void setNextTileBlack(int i, int j) {
        matrix.get(i).get(j).setNextState(0);
    }

    public void setNextTileRed(int i, int j) {
        matrix.get(i).get(j).setNextState(2);
    }

    public void setNextTileYellow(int i, int j) {
        matrix.get(i).get(j).setNextState(3);
    }

    public void setNextTileBlue(int i, int j) {
        matrix.get(i).get(j).setNextState(1);
    }

    public void setCurrent(int i, int j, int state) {
        matrix.get(i).get(j).setCurrentState(state);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                sb.append(matrix.get(i).get(j).getCurrentState() + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void currentBecomesNext() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                matrix.get(i).get(j).setCurrentState(getTileNextState(i, j));
            }
        }
    }
}
