import javax.swing.*;

public class Gallery {

    public void makeScreen(){
        JFrame Screen = new JFrame ("Navegador de imagens com galeria");

        

        Screen.setVisible(true);
        Screen.setBounds(0,0,800,500);
        Screen.setLocationRelativeTo(null);
        Screen.setLayout(new FlowLayout());

    }
}