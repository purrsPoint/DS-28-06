package com.github.pursspoint;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

class Main extends JFrame{    

    private JFileChooser selec_image;
    private JButton b_selec_image;


    public Main() {
        b_selec_image = new JButton();
        b_selec_image.setSize(50, 50);
        b_selec_image.setText("Selecionar foto");

        selec_image = new JFileChooser(); 
        selec_image.setCurrentDirectory(new File("."));
        // Cria a janela (JFrame)
        this.setTitle("navegador de imagens");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0,0,400, 300); // largura x altura
        this.setLayout(new GridLayout(5,1));

        //adiciona o botao
        this.getContentPane().add(b_selec_image);
        //adiciona metodo ao botao
        b_selec_image.addActionListener(e->{OpenFile();});
      


    }

    private void OpenFile(){
        selec_image.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.getName().endsWith(".jpg")) {
                     return true;
                }
                if (f.getName().endsWith(".png")) {
                 return true;   
                }
                if (f.getName().endsWith(".jpeg")) {
                    return true;
                }
                if (f.getName().endsWith(".gif")) {
                    return true;
                }
               
               return false;
            }

            @Override
            public String getDescription() {
                return "Arquivos .jpg .jpeg .png .gif";
            }
            
        });
        int result = selec_image.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File arq = selec_image.getSelectedFile();
          
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            Main tela = new Main();
            tela.setVisible(true);
        });
    }
}
