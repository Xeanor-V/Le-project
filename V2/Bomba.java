public class Bomba extends Actor {

	public static final int UP_LEFT = 0; //Diagonal superior izquierda
	public static final int UP = 1;	// arriba
	public static final int UP_RIGHT = 2;	//Diagonal superior derecha
	public static final int LEFT = 3;	//izquierda
	public static final int RIGHT = 4;	//Derecha
	public static final int DOWN_LEFT = 5;	//Diagonal inferior izquierda
	public static final int DOWN = 6;	//Abajo
	public static final int DOWN_RIGHT = 7;	//Diagonal inferior derecha
	protected static final int BOMB_SPEED = 5; 	//velocidad bomba
	protected int vx;
	protected int vy;
	
	public Bomba(Escenario esc, int mov, int x, int y) {
		super(esc);
		this.x = x;
		this.y = y;
		String sprite ="";
		switch (mov) {
			case UP_LEFT : vx = -BOMB_SPEED; vy = -BOMB_SPEED; sprite="bombaUpLeft.gif";break;
			case UP : vx = 0; vy = -BOMB_SPEED; sprite="bombaUp.gif";break;
			case UP_RIGHT: vx = BOMB_SPEED; vy = -BOMB_SPEED; sprite="bombaUpRight.gif";break;
			case LEFT : vx = -BOMB_SPEED; vy = 0; sprite = "bombaLeft.gif";break;
			case RIGHT : vx = BOMB_SPEED; vy = 0; sprite = "bombaRight.gif";break;
			case DOWN_LEFT : vx = -BOMB_SPEED; vy = BOMB_SPEED; sprite="bombaDownLeft.gif";break;
			case DOWN : vx = 0; vy = BOMB_SPEED; sprite = "bombaDown.gif";break;
			case DOWN_RIGHT : vx = BOMB_SPEED; vy = BOMB_SPEED; sprite = "bombaDownRight.gif";break;
		}
		setSpriteNames( new String[] {sprite});
	}
	
	public void act() {
		super.act();
		y+=vy;
		x+=vx;
		if (y < 0 || y > Escenario.HEIGHT || x < 0 || x > Escenario.WIDTH) //si se sale la bomba del área de juego
			remove(); //remover la bomba
	}
}