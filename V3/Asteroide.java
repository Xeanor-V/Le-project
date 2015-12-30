
public class Asteroide extends Actor {

	protected static final double AST_FREQUENCY = 0.001;
	protected static final int AST_SPEED=3;
	protected int vy, k;
	
	public Asteroide(Escenario escena) {
		super(escena);
		setSpriteNames( new String[] {"asteroide.gif"});
		setFrameSpeed(10);
	}

	public int getVy() { return vy; }
	public void setVy(int i) { vy = i;	}

	public void act() {
		super.act();
		y+=AST_SPEED;
		if (Math.random() < AST_FREQUENCY)
			generar();
		if (y > Escenario.PLAY_HEIGHT)
		  remove();
	}

	public void generar() {
		Asteroide a = new Asteroide(escena);
		a.setX( (int)(Math.random()*Escenario.WIDTH) );
		a.setY( 0 );
		a.setVy( (int)(Math.random()*20-10) );
		escena.addActor(a);
	}

	public void collision(Actor a) {
		if (a instanceof Bala) remove();
	}
}