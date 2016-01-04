import java.awt.event.KeyEvent;
import java.io.*;
import javax.sound.sampled.*;

public class Jugador extends Actor {

	public static final int MAX_SHIELDS = 200; //total de vida
	public static final int MAX_SCORE = 1000; //puntaje máximo
	public static final int MAX_BOMBS = 5; //maximo de bombas
	protected static final int PLAYER_SPEED = 4; //velocidad del jugador
	private boolean up, down, left, right; //movimiento por teclado
	protected int vx;	//velocidad en X
	protected int vy;	//velocidad en Y
	private int reserva_bombs;	//bombas disponibles
	private int score;	//puntaje
	private int shields;	//escudos
	private soundsCache sound;
	
	public Jugador(Escenario escena) {
		super(escena);
		setSpriteNames( new String[] {"spaceShipIcon.png"});
		reserva_bombs = MAX_BOMBS;//bombas disponibles
		shields = MAX_SHIELDS;
		score = 0; //puntaje del jugador
		sound = new soundsCache();
	}
	
	public void act() {
		super.act();
		x+=vx;
		y+=vy;
		if ( x < 0 )
			x = 0;
		if ( x > Escenario.WIDTH - getWidth())
			x = Escenario.WIDTH - getWidth();
		if ( y < 0 )
			y = 0;
		if ( y > Escenario.PLAY_HEIGHT - getHeight())
			y = Escenario.PLAY_HEIGHT - getHeight();
	}

	public int getVx() { //velocidad en X
		return vx; 
	}

 	public int getVy() { //obtener velocidad en Y
 		return vy; 
 	}

 	public void setVx(int i) { //modificar velocidad en X
		vx = i; 
	}

	public void setVy(int i) { // modificar velocidad en Y
  		vy = i; 
  	}

	protected void updateSpeed() { //cargar velocidad de acuerdo al movimiento
		vx=0;
		vy=0;
		if (down) vy = PLAYER_SPEED;
		if (up) vy = -PLAYER_SPEED;
		if (left) vx = -PLAYER_SPEED;
		if (right) vx = PLAYER_SPEED;
	}
  
	public void keyReleased(KeyEvent e) { //modifica el movimiento de tecla acabada de presionar
		switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN : down = false;break;
			case KeyEvent.VK_UP : up = false; break;
			case KeyEvent.VK_LEFT : left = false; break; 
			case KeyEvent.VK_RIGHT : right = false;break;
			case KeyEvent.VK_SPACE : fire(); break; //dsiparo
		}
		updateSpeed();
	}
  
	public void keyPressed(KeyEvent e) { //tecla que mueve el jugador y libera balas o bombas
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP : up = true; break;
			case KeyEvent.VK_LEFT : left = true; break;
			case KeyEvent.VK_RIGHT : right = true; break;
			case KeyEvent.VK_DOWN : down = true;break;
			case KeyEvent.VK_B : fireCluster(); break; //bombas disponibles
		}
		updateSpeed();
	}
  
	public void fire() { //bala
		Bala b = new Bala(escena); //objeto de tipo Bala
		daleplay();
		b.setX(x); //movimiento en X fijo
		b.setY(y - b.getHeight()); //modifica la posición de la bala en el eje Y
		escena.addActor(b); //lo añade como un nuevo actor para los gráficos o al escenario
	}
  
	public void fireCluster() {
		if (reserva_bombs == 0)
			return;

		reserva_bombs--;
		escena.addActor( new Bomba(escena, Bomba.UP_LEFT, x,y));
		escena.addActor( new Bomba(escena, Bomba.UP,x,y));
		escena.addActor( new Bomba(escena, Bomba.UP_RIGHT,x,y));
		escena.addActor( new Bomba(escena, Bomba.LEFT,x,y));
		escena.addActor( new Bomba(escena, Bomba.RIGHT,x,y));
		escena.addActor( new Bomba(escena, Bomba.DOWN_LEFT,x,y));
		escena.addActor( new Bomba(escena, Bomba.DOWN,x,y));
		escena.addActor( new Bomba(escena, Bomba.DOWN_RIGHT,x,y));
	}
  
	public int getScore() {
		return score;
	}

	public void setScore(int i) { 
		score = i;	
	}

	public void addScore(int i) { 
		score += i;  
	}

	public int getShields() {	
		return shields;	
	}

	public void setShields(int i) {	
		shields = i;	
	}

	public void addShields(int i) {
		shields += i;
		if (shields > MAX_SHIELDS) shields = MAX_SHIELDS;
	}
	
	public void collision(Actor a) {
		if (a instanceof Monstruo ) {
			a.remove();
			addShields(-40);
		}

		if (a instanceof Laser ) {
			a.remove();
			addShields(-10);
		}

		if (a instanceof Asteroide )
			escena.gameOver();

		if (getShields() == 0)
			escena.gameOver();
	}	

	public int getClusterBombs() { 
		return reserva_bombs;
	}

	public void setClusterBombs(int i) { 
		reserva_bombs = i; 
	}

	public void daleplay()
    {        
       try
       {
		    Clip clip=AudioSystem.getClip();
		    clip.open(AudioSystem.getAudioInputStream( getClass().getResourceAsStream( "Sonidos/96230_laserg.wav") ) );
		    clip.start();
		    //while (!clip.isRunning())
	        	Thread.sleep(5);
	        //while (clip.isRunning())
	        	Thread.sleep(5);
	        if (getShields() == 0)
				clip.close();
	    }catch(Exception ex){
	    	System.err.println( ex.getMessage() );
        }
    }
}