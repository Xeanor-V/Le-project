import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
   
public class soundCache extends ResourceCache {
	public Object loadResource(String url) {
		try{
			URL u = new URL(url);
			return Applet.newAudioClip(u);
		} catch (Exception e) {
			System.out.println("No se pudo cargar el sonido de: "+e);
			return null;
		}
	}
	public AudioClip getAudioClip(String name) {
		return (AudioClip)getResource(name);
	}
	public void playSound(final String name) {
		getAudioClip(name).play();
	}   
}