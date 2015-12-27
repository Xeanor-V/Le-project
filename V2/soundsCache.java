import java.io.*;
import javax.sound.sampled.*;


public class soundsCache
{
	File archivos[];
	public soundsCache()
	{
		archivos = new File [2];
		archivos[0] = new File("Sonidos/96230_laserg.wav");
		archivos[1] = new File("Sonidos/96230_laserg.wav");
	}

public synchronized void playSound(int index)
{
    try
    {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(archivos[index]));
        clip.start();
        clip.start();
        while (!clip.isRunning())
        Thread.sleep(10);
        while (clip.isRunning())
        Thread.sleep(10);
        clip.close();
    }
    catch (Exception exc)
    {
        exc.printStackTrace(System.out);
    }
    }
   /* public static void main(String[] args) throws Exception
    {
        soundsCache sound = new soundsCache();
        while(true)
        {
        sound.playSound(0);
        Thread.sleep(1000);
        }
    }*/
}