public class Bala extends Actor { //bala del jugador
	
	protected static final int BULLET_SPEED=10;
	
	public Bala(Escenario escena) {
		super(escena);
		setSpriteNames( new String[] {"misil.gif"});
	}
	
	public void act() {
		super.act();
		y-=BULLET_SPEED;
		if (y < 0)
			remove();
	}

	public void collision(Actor a) {
		if (a instanceof Monstruo) remove();
	}
}