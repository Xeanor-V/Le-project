import java.io.*;
import javax.sound.sampled.*;


public class soundsCache
{
	File archivos[];
    int index;
	public soundsCache()
	{
		archivos = new File [2];
		archivos[0] = new File("Sonidos/96230_laserg.wav");
		archivos[1] = new File("Sonidos/96230_laserg.wav");
	}

public synchronized void playSound(int index)
{
    this.index=index;
    Thread thread = new Thread(new player());
    thread.start();
}


private class player implements Runnable
{
    public void run()
    {
        try
        {
            System.out.println("Here");
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
}
}