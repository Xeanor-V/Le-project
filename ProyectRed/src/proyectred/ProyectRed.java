package proyectred;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//ESTA ES UNA PRUEVA DE COMO USAR EL TARJETASPANEL
public class ProyectRed extends JPanel implements ActionListener{
    private JTextField TFScore, TFLife;
    private JButton Enviar;
    TarjetasPanel TarjetasCaja;
    
    public ProyectRed(){
        setLayout(null);
        Enviar      = new JButton("Enviar");
        TFScore     = new JTextField("1233");
        TFLife      = new JTextField("21");
        TarjetasCaja = new TarjetasPanel();//SE CREA EL TARJETAS PANEL
        
        TarjetasCaja.setBounds  (510, 0, 280, 650);//SE ESTABLECE SU POSICION 
        TFScore.setBounds       (10, 50,  200, 50);
        TFLife.setBounds        (10, 100, 200, 50);
        Enviar.setBounds        (10, 150, 200, 50);
        
        add(TFScore);
        add(TFLife);
        add(Enviar);
        add(TarjetasCaja);
        
        Enviar.addActionListener(this);
        setVisible(true);
        paintComponents(getGraphics()); 
    }        
    public void actionPerformed(ActionEvent e){
        JButton Boton = (JButton)e.getSource();
        if(Boton==Enviar){
            //SE LLAMA AL METODO AGREGARTARJETA PARA MANDAR UN PUNTAJE Y UN VALOR DE VIDA
            //ESTO CREARA UNA TARJETA PARA EL USUSARIO
            TarjetasCaja.AgregarTarjeta(Integer.parseInt(TFScore.getText()),Integer.parseInt(TFLife.getText()));
        }
    }   
    public static void main(String[] args) {
        JFrame FChat = new JFrame("Prueba Red");
        FChat.setDefaultCloseOperation(FChat.EXIT_ON_CLOSE);
        FChat.setSize(800,680);
        FChat.add(new ProyectRed());
        FChat.setVisible(true);
        FChat.setResizable(false);
    }   
}
