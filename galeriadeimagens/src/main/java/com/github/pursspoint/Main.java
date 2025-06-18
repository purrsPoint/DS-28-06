package com.github.pursspoint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

class Main extends JFrame{    

    private final JFileChooser selec_image;
    private final JButton b_selec_image;
    private final JPanel painel_lateral;
    
  
    public Main() {
        // Cria a janela (JFrame)
        this.setTitle("navegador de imagens");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0,0,800, 700); // largura x altura
        this.setLayout(new BorderLayout());


        //cria o painel principal da pagina 
        JPanel  painel_principal = new JPanel();
        painel_principal.setBackground(Color.gray);
        painel_principal.setLayout(new FlowLayout(FlowLayout.LEADING));



        //cria o botao
        b_selec_image = new JButton();
        b_selec_image.setPreferredSize(new Dimension(getWidth()/2,40));
        b_selec_image.setText("Selecionar foto");

      

        //cria um filechoorser e seleciona o diretorio onde aparece
        selec_image = new JFileChooser(); 
        selec_image.setCurrentDirectory(new File(""));

        selec_image.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        //adiciona o botao ap painel prinicipal
        painel_principal.add(b_selec_image);
        
        

        //cria o painel lateral que fica do lado
        painel_lateral = new JPanel();
        painel_lateral.setLayout(new BoxLayout(painel_lateral, BoxLayout.Y_AXIS));

        //cria onde fica o scroll lateral
        JScrollPane painel_escrolavel = new JScrollPane(painel_lateral);
        painel_escrolavel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        painel_escrolavel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        painel_escrolavel.setPreferredSize(new Dimension(200, getHeight()));

        //adiciona itens
        this.getContentPane().add(painel_principal, BorderLayout.CENTER);       
        this.add(painel_escrolavel, BorderLayout.EAST);
        //adiciona metodo ao botao
        b_selec_image.addActionListener(e->{OpenFile();});
    


    }
    private void OpenFile(){
        
        selec_image.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
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
