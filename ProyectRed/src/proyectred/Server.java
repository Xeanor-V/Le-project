package proyectred;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    ArrayList<ObjectOutputStream> ObjectOutStream = new ArrayList<ObjectOutputStream>();
    ArrayList<TarjetaUsuario> TarjetasJuego = new ArrayList<TarjetaUsuario>();
//******************************************************************************
    public class ManagerClient implements Runnable {
        ObjectInputStream LECTOR;
        Socket SOCKET;
//------------------------------------------------------------------------------
        public ManagerClient(Socket ClientSocket){
            try {
                SOCKET = ClientSocket;
                LECTOR = new ObjectInputStream(SOCKET.getInputStream());      
            } catch(Exception ex) {
                System.out.println("---- Excepcion Lector de Servidor ----" + ex);
                ex.printStackTrace();
            }
          }
//------------------------------------------------------------------------------
        public void run(){
            TarjetaUsuario TarjetaX;
            try {
                while (true) {
                    TarjetaX = (TarjetaUsuario) LECTOR.readObject();
                    if(TarjetaX.getAction()==TarjetaX.CREATE_CARD)
                        AgregarTarjeta(TarjetaX);
                    else if(TarjetaX.getAction()==TarjetaX.UPDATE_CARD)
                        ModificarTarjeta(TarjetaX);
                    else if(TarjetaX.getAction()==TarjetaX.DELETE_CARD)
                        EliminarTarjeta(TarjetaX);
                    }
            } catch(Exception ex) {ex.printStackTrace();}
       }
//------------------------------------------------------------------------------
    } // Cierre

//******************************************************************************    
    private void go(){
        try {
            ServerSocket ServerSock = new ServerSocket(5000);
            while(true){
                Socket ClientSocket = ServerSock.accept();
                ObjectOutputStream Writer = new ObjectOutputStream(ClientSocket.getOutputStream());        
                ObjectOutStream.add(Writer);
               
                for(int i=0;i<TarjetasJuego.size();i++)
                    ComunicarA(TarjetasJuego.get(i),Writer);
               
                Thread t = new Thread(new ManagerClient(ClientSocket));
                t.start();

                System.out.println("Coneccion Establecida");                     }  
        } catch(Exception ex) { ex.printStackTrace(); }
    }
//******************************************************************************
    private void Comunicar(Object ObjetoX) {
        for(int i=0;i<ObjectOutStream.size();i++){
            try {
                ObjectOutStream.get(i).writeObject(ObjetoX);
                ObjectOutStream.get(i).flush();
            } catch(Exception ex) { ex.printStackTrace(); }
        }
    }
//******************************************************************************
    private void ComunicarA(Object ObjetoX, ObjectOutputStream ObjectOutStreamX) {
            try {
                ObjectOutputStream Writer = ObjectOutStreamX;
                Writer.writeObject(ObjetoX);
                Writer.flush();
            } catch(Exception ex) { ex.printStackTrace(); }
    }
//******************************************************************************
    private void AgregarTarjeta(TarjetaUsuario Tarjeta) {
        boolean E = false;
        for(int i=0;i<TarjetasJuego.size();i++)
            if(TarjetasJuego.get(i).getNickname().equals(Tarjeta.getNickname()))
                E=true;
        if(!E){
            TarjetasJuego.add(Tarjeta);
            Comunicar(Tarjeta);         }
    }
//******************************************************************************
    private void ModificarTarjeta(TarjetaUsuario Tarjeta) {
        for(int i=0;i<TarjetasJuego.size();i++)
            if(TarjetasJuego.get(i).getNickname().equals(Tarjeta.getNickname())){
                TarjetasJuego.remove(i);
                TarjetasJuego.add(i,Tarjeta);
                Comunicar(Tarjeta);
                TarjetasJuego.get(i).setAction(Tarjeta.CREATE_CARD);
                }
    }
//******************************************************************************
    private void EliminarTarjeta(TarjetaUsuario Tarjeta) {
        for(int i=0;i<TarjetasJuego.size();i++)
            if(TarjetasJuego.get(i).getNickname().equals(Tarjeta.getNickname())){
                TarjetasJuego.remove(i);
                Comunicar(Tarjeta);             }
    }
//******************************************************************************   
    public static void main (String[] args) {//M A I N
        new Server().go();
    }
//******************************************************************************
}
