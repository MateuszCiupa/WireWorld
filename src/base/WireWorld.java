package base;

import java.util.ArrayList;

public class WireWorld {
    private Grid gridOfTiles;
    private ArrayList<Tile> blueTiles;

    public WireWorld(int height, int width) {
        gridOfTiles = new Grid(height, width);
        blueTiles = new ArrayList<Tile>();
    }

    public void calculateWireWorld() {
        pickElectronHeads();
        calculateNextState();
    }

    private void calculateNextState() {
        for(int i = 0; i < gridOfTiles.getHeight(); i++) {
            for(int j = 0; j < gridOfTiles.getWidth(); j++) {
                int state = gridOfTiles.getTileCurrentState(i, j);

                switch(state) {
                    case 1:
                        gridOfTiles.setNextTileRed(i, j);
                        break;
                    case 2:
                        gridOfTiles.setNextTileYellow(i, j);
                        break;
                    case 3:
                        if(countBlueNeighbors(i, j) == 1 || countBlueNeighbors(i, j) == 2)
                            gridOfTiles.setNextTileBlue(i, j);
                        else
                            gridOfTiles.setNextTileYellow(i, j);
                        break;
                }
            }
        }

        gridOfTiles.currentBecomesNext();
    }

    private int countBlueNeighbors(int i, int j) {
        int blueNeighbors = 0;

        if(blueTiles.isEmpty())
            return 0;

        for(Tile tile : blueTiles) {
            if(tile.getI() == i-1 && tile.getJ() == j-1 ||
                    tile.getI() == i-1 && tile.getJ() == j ||
                    tile.getI() == i-1 && tile.getJ() == j+1 ||
                    tile.getI() == i && tile.getJ() == j-1 ||
                    tile.getI() == i && tile.getJ() == j+1 ||
                    tile.getI() == i+1 && tile.getJ() == j-1 ||
                    tile.getI() == i+1 && tile.getJ() == j ||
                    tile.getI() == i+1 && tile.getJ() == j+1)
                blueNeighbors++;
        }

        return blueNeighbors;
    }

    public Grid getGridOfTiles() {
        return gridOfTiles;
    }

    public void pickElectronHeads() {
        if(!blueTiles.isEmpty()) {
            blueTiles.clear();
        }

        for(int i = 0; i < gridOfTiles.getHeight(); i++) {
            for(int j = 0; j < gridOfTiles.getWidth(); j++) {
                if(gridOfTiles.getTileCurrentState(i, j) == 1)
                    blueTiles.add(gridOfTiles.getTile(i, j));
            }
        }
    }

    public static void main(String[] args) {
        WireWorld wireWorld = new WireWorld(10, 10);

        for(int i = 0; i < wireWorld.getGridOfTiles().getWidth(); i++) {
            wireWorld.getGridOfTiles().setCurrentTileYellow(1, i);
        }

        wireWorld.getGridOfTiles().setCurrentTileRed(1, 0);
        wireWorld.getGridOfTiles().setCurrentTileBlue(1, 1);

        System.out.println(wireWorld.getGridOfTiles());
        System.out.println();

        for(int i = 0; i < 5; i++) {
            wireWorld.calculateWireWorld();
            System.out.println(wireWorld.getGridOfTiles());
            System.out.println();
        }
    }
}
