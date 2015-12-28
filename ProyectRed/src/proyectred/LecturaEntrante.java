package proyectred;
import java.io.*;

public class LecturaEntrante implements Runnable {
    private LectorRed LR;
    private ObjectInputStream InputStream;
//******************************************************************************
    public LecturaEntrante(LectorRed LR, ObjectInputStream InputStream){ 
	this.LR=LR;
	this.InputStream=InputStream;             }
//******************************************************************************    
    public void run(){
	Object obj=null;
	int j;
        System.out.println("RUN");
        while(true){    j=0;
            try {obj=InputStream.readObject();
            } catch (IOException e) { System.out.println("IOEXCEPTION:"+e); j=1;
            } catch (IndexOutOfBoundsException e) { System.out.println("INDEXEXCEPTION:"+e); j=1;
            } catch (ClassNotFoundException e) { System.out.println("CLASE NO ENCONTRADA:"+e); j=1; } 
    	    if (j==0) { LR.LeerRed(obj); }
        }
    }
//******************************************************************************
}