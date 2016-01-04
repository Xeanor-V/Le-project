import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Espacio extends Canvas implements Escenario, KeyListener, ActionListener {
	
	private BufferedImage background, backgroundTile;
	private BufferStrategy strategy;
	private SpriteCache spriteCache;
	private ArrayList actores; 
	private int backgroundY;
	private long usedTime;
	private Jugador player;
	private soundCache sounds;

	private Boolean juegoFin = false;
	private Boolean ganador = false;
	
	public Espacio() {
		spriteCache = new SpriteCache();
		sounds = new soundCache();
		
		JFrame ventana = new JFrame("Juego en el espacio");
		JPanel panel = (JPanel)ventana.getContentPane();
		setBounds(0, 0, Escenario.WIDTH ,Escenario.HEIGHT);
		panel.setPreferredSize(new Dimension(Escenario.WIDTH,Escenario.HEIGHT));
		panel.setLayout(null);
		ventana.setBounds(300,50,Escenario.WIDTH,Escenario.HEIGHT);
		ventana.setVisible(true);

		ventana.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		panel.add(this);

		ventana.setResizable(false);
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		requestFocus();
		addKeyListener(this);
    	setIgnoreRepaint(true);
		BufferedImage cursor = spriteCache.createCompatible(10,10,Transparency.BITMASK);
		Toolkit t = Toolkit.getDefaultToolkit();
		Cursor c = t.createCustomCursor(cursor,new Point(5,5),"null");	//hacemos que el cursosr no se vea
		setCursor(c);
	}
	
	public void gameOver() {	//se implementa el método de la interfaz Escenario 
		juegoFin = true;
	}

	public void winner() {	//se implementa el método de la interfaz Escenario 
		ganador = true;
	}
	
	public void iniciar() {
	    actores = new ArrayList();
	    for (int i = 0; i < 10; i++) {
			Monstruo m = new Monstruo(this);
			m.setX( (int)(Math.random()*Escenario.WIDTH) );
			m.setY( i*20 );
			m.setVx( (int)(Math.random()*20-10) );
			actores.add(m);
    	}

    	for (int i=0; i<10; i++) {
    		Asteroide a = new Asteroide(this);
    		a.setX((int)(Math.random()*Escenario.WIDTH));
    		a.setY(0);
    		a.setVy( (int)(Math.random()*20-10) );
    		actores.add(a);
    	}
    
		player = new Jugador(this);
		player.setX(Escenario.WIDTH/2);
		player.setY(Escenario.PLAY_HEIGHT - 2*player.getHeight());

		backgroundTile = spriteCache.getSprite("FEstrellas.gif");
		background = spriteCache.createCompatible(
		           Escenario.WIDTH,
		 		   Escenario.HEIGHT+backgroundTile.getHeight(),
		           Transparency.OPAQUE);
		Graphics2D g = (Graphics2D)background.getGraphics();
		g.setPaint( new TexturePaint( backgroundTile, 
		            new Rectangle(0,0,backgroundTile.getWidth(),backgroundTile.getHeight())));
		g.fillRect(0,0,background.getWidth(),background.getHeight());
		backgroundY = backgroundTile.getHeight();
	}
	
	public void addActor(Actor a) {
		actores.add(a);
	}
	
	public Jugador getPlayer() {
		return player;
	}
	
	public void cargarEspacio() {
		int i = 0;
		while (i < actores.size()) {
			Actor m = (Actor)actores.get(i);
			if (m.isForRemove()) {
				actores.remove(i);
			} else {
				m.act();
				i++;
			}
		}
		player.act();
	}
	
	public void checarColisiones() {	//revisar colisiones durante el juego
		Rectangle playerBounds = player.getBounds();
		for (int i = 0; i < actores.size(); i++) {
			Actor a1 = (Actor)actores.get(i);
			Rectangle r1 = a1.getBounds();
			if (r1.intersects(playerBounds)) {
				player.collision(a1);
				a1.collision(player);
			}
			for (int j = i+1; j < actores.size(); j++) {
				Actor a2 = (Actor)actores.get(j);
				Rectangle r2 = a2.getBounds();
				if (r1.intersects(r2)) {
					a1.collision(a2);
					a2.collision(a1);
				}
			}
		}
	}
	
	public void paintEscudos(Graphics2D g) {
		g.setPaint(Color.red);
		g.fillRect(100,Escenario.PLAY_HEIGHT,Jugador.MAX_SHIELDS,30);
		g.setPaint(Color.green);
		g.fillRect(100+Jugador.MAX_SHIELDS-player.getShields(),Escenario.PLAY_HEIGHT,player.getShields(),30);
		g.setFont(new Font("Georgia", Font.BOLD,20));
		g.setPaint(Color.white);
		g.drawString("Vida: ", 20, Escenario.PLAY_HEIGHT+20);
	}
    
	public void paintScore(Graphics2D g) {
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setPaint(Color.white);
		g.drawString("Puntuacion:", 20, Escenario.PLAY_HEIGHT - 460);
		g.setPaint(Color.red);
		if (player.getScore() >= 0) {
			g.drawString(player.getScore()+"",170,Escenario.PLAY_HEIGHT-460);
		} else {
			player.setScore(0);
			g.drawString(player.getScore()+"",170,Escenario.PLAY_HEIGHT-460);
		}
	}
	
	public void paintBombs(Graphics2D g) {	//bombas dismponibles
		int xBase = Jugador.MAX_SHIELDS;
		for (int i = 0; i < player.getClusterBombs(); i++) {
			BufferedImage bomb = spriteCache.getSprite("bombaUpLeft.gif");
			g.drawImage(bomb, xBase+i*bomb.getWidth()-50, Escenario.PLAY_HEIGHT+40, this);
		}
		g.setPaint(Color.white);
		g.drawString("Bombas: ", 20, Escenario.PLAY_HEIGHT+50);
	}	
	
	public void paintStatus(Graphics2D g) {
		g.setPaint(Color.black);
		g.fillRect(0, 0, 220, 50);
		g.setPaint(Color.black);
		g.fillRect(0, Escenario.PLAY_HEIGHT-5, 350, Escenario.PLAY_HEIGHT);
		g.setPaint(Color.black);
		g.fillRect(350,  Escenario.PLAY_HEIGHT + 50, Escenario.WIDTH - 350, Escenario.PLAY_HEIGHT - 50);
		g.setPaint(Color.white);
		g.fillRect(0, Escenario.PLAY_HEIGHT-5, 350, 1);
		g.setPaint(Color.white);
		g.drawLine(350, Escenario.PLAY_HEIGHT-5, Escenario.WIDTH - 350, Escenario.PLAY_HEIGHT + 50);
		g.setPaint(Color.white);
		g.fillRect(Escenario.WIDTH - 350, Escenario.PLAY_HEIGHT + 50, Escenario.WIDTH - 350, 1);
		g.setPaint(Color.white);
		g.fillRect(0, 50, 220, 1);
		g.setPaint(Color.white);
		g.fillRect(220, 0, 1, 50);
		paintScore(g);
		paintEscudos(g);
		paintBombs(g);
	}
	
	public void paintEspacio() {
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		g.drawImage( background,0,0,Escenario.WIDTH,Escenario.HEIGHT,
		             0,backgroundY,Escenario.WIDTH,backgroundY+Escenario.HEIGHT,this);
		for (int i = 0; i < actores.size(); i++) {
			Actor m = (Actor)actores.get(i);
			m.paint(g);
		}
		player.paint(g);
		paintStatus(g);
		strategy.show();
	}
	
	public void paintGameOver() {
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,25));
		g.drawString("GAME OVER",Escenario.WIDTH/2-100,Escenario.HEIGHT/2);
		strategy.show();
	}

	public void paintWinner() {
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("WINNER!!", Escenario.WIDTH/2-140, Escenario.HEIGHT/2);
		strategy.show();
	}
	
	public SpriteCache getSpriteCache() {
		return spriteCache;
	}

	public soundCache getSoundCache() {
		return sounds;
	}
	
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {  }
	
	public void actionPerformed(ActionEvent e) {
		
	}

	public void jugar() {
		usedTime = 1000;
		iniciar();
		while (isVisible() && !juegoFin && (player.getScore() < 1000)) {
			long startTime = System.currentTimeMillis();
			backgroundY--;
			if (backgroundY < 0)
				backgroundY = backgroundTile.getHeight();
			cargarEspacio();
			checarColisiones();
			paintEspacio();
			usedTime = System.currentTimeMillis()-startTime;
			do {
  				Thread.yield();
			} while (System.currentTimeMillis()-startTime < 17);
		}
		if (player.getScore() >= 1000) {
			paintWinner();
			return;
		}
		paintGameOver();
	}
	
	public static void main(String[] args) {
		Menu menu = new Menu();
		while(!menu.done)
		{
			try
			{
			Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
				System.out.println("Problema al iniciar el Menu");
			}

		}
		menu.dispose();
		Espacio es = new Espacio();
		es.jugar();
	}
}