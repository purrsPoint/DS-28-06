package com.github.pursspoint;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

class Main extends JFrame {

    private final JFileChooser selec_image;
    private final JButton b_selec_image;
    private final JPanel painel_lateral;
    private final JPanel painel_principal;
    private final JPanel painel_visualizacao;

    public Main() {
        this.setTitle("navegador de imagens");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 800, 700);
        this.setLayout(new BorderLayout());
        this.setResizable(false);


        // Painel principal com botão e área de visualização
        painel_principal = new JPanel(new BorderLayout());
        painel_principal.setBackground(Color.gray);

        // Painel do botão
        JPanel painel_topo = new JPanel(new BorderLayout());
        b_selec_image = new JButton("Selecionar pasta");
        b_selec_image.setPreferredSize(new Dimension(0, 40));
        painel_topo.add(b_selec_image, BorderLayout.CENTER);

        painel_principal.add(painel_topo, BorderLayout.NORTH);

        // Painel onde aparecerá a imagem centralizada
        painel_visualizacao = new JPanel(new GridBagLayout()); // Para centralizar
        painel_visualizacao.setBackground(Color.gray);
        painel_principal.add(painel_visualizacao, BorderLayout.CENTER);

        // File chooser
        selec_image = new JFileChooser();
        selec_image.setCurrentDirectory(new File(System.getProperty("user.home")));
        selec_image.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Painel lateral com imagens pequenas
        painel_lateral = new JPanel(new GridLayout(0, 1, 0, 30));
        JScrollPane painel_escrolavel = new JScrollPane(painel_lateral);
        painel_escrolavel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        painel_escrolavel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        painel_escrolavel.setPreferredSize(new Dimension(200, getHeight()));
        painel_escrolavel.getVerticalScrollBar().setUnitIncrement(24);

        this.getContentPane().add(painel_principal, BorderLayout.CENTER);
        this.add(painel_escrolavel, BorderLayout.EAST);

        b_selec_image.addActionListener(e -> OpenFile());
    }

    private void OpenFile() {
        int resultado = selec_image.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            painel_lateral.removeAll();

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
                            System.out.println("Imagem nula:"+ arquivo.getAbsolutePath());
                        }
                        else{
                           
                            
                            ImageIcon icon = new ImageIcon(imagem.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                            JLabel label = new JLabel(arquivo.getName(), icon,  JLabel.CENTER);
                            label.setFont(label.getFont().deriveFont(8f));
                            label.setHorizontalTextPosition(JLabel.CENTER);
                            label.setVerticalTextPosition(JLabel.BOTTOM);

                            JPanel container = new JPanel(new BorderLayout());
                            container.add(label,BorderLayout.CENTER);
                            container.setPreferredSize(new Dimension(150,180));
                            container.setMaximumSize(new Dimension(150,180));
                            container.setBorder(BorderFactory.createLineBorder(Color.BLACK));


                            label.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    exibirImagemCentral(imagem);
                                }
                            });
                            
                            container.add(label);
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

    private void exibirImagemCentral(BufferedImage imagem) {
        painel_visualizacao.removeAll();

        int largura = Math.min(imagem.getWidth(), 500);
        int altura = Math.min(imagem.getHeight(), 500);
        ImageIcon imagem_grande = new ImageIcon(imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH));
        JLabel fullLabel = new JLabel(imagem_grande);
        painel_visualizacao.add(fullLabel, new GridBagConstraints());

        painel_visualizacao.revalidate();
        painel_visualizacao.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main tela = new Main();
            tela.setVisible(true);
        });
    }
}
