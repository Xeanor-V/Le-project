import java.io.*;
import java.awt.*;
import java.awt.image.VolatileImage;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.*;
    import java.awt.image.DataBufferByte;
    import java.io.IOException;
    import javax.imageio.ImageIO;
    import java.io.File;

public class Volatile
{
	int h,w;
	JLabel imagen;
	public Volatile(String nombre)
	{
		VolatileImage im = loadFromFile(nombre);
		JFrame container = new JFrame("Volatile");

		container.setSize(1000,1000);
		container.setLayout(null);
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		imagen = new JLabel (new ImageIcon(im));
		container.add(imagen);

		//imagen.setLocation(100,100);
		imagen.setBounds(0,0,w,h);
		container.getContentPane().validate();
		//container.repaint();
		container.setVisible(true);
		Thread thread = new Thread();
		move();
	}
	public void move()
	{
		int x=0;
		while(x<=1000)
		{
			x+=2;
			imagen.setBounds(x,0,w,h);
			try
			{
			Thread.sleep(1);
			}
			catch(InterruptedException e)
			{

			}

		}


	}

	public VolatileImage loadFromFile(String filename) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
 
	// Loads the image from a file using ImageIO.
                try
                {
		BufferedImage bimage = ImageIO.read( new File(filename) );
                // From Code Example 2.
		h=bimage.getHeight();
		w=bimage.getWidth();
		VolatileImage vimage = createVolatileImage(w, h, Transparency.TRANSLUCENT);
 
                   
		Graphics2D g = null;
 
		try {
			g = vimage.createGraphics();
 
			// These commands cause the Graphics2D object to clear to (0,0,0,0).
			g.setComposite(AlphaComposite.Src);
			g.setColor(Color.black);
			g.clearRect(0, 0, vimage.getWidth(), vimage.getHeight()); // Clears the image.
 
			g.drawImage(bimage,null,0,0);
		} finally {	
			// It's always best to dispose of your Graphics objects.
			g.dispose();
		}
 
		return vimage;
                }
                catch(IOException e)
                {
                    
                }
                return null;
	
	}
        
        private VolatileImage createVolatileImage(int width, int height, int transparency) 
        {	
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
			VolatileImage image = null;
 
			image = gc.createCompatibleVolatileImage(width, height, transparency);
 
			int valid = image.validate(gc);
 
			if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
				image = this.createVolatileImage(width, height, transparency);
				return image;
			}
 
			return image;
		}
	public static void main(String[] args) 
	{
		new Volatile("Nave.gif");
	}
}