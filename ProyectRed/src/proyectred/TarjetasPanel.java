package proyectred;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TarjetasPanel extends JPanel implements Serializable, LectorRed{
    private Red RED;
    private JPanel TarjetasCaja;
    private JScrollPane ScrollTarjetas;
    private ArrayList<TarjetaUsuario> TarjetasUsuario;
    private String NICKNAME, SCORE, LIFE;
    private Image IMAGE=null;
    
    public TarjetasPanel(){
        //------------------------------------------------------------
        //ESTABLESE EL NICKNAME DEL USUARIO
        NICKNAME = JOptionPane.showInputDialog("Ingrese su Nickname", "Player");
        if(NICKNAME==null)      System.exit(0);
        //ESTABLESE EL PUNTAJE DEL USUARIO
        SCORE    = JOptionPane.showInputDialog("Ingrese el Score", "123");
        if(SCORE==null)         SCORE="0";
        //ESTABLESE LA VIDA DEL USUARIO
        LIFE     = JOptionPane.showInputDialog("Ingrese su vida", "135");
        if(SCORE==null)         SCORE="0";
        //ESTABLESE LA IMAGEN DEL USUARIO
        try{
            IMAGE = ImageIO.read(new File("user.png"));
            JFileChooser FileX = new JFileChooser();
            FileX.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
            FileX.setAcceptAllFileFilterUsed(false);
            FileX.setDialogTitle("Imagen de Usuario");
            FileX.showOpenDialog(this);
            File Archivo=FileX.getSelectedFile();
            IMAGE = ImageIO.read(Archivo);
        }catch(Exception e){System.out.println("sasasasas");;}
        //------------------------------------------------------------
        ElementosGraficos();
        setVisible(true);
        paintComponents(getGraphics());
        //------------------------------------------------------------
        //CONECTA CON EL SERVIDOR
        try{conectar();
        }catch(Exception e){
            System.err.print(e);
            JOptionPane.showMessageDialog(null,"Error al intentar conectarse al servidor, verifique su conexion","RED ERROR",2);
            System.exit(0);        }
        //------------------------------------------------------------
        //CREA LA TARJETA DEL USUARIO
        TarjetaUsuario TarjetaPropia = new TarjetaUsuario(NICKNAME,
        Integer.parseInt(SCORE),
        Integer.parseInt(LIFE),
        new ImageIcon(IMAGE));
        RED.MandarARed(TarjetaPropia);
        //------------------------------------------------------------
    }
    
    private void ElementosGraficos(){//GENERA LA FORMA EN LA QUE SE VE EL TARJETASPANEL
        //------------------------------------------------------------
        setLayout(null);
        TarjetasUsuario = new ArrayList<TarjetaUsuario>();
        TarjetasCaja = new JPanel();
        TarjetasCaja.setLayout(null); 
        TarjetasCaja.setPreferredSize(new Dimension(10,10));
        TarjetasCaja.setBackground(new Color(0x212121));
        ScrollTarjetas = new JScrollPane(TarjetasCaja);
        ScrollTarjetas.setBackground(new Color(0x212121));
        ScrollTarjetas.setBounds(0, 0, 280, 650);
        add(ScrollTarjetas);
        //------------------------------------------------------------
    }
    
    private void ActualizarCajaDeMensajes(){//ACTUALIZA LA INFROMACION DEL TARJETA PANEL DESPUES DE QUE SE MODIFICO
        int E=10;
        for(int i=1;i<=TarjetasUsuario.size();i++){
            //ACOMODO LAS TARJETAS DE MODO QUE NO SE ENSIMEN
            TarjetasUsuario.get(TarjetasUsuario.size()-i).setBounds(10, E,
                    TarjetasUsuario.get(TarjetasUsuario.size()-i).getWidth(),
                    TarjetasUsuario.get(TarjetasUsuario.size()-i).getHeight());
            //COMPRUEVA SI UNA TARJETA DEBE DE AGREGARSE VISUALMENTE O NO
            if(TarjetasUsuario.get(TarjetasUsuario.size()-i).getScore()>0){
                TarjetasCaja.add(TarjetasUsuario.get(TarjetasUsuario.size()-i),0);
                E=E+110;}
            //AJUSTA LAS TARJETASUSUARIO
            TarjetasCaja.setPreferredSize(new Dimension(10,E));
            ScrollTarjetas.setAutoscrolls(true); 
            }
        
        System.out.println("ACUTUALIZANDO CAJA DE TARJETAS");
        paintComponents(getGraphics());
    }

    public void LeerRed(Object ObjectX){//ACCIONES QUE REALIZA EL TARJETASPANEL AL MOMENTO DE RECIVIR INFO DEL SERVIDOR
        TarjetaUsuario TARJETA = (TarjetaUsuario)ObjectX;
        System.out.println("LEYENDO TARJETA");
        
        //IDENTIFICA EL TARJETA DEL USUSARIO Y LA RESALTA
        if(TARJETA.getNickname().equals(NICKNAME))
            TARJETA.Me();
        
        //ACCIONES A REALIZAR SI LA INFORMACION ES DE UN USUARIO NUEVO
        if(TARJETA.getAction()==TARJETA.CREATE_CARD){
            if(TARJETA.getNickname().equals(NICKNAME)){
                TarjetasUsuario.add(TarjetasUsuario.size(),TARJETA);}
            else
                TarjetasUsuario.add(0,TARJETA);
        System.out.println("AÑADIENDO TARJETA");}
        
        //ACCIONES A REALIZAR SI LA INFORMACION ES DE UN USUARIO EXISTENTE
        if(TARJETA.getAction()==TARJETA.UPDATE_CARD){
            for(int i=0;i<TarjetasUsuario.size();i++)
                if(TarjetasUsuario.get(i).getNickname().equals(TARJETA.getNickname())){
                    TarjetasUsuario.remove(i);
                    TarjetasUsuario.add(i,TARJETA);                }
        System.out.println("MODIFICANDO TARJETA");}
        
        //ACCIONES A REALIZAR SI UN USUARIO SE VA A QUITAR
        if(TARJETA.getAction()==TARJETA.DELETE_CARD){
            for(int i=0;i<TarjetasUsuario.size();i++)
                if(TarjetasUsuario.get(i).getNickname().equals(TARJETA.getNickname())){
                    TarjetasUsuario.get(i).Dead();
                    TarjetasUsuario.remove(i);}
        System.out.println("ELIMINANDO TARJETA");}
        
        ActualizarCajaDeMensajes();
    }
    
    public void AgregarTarjeta(int Score,int Life){//METODO PUBLICO PARA AGREGAR INFORMACION AL TARJETAS PANEL
        System.out.println("AGREGANDO TARJETA");
        TarjetaUsuario TarjetaPropia = new TarjetaUsuario(NICKNAME,Score,Life,new ImageIcon(IMAGE));
        //CHECA LO QUE SE DEBE DE HACER CON LA NUEVA INFORMACION
        if(TarjetaPropia.getLife()==0)
            TarjetaPropia.setAction(TarjetaPropia.DELETE_CARD);
        else
            TarjetaPropia.setAction(TarjetaPropia.UPDATE_CARD);
        //INFORMA AL SERVIDOR DE LA NUEVA INFORMACION
        RED.MandarARed(TarjetaPropia);
    }
    
    private void conectar(){//CONECTA EL TARJETASPANEL AL SERVIDOR
     	System.out.println("INTENTANDO CONECTAR");
        RED =new Red(this);
    }    
}