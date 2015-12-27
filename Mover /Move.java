    import java.awt.image.BufferedImage;
    import java.awt.image.DataBufferByte;
    import java.io.IOException;
    import javax.imageio.ImageIO;
    import javax.swing.*;
    import java.util.*;
    import java.awt.*;
    import java.io.File;

    
public class Move extends Canvas
{
    private static int [][]imgRGB;
    Thread thread;
    int h=400,w=500;
    public Move(String nombre)
    {
        try
        {
           BufferedImage hugeImage = ImageIO.read(new File(nombre));
           imgRGB = convertToRGB(hugeImage);
            thread = new Thread();
           
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
    
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        while(true)
        {
            h--;
            if(h==0)
            {
              //  thread.stop();
                break;
            }
           /* try
            {
            //Thread.sleep(1);
            }
            //catch(InterruptedException e)
            //{
                
           }*/
        System.out.println(h+" "+w);
        //System.out.println(imgRGB[0][0]);
        for(int i=0 ; i<imgRGB.length ; i++)
        {
            for(int j=0 ; j<imgRGB[i].length; j++)
            {
                g.setColor(new Color(imgRGB[i][j]));
                if(imgRGB[i][j]==-1 || imgRGB[i][j]==0)
                    continue;
                int y = i+w, x = j+h;
                g.drawLine(y,x,y,x);
            }
        }
        }
        
    }
    private static int[][] convertToRGB(BufferedImage image) {

     int width = image.getWidth();
      int height = image.getHeight();
      System.out.println(width + " " + height);
      int[][] result = new int[height][width];

      for (int row = 0; row < width; row++) {
         for (int col = 0; col < height; col++) {
             try
             {
            result[row][col] = image.getRGB(row, col);
             }
             catch(Exception e)
             {
                 System.out.print(e);
             }
         }
      }

      return result;
    }

    public static void main(String[] args) 
    {
        JFrame container = new JFrame("pixel");
        container.add(new Move("Nave.gif"));
        container.setSize(1000,1000);
        container.setVisible(true);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
}
