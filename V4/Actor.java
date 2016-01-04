import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Actor {
	
	protected boolean paraRemover;
	protected SpriteCache spriteCache;	//imagen del actor
	protected int marco_en_corriente; //cuadro actual
	protected String[] nomb_sprite;
	protected int width, height;
	protected int velMarco;
	protected Escenario escena; //escenario
	protected int x, y;
	protected int t;
	
	public Actor(Escenario escena) {
		this.escena = escena;
		spriteCache = escena.getSpriteCache();
		marco_en_corriente = 0; //inicializa marco actual
		velMarco = 1;
		t=0;
	}

	public void act() {
		t++;
		if (t % velMarco == 0) {	//
			t=0;
  			marco_en_corriente = (marco_en_corriente + 1) % nomb_sprite.length;
		}
	}
	
	public void collision(Actor a) {  }

	public void paint(Graphics2D g){	//pintar la imagen
		g.drawImage( spriteCache.getSprite(nomb_sprite[marco_en_corriente]), x, y, escena );
	}
	
	public int getX()  { 
		System.out.println(x);
		return x; }
	public int getY() {	
		System.out.println(y);
		return y; }
	public int getWidth() {	return width; }
	public int getHeight() { return height; }
	public int getFrameSpeed() { return velMarco; }
	public void setHeight(int i) { height = i; }
	public void setWidth(int i) { width = i; }
	public void getFrameSpeed(int i) { velMarco = i; }

	public Rectangle getBounds() {
		return new Rectangle( x, y, width, height );
	}
	
	public void setX(int i) { x = i; }
	public void setY(int i) { y = i; }
	public void setFrameSpeed(int i) { velMarco = i; }
	
	public void setSpriteNames(String[] names) { 
		nomb_sprite = names;
		height = 0;
		width = 0;
		for ( int i = 0; i < names.length; i++ ) {
	  		BufferedImage image = spriteCache.getSprite(nomb_sprite[i]);
		  	height = Math.max(height, image.getHeight());
			width = Math.max(width, image.getWidth());
		}
	}

	public void remove() {	//remover la imagen, marcar la bandera paraRemover a true
		paraRemover = true;
	}
	
	public boolean isForRemove() {	//ha sido removida return true or false
		return paraRemover;
	}
}