package animation;

import java.awt.Graphics;

public class Box extends TetrisShape {

    private static final long serialVersionUID = 1L;

    @Override
    public void paint(Graphics g) {
        drawSingleBox(g, x * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
        drawSingleBox(g, (x + 1) * Constants.LENGTH_OF_BOX, y * Constants.LENGTH_OF_BOX);
        drawSingleBox(g, x * Constants.LENGTH_OF_BOX, (1 + y) * Constants.LENGTH_OF_BOX);
        drawSingleBox(g, (1 + x) * Constants.LENGTH_OF_BOX, (y + 1) * Constants.LENGTH_OF_BOX);
    }

    public Box(int state) {
        this(0,0, state);
    }

    public Box(int x, int y, int state) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isFilled(boolean[][] series, int x, int y) {
        return series[y + 1][x + 1] || series[y + 1][x];
    }

    @Override
    public boolean[][] assigned(int x, int y, boolean[][] series) {
        series[y][x] = true;
        series[y - 1][x] = true;
        series[y][x + 1] = true;
        series[y - 1][x + 1] = true;
        series = reduced(series, y, 2);
        return series;
    }

    @Override
    public int getMaxOnXAxis() {
        return 8;
    }
    
    @Override
    public int getMinOnXAxis(){
        return 0;
    }

    public static int getStartingOnYAxis() {
        return -3;
    }
}
