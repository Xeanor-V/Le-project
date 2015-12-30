import java.awt.image.ImageObserver;

public interface Escenario extends ImageObserver {	//interfaz para el escenario
	public static final int WIDTH = 800;	//ancho de la ventana
	public static final int HEIGHT = 600;	//altura de la ventana
	public static final int PLAY_HEIGHT = 500; 	//longitud de la altura del área de movimiento del jugador
	public static final int SPEED = 10;
	public SpriteCache getSpriteCache();	//obtener el sprite o la imagen
	public soundCache getSoundCache();
	public void addActor(Actor a);	//añadir un nuevo actor al escenario
	public Jugador getPlayer();	//obtener un el jugador ya sea Monstruo o Jugador
	public void winner();		//ganador
	public void gameOver();		//juego teminado
}