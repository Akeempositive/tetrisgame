package animation;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author POSITIVITy
 */
public class Field extends JComponent {
    private boolean series[][] ;

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(0, 0, 300, 600);
        renderFrameFromSeries(series, g);
    }
    
    public Field(boolean series[][]){
        this.series = series;
    }
    
     public void renderFrameFromSeries(boolean series[][], Graphics g) {
        for (int i = 0; i < series.length; i++) {
            boolean row[] = series[i];
            for (int j = 0; j < series[0].length; j++) {
                boolean column = row[j];
                if (column) {
                    drawSingleBox(g, i, j, Constants.LENGTH_OF_BOX, Constants.LENGTH_OF_BOX);
                }
            }
        }
    }

    public boolean[][] getSeries() {
        return series;
    }

    public void setSeries(boolean[][] series) {
        this.series = series;
    }

    private void drawSingleBox(Graphics g, int b, int a, int c, int d) {
        g.setColor(Color.black);
        g.fillRect(a * 30, b * 30, c, d);
        g.setColor(Color.red);
        g.fillRect(a * 30 + 2, b * 30 + 2, c - 4, d - 4);
    }
}
