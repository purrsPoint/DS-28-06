import javax.swing.*;
import java.awt.*;

public class Gallery{

    private JButton chooser_button;

    public void makeScreen(){
        JFrame Screen = new JFrame ("Navegador de imagens com galeria");


        Screen.setVisible(true);
        Screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Screen.setBounds(0,0,800,500);
        Screen.setLocationRelativeTo(null);

        //Construindo botão
        chooser_button.setText("Selecionar foto");
        chooser_button.setBounds(0,0,50,50);


        //Adicionando componentes 
        Screen.getContentPane().add(chooser_button);

    }
}