package proyectred;
import java.io.*;

public class LecturaEntrante implements Runnable {
    private LectorRed LR;
    private ObjectInputStream oisNet;
//******************************************************************************
    public LecturaEntrante(LectorRed LR, ObjectInputStream oisNet){ 
	this.LR=LR;
	this.oisNet=oisNet;
    }

//******************************************************************************    
    public void run(){
	Object obj=null;
	int j,k=0;
        System.out.println("run");
   	
        for(  ;  ;  ){
            j=0;
            try {obj=oisNet.readObject();
            } catch (IOException e) { System.out.println("IO excepcion"+e); j=1;
            } catch (IndexOutOfBoundsException e) { System.out.println("IndexException excepcion"+e); j=1;
            } catch (ClassNotFoundException ex) { System.out.println("Clase no encontrada"+ex); j=1; } 
    		
            if (j==0) { LR.LeerRed(obj); }
        }
    }
//******************************************************************************
}
