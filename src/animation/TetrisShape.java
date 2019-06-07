/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 *
 * @author Positive
 */
public abstract class TetrisShape extends JComponent {
    
    protected int x, y;
    static int state;
    
    @Override
    public abstract void paint(Graphics g);
    
    public abstract boolean isFilled(boolean series[][], int x, int y);

    public abstract boolean[][] assigned(int x, int y, boolean[][] series);
    
    public abstract int getMaxOnXAxis();
    
    public abstract int getMinOnXAxis();
    
    public static int getStartingOnYAxis(){return 0;}

    boolean canReduced(boolean series[][], int y) {
        boolean reduceRow = true;
        int j = 0;
        while (j < series[0].length) {
            if (!series[y][j]) {
                reduceRow = false;
                break;
            }
            j++;
        }
        return reduceRow;
    }

    boolean[][] reduced(boolean[][] series, int y, int depth) {
        int i = 0;

        while (i < depth) {
            if (canReduced(series, y - i)) {
                int from = y - i;
                while (from >= 1) {
                    series[from] = series[from - 1];
                    from--;
                }
                depth--;
            } else {
                i++;
            }
        }
        return series;
    }
        
    public void drawSingleBox(Graphics g, int a, int b) {
        g.setColor(Color.black);
        g.fillRect(a , b , Constants.LENGTH_OF_BOX, Constants.LENGTH_OF_BOX);
        g.setColor(Color.red);
        g.fillRect(a  + 2, b + 2, Constants.LENGTH_OF_BOX - 4, Constants.LENGTH_OF_BOX - 4);
    }

}
