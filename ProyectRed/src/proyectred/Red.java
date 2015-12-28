package proyectred;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Red {
    private Socket Cliente;
    private ObjectInputStream InputStream;
    private ObjectOutputStream OutputStream;
    private int Puerto=5000;
    private LectorRed LR;
    private boolean ISC=false;
    
//******************************************************************************    
    public Red(LectorRed LR) {  
        this.LR=LR;
        Conexion();
    }
//******************************************************************************    
    public void Conexion() {//CONECTA AL USUARIO CON EL SERVIDOR 
        int i=0;
        while(i<10){//Intento de conexion con el servidor 	
            System.out.println("1.-ESPERANDO POR EL SERVIDOR . . ."); i=10; ISC=true;
            try {Cliente=new Socket("localhost", Puerto);
            } catch ( IOException e) {	System.out.println("FALLO LA CREACION DEL SOCKET"); i=i+1; ISC=false;      }
        }
        //----------------------------------------------------------------------
        if(ISC)     System.out.println("2.-CONECTANDO AL SERVIDOR.");
        //----------------------------------------------------------------------
        try {//Creacion de los fujos 
            InputStream = getOISNet(Cliente.getInputStream());
            OutputStream = getOOSNet(Cliente.getOutputStream()); 
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR AL CREAR LOS FLUJOS"+e);
        }
        //----------------------------------------------------------------------
        (new Thread( new LecturaEntrante(LR, InputStream) )).start();
}  
//******************************************************************************    
    public void MandarARed(Object ObjetoX) {//MANDA INFORMACION AL SERVIDOR     
        try {
            OutputStream.writeObject(ObjetoX);        
            OutputStream.flush();
        } catch (IOException ex) { ex.printStackTrace(); }
    }
//******************************************************************************    
    ObjectOutputStream getOOSNet(OutputStream os) throws IOException {  return new ObjectOutputStream(os);    }
    ObjectInputStream getOISNet(InputStream is) throws IOException {    return new ObjectInputStream(is);    }
//******************************************************************************
    public boolean IsConnected() {      return ISC;     }  
//******************************************************************************
}