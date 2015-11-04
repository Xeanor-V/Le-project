import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KeyBidings extends JFrame {
    int x[]= new int[3];
    int y[] = new int [3];
    int x1=100;
    
    int y1 =100;
    
    DrawPanel drawPanel = new DrawPanel();
    
    public KeyBidings(){
        Action rightAction = new AbstractAction(){
        
            public void actionPerformed(ActionEvent e) {
                if((x[2])<200)
                {
                x[2] +=10;
                x[1] +=10;
                x[0] +=10;
                drawPanel.repaint();
                }

            }
        };
        Action leftAction = new AbstractAction(){
        
            public void actionPerformed(ActionEvent e) {
            if(x[0]!=0)
            {
               x[2] -=10;
                x[1] -=10;
                x[0] -=10;
                drawPanel.repaint();
            }
            }
        };
        
        Action upAction = new AbstractAction(){
        
            public void actionPerformed(ActionEvent e) {
                if(y[1]!=0)
                {
                 
                y[2]-=10;
                y[1] -=10;
                y[0] -=10;
                drawPanel.repaint();
                }
            }
        };
        Action downAction = new AbstractAction(){
        
            public void actionPerformed(ActionEvent e) {
                if((y[2])<200)
                {
                y[2] +=10;
                y[1] +=10;
                y[0] +=10;
                drawPanel.repaint();
                }
            }
        };
                x[2] =50;
                x[1] =25;
                x[0] =0;
                
                y[2] =50;
                y[1] =0;
                y[0] =50;
            InputMap inputMap = drawPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = drawPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        actionMap.put("rightAction", rightAction);
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        actionMap.put("leftAction", leftAction);
        inputMap.put(KeyStroke.getKeyStroke("UP"), "upAction");
        actionMap.put("upAction", upAction);
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        actionMap.put("downAction", downAction);
        add(drawPanel);
        
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       // setSize(200,200);
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    private class DrawPanel extends JPanel {


        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.GREEN);
            g.drawPolygon(x, y, 3);
            //g.setColor(Color.BLACK);
            //g.fillRect(x+5,y+5,40,40);
        }

        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                new KeyBidings();
            }
        });
    }
}