package gui;

import base.DisplayDriver;
import base.Grid;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class JavaFXDisplayDriver implements DisplayDriver {
    private Pane tilePane = new Pane();
    private int gridSize;
    private double strokeWidth;

    public JavaFXDisplayDriver(int gridSize, double recSize, Grid grid) {
        setGridSize(gridSize);
        setStrokeWidth(recSize/20);

        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                Color c = whichColor(grid.getTileCurrentState(i, j));
                Rectangle r = new Rectangle(recSize-strokeWidth, recSize-strokeWidth, c);
                r.setX(j*recSize);
                r.setY(i*recSize);
                r.setStroke(Color.GRAY);
                r.setStrokeWidth(strokeWidth);
                tilePane.getChildren().add(r);
            }
        }
    }

    @Override
    public void displayGrid(Grid grid) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Rectangle r = (Rectangle) tilePane.getChildren().get(i * gridSize + j);
                r.setFill(whichColor(grid.getTileCurrentState(i, j)));
            }
        }
    }

    private Color whichColor(int state) {
        if(state == 0) {
            return Color.BLACK;
        } else if(state == 1) {
            return Color.BLUE;
        } else if(state == 2) {
            return Color.RED;
        } else {
            return Color.YELLOW;
        }
    }

    public void colorTile(int i, int j, int chosenColor) {
        Rectangle r = (Rectangle) tilePane.getChildren().get(i * gridSize + j);
        r.setFill(whichColor(chosenColor));
    }

    public Pane getTilePane() {
        return tilePane;
    }

    private void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    private void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }
}
