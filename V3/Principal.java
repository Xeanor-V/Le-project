import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Principal extends JFrame implements ActionListener {

	private BufferedImage background, backgroundTile;
	private BufferStrategy strategy;
	private SpriteCache spriteCache;
	private int backgroundY;
	private JFrame ventana_prin;
	private JFrame ventana_menu;
	private JButton espacio, help;
	public static final int WIDTH = 500, HEIGHT = 400;

	public Principal() {
		//spriteCache = new SpriteCache();

		ventana_prin = new JFrame("Guerra en el espacio");
		JPanel panel = (JPanel)ventana_prin.getContentPane();
		setBounds(0,0,WIDTH,HEIGHT);
		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		panel.setLayout(new GridLayout(2,1));
		ventana_prin.setBounds(450,200,WIDTH,HEIGHT);
		ventana_prin.setVisible(true);

		ventana_prin.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		espacio = new JButton("Jugar");
		help = new JButton("Ayuda");

		panel.add(espacio);
		panel.add(help);

		espacio.addActionListener(this);
		help.addActionListener(this);
	}

	/*public void inicio() {
		backgroundTile = spriteCache.getSprite("FEstrellas.gif");
		background = spriteCache.createCompatible(
		           WIDTH,
		 		   HEIGHT+backgroundTile.getHeight(),
		           Transparency.OPAQUE);
		Graphics2D g = (Graphics2D)background.getGraphics();
		g.setPaint( new TexturePaint( backgroundTile, 
		            new Rectangle(0,0,backgroundTile.getWidth(),backgroundTile.getHeight())));
		g.fillRect(0,0,background.getWidth(),background.getHeight());
		backgroundY = backgroundTile.getHeight();
	}

	public void paintEspacio() {
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		g.drawImage( background,0,0,WIDTH,HEIGHT,
		             0,backgroundY,WIDTH,backgroundY+HEIGHT,this);
		paintTitle(g);
		paintButton(g);
		strategy.show();
	}

	public void paintTitle(Graphics2D g) {
		g.setFont(new Font("Arial",Font.BOLD,40));
		g.setPaint(Color.white);
		g.drawString("Guerra Epacial", 20, Escenario.PLAY_HEIGHT - 460);
	}

	public void paintButton(Graphics2D g) {
		g.setPaint(Color.white);
		g.fillRect(220, 200, 150, 45);
		g.setFont(new Font("TimesRoman",Font.BOLD,25));
		g.setPaint(Color.black);
		g.drawString("-> Enter", 240, 225);
	}

	public void keyPressed(KeyEvent e) { //tecla que mueve el jugador y libera balas o bombas
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER :
				espacio = new Espacio();
				espacio.jugar();
				dispose();
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {  

	}*/

	public void actionPerformed(ActionEvent e) {
		JButton btnGet = (JButton)e.getSource();
		if (btnGet == espacio) {
			Espacio esp = new Espacio();
			esp.jugar();
			ventana_prin.setVisible(false);
			dispose();
		}
	}

	public static void main(String[] args) {
		new Principal();
	}
}