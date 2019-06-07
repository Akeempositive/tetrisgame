/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Positive
 */
public class SingleBox extends JComponent {

    int a, b, c, d;

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(a * 30, b * 30, c, d);
        g.setColor(Color.red);
        g.fillRect(a * 30 + 2, b * 30 + 2, c - 4, d - 4);
    }
    
    public SingleBox(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
