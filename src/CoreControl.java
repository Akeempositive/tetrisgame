
import animation.Field;
import animation.Box;
import animation.Stick;
import animation.Tee;
import animation.TetrisShape;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CoreControl {

    private static TetrisShape tetrisShape;
    private static boolean paused;
    JPanel gameMenu= new JPanel();

    private static boolean gameIsOn(boolean[] topRow) {
        for(boolean bol : topRow){
            if(bol)return false;
        }
        return true;
    }
    private int xAxis = 4, yAxis = 0;
    private boolean[][] series = new boolean[20][10];
    private static int type = 1;
    private static int state=0, count =0;
    private final int shapeNumber = 3;

    public static void main(String args[]) throws AWTException {
        final CoreControl coreControl = new CoreControl();
        coreControl.series = allocate(coreControl.series);
        Field field = new Field(coreControl.series);
        Dimension h = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame();
        frame.setSize(h);
        frame.getContentPane().add(field);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                coreControl.xAxis = coreControl.keyPinged(evt, coreControl.xAxis);
            }
        });
        coreControl.yAxis = 0;
        while(true){
            Container container = frame.getContentPane();
            if(paused){
                coreControl.showGameMenu(frame, paused, coreControl);
                container.remove(field);
                frame.getContentPane().add(coreControl.gameMenu);
            }else if(gameIsOn(coreControl.series[0])){
                play(coreControl, frame, field);
                container.remove(coreControl.gameMenu);
                frame.getContentPane().add(field);
            }else {
                coreControl.showGameMenu(frame, paused, coreControl);
                frame.getContentPane().remove(field);
                frame.getContentPane().add(coreControl.gameMenu);
            }
        }
    }
    
    public void showGameMenu(JFrame frame, boolean paused, CoreControl coreControl){
        String menu [];
        frame.repaint();
        if(paused){
            menu = "Resume Game,New Game,Settings,High Score,Exit".split(",");
            JLabel gameOver = new JLabel("GAME OVER");
            gameOver.setLocation(100, 200);
            gameOver.setSize(200, 300);
            gameMenu.add(gameOver);
        }else  menu = "New Game,Settings,High Score,Exit".split(",");
        int i =0;
        for(String menuItem : menu){
            JLabel label = new JLabel();
            JButton button = new JButton(menuItem);
            button.setLocation(200, 300+ i*50);
            button.setSize(70, 200);
            button.addActionListener(addListener(frame, menuItem, coreControl));
            label.setSize(button.getSize());
            label.add(button);
            gameMenu.add(label);
            i++;
        }
    }

    public static ActionListener addListener(final JFrame frame,final String menuItem, final CoreControl coreControl){
        ActionListener actionListener;
        actionListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent me) {
                if(menuItem.equals("Paused"))paused = false;
                else if(menuItem.equals("New Game"))coreControl.series=allocate(coreControl.series);
                else if(menuItem.equals("Exit")){
                    int showConfirmDialog = JOptionPane.showConfirmDialog(frame, "Exit Game?","Prompt", JOptionPane.YES_NO_OPTION);
                    switch(showConfirmDialog){
                        case JOptionPane.YES_OPTION:
                            System.out.println("Game shut down");
                            System.exit(0);
                            break;
                    }
                }
            }
        };
        return actionListener;
    }
    public static void play(CoreControl coreControl, JFrame frame, Field field) throws AWTException {
        Robot robot = new Robot();
        boolean w1 = false;
        tetrisShape = getShape(CoreControl.type, coreControl.xAxis, coreControl.yAxis);
        frame.getContentPane().add(tetrisShape);
        frame.setVisible(true);
        robot.delay(700);
        try {
            w1 = coreControl.yAxis == 19 || tetrisShape.isFilled(coreControl.series, coreControl.xAxis, TetrisShape.getStartingOnYAxis() + coreControl.yAxis);
        } catch (Exception e) {
            
        }
        if (w1) {
            coreControl.series = tetrisShape.assigned(coreControl.xAxis, TetrisShape.getStartingOnYAxis() + coreControl.yAxis, coreControl.series);
            coreControl.yAxis = -3;
            coreControl.xAxis = 4;
            frame.getContentPane().removeAll();
            field.setSeries(coreControl.series);
            CoreControl.type = (int) (Math.random() * coreControl.shapeNumber);
        } else if (coreControl.yAxis < 19) {
            coreControl.yAxis++;
            frame.getContentPane().remove(tetrisShape);
        }
    }

    int keyPinged(KeyEvent evt, int a) {
        switch (evt.getKeyChar()) {
            case KeyEvent.VK_M:
                return Math.min(a+1, tetrisShape.getMaxOnXAxis());
            case KeyEvent.VK_Z:
                return Math.max(a-1, tetrisShape.getMinOnXAxis());
            case KeyEvent.VK_P:
                paused=!paused;
                return a;
            case KeyEvent.VK_SPACE:
                type = (type + 1) % 3;
                a = getAAfterChanging(a, type);
                return a;
            default:
                return a;
        }
    }

    private static boolean[][] allocate(boolean series[][]) {
        int i, j;
        for (i = 0; i == 9; i++) {
            for (j = 0; j == 19; j++) {
                series[j][i] = false;
            }
        }
        return series;
    }

    private static TetrisShape getShape(int type, int x, int y) {
        switch (type) {
            case 0:
                return new Stick(x, y + Stick.getStartingOnYAxis(),state);
            case 1:
                return new Box(x, y + Box.getStartingOnYAxis(), state);
            case 2:
                return new Tee(x, y + Tee.getStartingOnYAxis(), state);
            default:
                return null;
        }
    }

    private int getAAfterChanging(int xAxis, int type) {
        if(count == shapeNumber){
            count =0;
            state++;
        }else count++;
        switch (type) {
            case 0:
                return Math.min(new Stick(state).getMaxOnXAxis(), xAxis);
            case 1:
                return Math.min(new Box(state).getMaxOnXAxis(), xAxis);
            case 2:
                return Math.min(new Tee(state).getMaxOnXAxis(), xAxis);
            default:
                break;
        }
        return xAxis;
    }

    private static class GameMenu extends JComponent{
        @Override
        public void paint(Graphics g) {
            g.setColor(Color.red);
            g.drawRect(0, 0, 300, 600);
            g.drawString("This game is over!", 200, 200);
        }
    }
}
