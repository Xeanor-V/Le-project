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
        NICKNAME = JOptionPane.showInputDialog("Ingrese su Nickname", "Player");
        if(NICKNAME==null)      System.exit(0);
        SCORE    = JOptionPane.showInputDialog("Ingrese el Score", "123");
        if(SCORE==null)         SCORE="0";
        LIFE     = JOptionPane.showInputDialog("Ingrese su vida", "135");
        if(SCORE==null)         SCORE="0";
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
        try{conectar();
        }catch(Exception e){
            System.err.print(e);
            JOptionPane.showMessageDialog(null,"Error al intentar conectarse al servidor, verifique su conexion","RED ERROR",2);
            System.exit(0);        }
        //------------------------------------------------------------
        TarjetaUsuario TarjetaPropia = new TarjetaUsuario(NICKNAME,
        Integer.parseInt(SCORE),
        Integer.parseInt(LIFE),
        new ImageIcon(IMAGE));
        RED.MandarARed(TarjetaPropia);
        //------------------------------------------------------------
    }
    
    private void ElementosGraficos(){
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
    
    private void ActualizarCajaDeMensajes(){
        int E=10;
        for(int i=1;i<=TarjetasUsuario.size();i++){
            TarjetasUsuario.get(TarjetasUsuario.size()-i).setBounds(10, E,
                    TarjetasUsuario.get(TarjetasUsuario.size()-i).getWidth(),
                    TarjetasUsuario.get(TarjetasUsuario.size()-i).getHeight());
            if(TarjetasUsuario.get(TarjetasUsuario.size()-i).getScore()>0){
                TarjetasCaja.add(TarjetasUsuario.get(TarjetasUsuario.size()-i),0);
                E=E+110;}
            TarjetasCaja.setPreferredSize(new Dimension(10,E));
            ScrollTarjetas.setAutoscrolls(true); 
            }
        System.out.println("ACUTUALIZANDO CAJA DE TARJETAS");
        paintComponents(getGraphics());
    }

    public void LeerRed(Object ObjectX) {
        TarjetaUsuario TARJETA = (TarjetaUsuario)ObjectX;
        System.out.println("LEYENDO TARJETA");
        if(TARJETA.getNickname().equals(NICKNAME))
            TARJETA.Me();
        if(TARJETA.getAction()==TARJETA.CREATE_CARD)
            if(TARJETA.getNickname().equals(NICKNAME)){
                TarjetasUsuario.add(TarjetasUsuario.size(),TARJETA);}
            else
                TarjetasUsuario.add(0,TARJETA);
        if(TARJETA.getAction()==TARJETA.UPDATE_CARD)
            for(int i=0;i<TarjetasUsuario.size();i++)
                if(TarjetasUsuario.get(i).getNickname().equals(TARJETA.getNickname())){
                    TarjetasUsuario.remove(i);
                    TarjetasUsuario.add(i,TARJETA);                }
        if(TARJETA.getAction()==TARJETA.DELETE_CARD)
            for(int i=0;i<TarjetasUsuario.size();i++)
                if(TarjetasUsuario.get(i).getNickname().equals(TARJETA.getNickname())){
                    TarjetasUsuario.get(i).Dead();
                    TarjetasUsuario.remove(i);}
        ActualizarCajaDeMensajes();
    }
    
    public void AgregarTarjeta(int Score,int Life){
        System.out.println("AGREGANDO TARJETA");
        TarjetaUsuario TarjetaPropia = new TarjetaUsuario(NICKNAME,Score,Life,new ImageIcon(IMAGE));
        if(TarjetaPropia.getLife()==0)
            TarjetaPropia.setAction(TarjetaPropia.DELETE_CARD);
        else
            TarjetaPropia.setAction(TarjetaPropia.UPDATE_CARD);
        RED.MandarARed(TarjetaPropia);
    }
    
    private void conectar(){
     	System.out.println("Intentando Conectar");
        RED =new Red(this);
    }    
}
