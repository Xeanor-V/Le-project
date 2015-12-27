package proyectred;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Red {
    private Socket cliente;
    private ObjectInputStream oisNet;
    private ObjectOutputStream oosNet;
    private int puerto=5000;
    private LectorRed LR;
    private boolean ISC=false;
    
//******************************************************************************    
    public Red(LectorRed LR) {  
        this.LR=LR;
        Conexion();
    }

//******************************************************************************    
    public void Conexion() { 
        int i=0;
        //String host = JOptionPane.showInputDialog("Introdusca la conexion", "localhost");
        String host = "localhost";
        while(i<10){	
            System.out.println("Esperando por el servidor . . ."); i=10; ISC=true;
            try {
		cliente=new Socket(host, puerto);
            } catch ( IOException e) {	System.out.println("Fallo creacion Socket"); i=i+1; ISC=false;      }
        }
        
        if(ISC)
            System.out.println("Connectado al servidor.");
        
        try {
            oisNet = getOISNet(cliente.getInputStream());
            oosNet = getOOSNet(cliente.getOutputStream()); 
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al crear los fujos de objeto"+e);
        }
        
        (new Thread( new LecturaEntrante(LR, oisNet) )).start();
}  
//******************************************************************************    
    public boolean IsConnected() { 
        return ISC;
    }  
    
//******************************************************************************
    public void MandarARed(Object ObjetoX) {     
        try {
            oosNet.writeObject(ObjetoX);        
            oosNet.flush();
        } catch (IOException ex) { ex.printStackTrace(); }
    }
//******************************************************************************    
    ObjectOutputStream getOOSNet(OutputStream os) throws IOException {
		return new ObjectOutputStream(os);
    }
    
    ObjectInputStream getOISNet(InputStream is) throws IOException {
        	return new ObjectInputStream(is);
    }
    
//******************************************************************************
}