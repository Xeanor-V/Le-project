public class Monstruo extends Actor implements Runnable {

	protected static final double FIRING_FREQUENCY = 0.01;	//
	protected int vx, k = 10, time = 10;
	protected final int duration = 1000;
	protected Thread e;

	public Monstruo(Escenario escena) {
		super(escena);
		setSpriteNames( new String[] {"SNaveEnemiga.png"} );
		setFrameSpeed(35);
		e = new Thread(this);
		e.start();
		e.suspend();
	}
	
	public void act() {
		super.act();
		x+=vx;
		if (x < 0 || x > Escenario.WIDTH)
		  vx = -vx;
		if (Math.random() < FIRING_FREQUENCY)
		  fire();
	}

	public int getVx() { return vx; }
	public void setVx(int i) { vx = i;	}
	
	public void collision(Actor a) {
		if (a instanceof Bala || a instanceof Bomba) { //si es Bala o es una bomba
			remove();	//remover
			//exploit();	//explotar
			generar();	//generar otro
			escena.getPlayer().addScore(20); //agregar puntaje al jugador
		}
		if ( a instanceof Jugador) {
			remove();	//remover
			exploit();	//explotar
			generar();	//generar otro
		}
		e.suspend();
	}

	public void exploit() {	//explotar el enemigo
		e.resume();
	}

	public void run() {	
		while(time-- > 0) {
			setSpriteNames( new String[] {"Explosion.gif"} );
			try{
				Thread.sleep(duration);
			} catch(Exception ex) {  }
		}
	}
	
	public void generar() {	//generar actor o monstruo
		Monstruo m = new Monstruo(escena);
		m.setX( (int)(Math.random()*Escenario.WIDTH) );
		m.setY( (int)(Math.random()*Escenario.PLAY_HEIGHT/2) );
		m.setVx( (int)(Math.random()*20-10) );
		escena.addActor(m);
	}
	
	public void fire() {
		Laser m = new Laser(escena);
		m.setX(x+getWidth()/2);
		m.setY(y + getHeight());
		escena.addActor(m);
	}
}