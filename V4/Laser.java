public class Laser extends Actor { //laser del enemigo
	
	protected static final int LASER_SPEED=3;
	
	public Laser(Escenario escena) {
		super(escena);
		setSpriteNames( new String[] {"disparo0.gif","disparo1.gif","disparo2.gif"});
		setFrameSpeed(10);
	}
	
	public void act() {
		super.act();
		y+=LASER_SPEED;
		if (y > Escenario.PLAY_HEIGHT)
		  remove();
	}
}