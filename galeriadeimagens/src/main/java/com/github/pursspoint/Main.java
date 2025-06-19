package com.github.pursspoint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;import java.awt.GridLayout;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        painel_lateral.setLayout(new GridLayout(0,1,0, 0x64));

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
                if(f.getName().endsWith(".gif")) {
                    return true;
                }
               
               return false;
            }

            @Override
            public String getDescription() {
                return "Arquivos .jpg .jpeg .png .gif";
            }
            
        });

       int resultado = selec_image.showOpenDialog(this);

        if(resultado == JFileChooser.APPROVE_OPTION) {

           
            painel_lateral.removeAll();

            File pasta = selec_image.getSelectedFile();
            File[] arquivos = pasta.listFiles(); 
          
            for(File arquivo : arquivos){
                try{    
                BufferedImage imagem = ImageIO.read(arquivo);

                if(imagem != null){
                    ImageIcon icon = new ImageIcon(imagem.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                    JLabel label = new JLabel(icon);
                    label.setAlignmentX(Component.CENTER_ALIGNMENT);
                    painel_lateral.add(label);
                    painel_lateral.add(Box.createRigidArea(new Dimension(0, 10))); 
                }else{
                    System.out.println("Nao deu "+arquivo.getName());
                }
              }catch(IOException e){
                e.printStackTrace();
              }
        }    

        painel_lateral.revalidate();
        painel_lateral.repaint();      
    }
 }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            Main tela = new Main();
            tela.setVisible(true);
        });
    }
}

