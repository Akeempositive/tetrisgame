/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Graphics;

/**
 *
 * @author Positive
 */
public class Tee extends TetrisShape {
   //todo minimum for xAxis should be one
    @Override
    public boolean isFilled(boolean[][] series, int x, int y) {
        switch (state) {
            case 0:
                return series[y + 1][x + 1] || series[y + 1][x] || series[y + 1][x - 1];
            case 1:
                return series[y + 1][x] || series[y][x - 1] || series[y][x + 1];
            case 2:
                return series[y + 1][x] || series[y][x - 1];
            case 3:
                return series[y + 1][x] || series[y][x + 1];
            default:
                return true;
        }
    }

    @Override
    public void paint(Graphics g) {
        switch (state) {
            case 0:
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, Constants.LENGTH_OF_BOX * (1 + x), y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x - 1) * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, (y - 1) * Constants.LENGTH_OF_BOX);
                break;
            case 1:
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x - 1) * Constants.LENGTH_OF_BOX, (y - 1) * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, (y - 1) * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x + 1) * Constants.LENGTH_OF_BOX, (y - 1) * Constants.LENGTH_OF_BOX);
                break;
            case 2:
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x - 1) * Constants.LENGTH_OF_BOX, (y - 1) * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, (y - 1) * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x) * Constants.LENGTH_OF_BOX, (y - 2) * Constants.LENGTH_OF_BOX);
                break;
            case 3:
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x + 1) * Constants.LENGTH_OF_BOX, (y - 1) * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, x * Constants.LENGTH_OF_BOX, (y - 1) * Constants.LENGTH_OF_BOX);
                drawSingleBox(g, (x) * Constants.LENGTH_OF_BOX, (y -2) * Constants.LENGTH_OF_BOX);
                break;
        }
    }

    @Override
    public boolean[][] assigned(int x, int y, boolean[][] series) {
        switch (state) {
            case 0:
                series[y][x + 1] = true;
                series[y][x] = true;
                series[y][x - 1] = true;
                series[y - 1][x] = true;
                return reduced(series, y, 2);
            case 1:
                series[y-1][x + 1] = true;
                series[y][x] = true;
                series[y-1][x - 1] = true;
                series[y - 1][x] = true;
                return reduced(series, y, 2);
            case 2:
                series[y-1][x - 1] = true;
                series[y][x] = true;
                series[y-1][x] = true;
                series[y - 2][x] = true;
                return reduced(series, y, 3);
            case 3:
                series[y-1][x + 1] = true;
                series[y][x] = true;
                series[y-1][x] = true;
                series[y - 2][x] = true;
                return reduced(series, y, 3);
            default:
                return series;
        }
        
    }

    @Override
    public int getMaxOnXAxis() {
        return state<=1?7:8;
    }

    @Override
    public int getMinOnXAxis(){
        return state<=1?1:0;
    }
    public static int getStartingOnYAxis() {
        return -2;
    }

    public Tee(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state%4;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Tee(int state) {
        this(0,0, state);
    }
}
