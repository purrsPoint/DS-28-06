import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class principal {
    public static void main (String[] args) {
        // Cria a janela (JFrame)
        JFrame frame = new JFrame("Minha Página Swing");
        frame.setBounds(0,0,500, 500); // largura x altura
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5,1));

        // Cria componentes
   
        JButton button = new JButton("Clique aqui");
        button.addActionListener(e -> {});

        // Adiciona os componentes ao painel

        frame.add(button);

        // Torna a janela visível
        frame.setVisible(true);
    }

    public void OpenFiles(){
        JFileChooser Open_ie = new JFileChooser();
        
        //titulo da janela
        Open_ie.setDialogTitle("Seleciona imagem");

        //defina o tipo de arquivo selecionado
        Open_ie.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //filtra
        FileNameExtensionFilter filter = new FileNameExtensionFilter( "Imagens",".jpg", ".png" , ".jpeg");

        int returnValue = Open_ie.showOpenDialog(null);

        if(returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = Open_ie.getSelectedFile();

            JOptionPane.showMessageDialog(null,"Arquivo selecionado"+ selectedFile.getAbsolutePath());
            
        }else{
            JOptionPane.showMessageDialog(null,"nenhma imagem selecionada");
        }
    }
}
