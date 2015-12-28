package proyectred;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TarjetaUsuario extends JPanel implements Serializable{
    private JLabel LNickname,LScore,LImageUser,LBackground,LDead,LMe;    
    private String Nickname;
    private ImageIcon ImageUser,Background,ImageMe;
    private JProgressBar PLife;
    private int Score,Life,Action;
    public int CREATE_CARD=1;
    public int UPDATE_CARD=2;
    public int DELETE_CARD=3;
    
    public TarjetaUsuario(String nickname,int score,int life,ImageIcon imageuser){
        //CODIGO DEL ACOMODO GRAFICO DE LA TARJETA DE USUARIO
        //------------------------------------------------------------
        Nickname    = nickname;
        Score       = score;
        Life        = life;
        ImageUser   = imageuser;
        Action      = CREATE_CARD;
        //------------------------------------------------------------        
        setLayout(null);
        //------------------------------------------------------------
        Image Imagen = ImageUser.getImage();   
        Image Imagen2 =  Imagen.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageUser = new ImageIcon(Imagen2);
        LImageUser = new JLabel(ImageUser);
        //------------------------------------------------------------
        LNickname = new JLabel(nickname);
        LNickname.setFont(new Font("Bauhaus 93", Font.ITALIC, 23));
        LNickname.setForeground(new Color(0xE53935));
        //------------------------------------------------------------
        LScore = new JLabel("Score: "+Score);
        LScore.setFont(new Font("Bauhaus 93", Font.PLAIN, 15));
        LScore.setForeground(new Color(0xE8EAF6));
        //------------------------------------------------------------
        PLife= new JProgressBar(0, 200);
        if(Life>80){
        PLife.setForeground(new Color(0x1A237E));
        PLife.setBackground(new Color(0x9FA8DA));        }
        else if(Life>40){
        PLife.setForeground(new Color(0xE64A19));
        PLife.setBackground(new Color(0xFFAB91));        }
        else{
        PLife.setForeground(new Color(0xB71C1C));
        PLife.setBackground(new Color(0xE57373));        }
        PLife.setValue(Life);
        //------------------------------------------------------------
        Image backg = new ImageIcon("card.png").getImage();   
        Image backg2 =  backg.getScaledInstance(240,100,Image.SCALE_SMOOTH);
        Background = new ImageIcon(backg2);
        LBackground = new JLabel(Background);
        //------------------------------------------------------------
        LDead = new JLabel("is dead!!");
        LDead.setForeground(new Color(0xE53935));
        //------------------------------------------------------------
        Image Me = new ImageIcon("shild.png").getImage();   
        Image Me2 =  Me.getScaledInstance(36,40,Image.SCALE_SMOOTH);
        LMe = new JLabel(new ImageIcon(Me2));
        LMe.setVisible(false);
        //------------------------------------------------------------
        LMe         .setBounds(200, 5, 36, 40);
        LImageUser  .setBounds(10, 10, 50,  50);
        LNickname   .setBounds(70, 10, 130, 25);
        LScore      .setBounds(70, 35, 140, 25);
        PLife       .setBounds(10, 75, 220, 12);
        LBackground .setBounds(0,  0,  240, 100);
        //------------------------------------------------------------
        add(LMe);
        add(LNickname);
        add(LScore);
        add(LImageUser);
        add(PLife);
        add(LBackground);
        //------------------------------------------------------------
        setBorder(LineBorder.createBlackLineBorder());
        setBackground(new Color(0x424242));
        setSize(240,100);
        setVisible(true);
        paintComponents(getGraphics());
        //------------------------------------------------------------
    }
    
    public void Me(){//AGREGA UN ICONO QUE IDENTIFICA LA TARJETA COMO PROPIA
        LMe.setVisible(true);
        paintComponents(getGraphics());
    }
    
    public void Other(){//ESTABLECE QUE LA TARJETA ES DE ALGUIEN MAS
        LMe.setVisible(false);
        paintComponents(getGraphics());
    }
    
    public void Dead(){//CAMBIA LA APARIENCIA DE LA TARJETA
        LNickname.setBounds(20, 20, 200, 30);
        LDead.setBounds(20, 60, 200, 30);
        LNickname.setFont(new Font("Bauhaus 93", Font.ITALIC, 34));
        LDead.setFont(new Font("Bauhaus 93", Font.ITALIC, 34));
        add(LNickname);
        add(LDead);
        add(LBackground);
        remove(LScore);
        remove(PLife);
        remove(LImageUser);
        paintComponents(getGraphics());
    }
    
    public String getNickname(){   return Nickname;    }
    
    public void setNickname(String nickname){   Nickname=nickname;    }
    
    public int getScore(){   return Score;    }
    
    public void setScore(int score){   Score=score;    }
    
    public int getLife(){   return Life;    }
    
    public void setLife(int life){
        Life=life;
        if(Life>80){
        PLife.setForeground(new Color(0x1A237E));
        PLife.setBackground(new Color(0x9FA8DA));        }
        else if(Life>40){
        PLife.setForeground(new Color(0xE64A19));
        PLife.setBackground(new Color(0xFFAB91));        }
        else{
        PLife.setForeground(new Color(0xB71C1C));
        PLife.setBackground(new Color(0xE57373));        }
    }
    
    public ImageIcon getImageIcon(){   return ImageUser;    }
    
    public void setImage(ImageIcon imageuser){   ImageUser=imageuser;    }
    
    public int getAction(){   return Action;    }
    
    public void setAction(int action){   Action=action;    }
    
}
