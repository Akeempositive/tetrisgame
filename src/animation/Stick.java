/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author POSITIVITy
 */
public class Stick extends TetrisShape {

    @Override
    public void paint(Graphics g) {
        switch (state) {
            case 0:
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, (y + 1) * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, (y + 2) * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, (y + 3) * Constants.LENGTH_OF_BOX);
                break;
            case 1:
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x + 1) * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x - 1) *Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x + 2) * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                break;
        }

    }

    public Stick(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state%2;
    }

    @Override
    public boolean isFilled(boolean[][] series, int x, int y) {
        switch (state) {
            case 0:
                return series[y + 1][x];
            case 1:
                return series[y + 1][x] || series[y + 1][x + 1] || series[y + 1][x - 1] || series[y + 1][x + 2];
            default:
                return false;
        }
    }

    public Stick(int state) {
        this(0,0,state);
    }

    @Override
    public boolean[][] assigned(int x, int y, boolean[][] series) {
        switch (state) {
            case 0:
                series[y][x] = true;
                series[y - 1][x] = true;
                series[y - 2][x] = true;
                series[y - 3][x] = true;
                return reduced(series, y, 4);
            case 1:
                series[y][x] = true;
                series[y][x + 1] = true;
                series[y][x - 1] = true;
                series[y][x + 2] = true;
                return reduced(series, y, 1);
            default:
                return series;
        }
    }

    @Override
    public int getMaxOnXAxis() {
        return state==0?9:6;
    }

    public static int getStartingOnYAxis() {
        return -3;
    }

    @Override
    public int getMinOnXAxis() {
        return state==0?0:1;
    }
}
