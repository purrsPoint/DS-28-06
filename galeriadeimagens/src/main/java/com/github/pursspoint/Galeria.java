package com.github.pursspoint;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class Galeria extends JFrame {

    private final JFileChooser selec_image;
    private final JButton b_selec_image;
    private final JPanel painel_lateral;
    private final JPanel painel_principal;
    private final JPanel painel_visualizacao;
    private final JTextArea nome_arq_area;

    public Galeria() {

        //configura o JFrame
        this.setTitle("navegador de imagens");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 800, 500);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        // Painel principal que comporta o botão o textarea e área de visualização
        painel_principal = new JPanel(new BorderLayout());
        painel_principal.setBorder(BorderFactory.createLineBorder(Color.black,5));

        //configura a area onde o caminho do arquivo fica
        nome_arq_area = new JTextArea();
        nome_arq_area.setText("");
        nome_arq_area.setEditable(false);
        nome_arq_area.setLineWrap(true);
        nome_arq_area.setWrapStyleWord(true);
        nome_arq_area.setBackground(Color.white);
        nome_arq_area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nome_arq_area.setPreferredSize(new Dimension(300, 20));

        // Painel do botão
        JPanel painel_topo = new JPanel(new BorderLayout());

        // Criando o botao
        b_selec_image = new JButton("Selecionar pasta");
        b_selec_image.setPreferredSize(new Dimension(0, 40));
        b_selec_image.setBackground(Color.BLACK);
        b_selec_image.setForeground(Color.WHITE);
        b_selec_image.setFocusPainted(false);
        b_selec_image.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        
        b_selec_image.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                b_selec_image.setBackground(Color.WHITE);
                b_selec_image.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e){
                b_selec_image.setBackground(Color.BLACK);
                b_selec_image.setForeground(Color.WHITE);
            }
        });

        painel_topo.add(b_selec_image, BorderLayout.CENTER);
    
        
        // Painel onde aparecerá a imagem centralizada
        painel_visualizacao = new JPanel(new BorderLayout());
        painel_visualizacao.setBackground(Color.white);

        //adiciona os itens do painel principal
        painel_principal.add(painel_visualizacao, BorderLayout.CENTER);
        painel_principal.add(nome_arq_area,BorderLayout.SOUTH);
        painel_principal.add(painel_topo, BorderLayout.NORTH);
        
        // File chooser
        selec_image = new JFileChooser();
        selec_image.setCurrentDirectory(new File(System.getProperty("user.home")));
        selec_image.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Painel lateral com imagens pequenas
        painel_lateral = new JPanel();
        painel_lateral.setLayout(new BoxLayout(painel_lateral, BoxLayout.Y_AXIS));
        painel_lateral.setBorder(BorderFactory.createEmptyBorder(5, 5, 5 ,5));

        //faz o painel_lateral ser escrolavel
        JScrollPane painel_escrolavel = new JScrollPane(painel_lateral);
        painel_escrolavel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        painel_escrolavel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        painel_escrolavel.setPreferredSize(new Dimension(250, getHeight()));
        painel_escrolavel.getVerticalScrollBar().setUnitIncrement(24);

        //adiciona os componentes principais
        this.getContentPane().add(painel_principal, BorderLayout.CENTER);
        this.add(painel_escrolavel, BorderLayout.EAST);



        b_selec_image.addActionListener(e -> OpenFile());

    }

    private void OpenFile() {
        
        int resultado = selec_image.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            painel_lateral.removeAll();
            painel_visualizacao.removeAll();
            nome_arq_area.setText("");

            //atualiza o painel onde a imagem aumentada fica
            painel_visualizacao.revalidate();
            painel_visualizacao.repaint();

            //atualiza a area do caminho
            nome_arq_area.revalidate();
            nome_arq_area.repaint();

            File pasta = selec_image.getSelectedFile();
            File[] arquivos = pasta.listFiles();

            if (arquivos == null) {
                System.out.println("Pasta vazia ou inacessível");
                return;
            }


            for (File arquivo : arquivos) {
                String nome = arquivo.getName().toLowerCase();
                if (arquivo.isFile() && (nome.endsWith(".jpg") || nome.endsWith(".jpeg") || nome.endsWith(".png") || nome.endsWith(".gif"))) {
                    try {

                        BufferedImage imagem = ImageIO.read(arquivo);

                        if(imagem == null){
                            System.out.println("Imagem corrompida ou nula:"+ arquivo.getAbsolutePath());
                        }
                        else{
                            
                            ImageIcon icon = new ImageIcon(imagem.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                            JLabel icon_label = new JLabel(icon);
                            icon_label.setPreferredSize(new Dimension(150 , 150));
                            icon_label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            JLabel label = new JLabel(arquivo.getName(), JLabel.CENTER);
                            label.setFont(label.getFont().deriveFont(10f));
                            label.setHorizontalTextPosition(JLabel.CENTER);
                            label.setVerticalTextPosition(JLabel.BOTTOM);
                            label.setPreferredSize(new Dimension(150, 20));

                            JPanel container = new JPanel(new BorderLayout());
                            container.add(icon_label, BorderLayout.CENTER);
                            container.add(label, BorderLayout.SOUTH);
                            container.setPreferredSize(new Dimension(150,200));
                            container.setMaximumSize(new Dimension(150,200));
                            container.setBorder(BorderFactory.createEmptyBorder(15,2,15,2));
                            

                            container.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    exibirImagem(imagem, arquivo);
                                    
                                }
                            });
                            
                            painel_lateral.add(container);
                        }
                    } catch (IOException e) {

                    }
                }
            }

            painel_lateral.revalidate();
            painel_lateral.repaint();
        }
    }

    private void exibirImagem(BufferedImage imagem, File arquivo) {
        painel_visualizacao.removeAll();

        int largura = Math.min(imagem.getWidth(), 350);
        int altura = Math.min(imagem.getHeight(), 350);
        ImageIcon imagem_grande = new ImageIcon(imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH));
        JLabel fullLabel = new JLabel(imagem_grande);

        nome_arq_area.setText(arquivo.getAbsolutePath());


        painel_visualizacao.add(fullLabel, BorderLayout.CENTER);

        painel_visualizacao.revalidate();
        painel_visualizacao.repaint();

    }

    public static void main(String[] args) {
   
            Galeria tela = new Galeria();
            tela.setVisible(true);
   
    }
}
