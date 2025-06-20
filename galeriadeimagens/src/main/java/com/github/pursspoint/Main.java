package com.github.pursspoint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

    public Main() {
        this.setTitle("navegador de imagens");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 800, 700);
        this.setLayout(new BorderLayout());

        JPanel painel_principal = new JPanel();
        painel_principal.setBackground(Color.gray);
        painel_principal.setLayout(new FlowLayout(FlowLayout.LEADING));

        b_selec_image = new JButton("Selecionar foto");
        b_selec_image.setPreferredSize(new Dimension(getWidth() / 2, 40));

        selec_image = new JFileChooser();
        selec_image.setCurrentDirectory(new File(System.getProperty("user.home")));
        selec_image.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // só pastas

        painel_principal.add(b_selec_image);

        painel_lateral = new JPanel();
        painel_lateral.setLayout(new GridLayout(0, 1, 0, 30));

        JScrollPane painel_escrolavel = new JScrollPane(painel_lateral);
        painel_escrolavel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        painel_escrolavel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        painel_escrolavel.setPreferredSize(new Dimension(200, getHeight()));
        painel_escrolavel.getVerticalScrollBar().setUnitIncrement(24);

        this.getContentPane().add(painel_principal, BorderLayout.CENTER);
        this.add(painel_escrolavel, BorderLayout.EAST);

        b_selec_image.addActionListener(e -> {
            OpenFile();
        });
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
                        if (imagem != null) {
                            JPanel painel_imagem = new JPanel();
                            painel_imagem.setLayout(new BorderLayout());
                            painel_imagem.setMaximumSize(new Dimension(150, 150));

                            ImageIcon icon = new ImageIcon(imagem.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                            JLabel label = new JLabel(icon);
                            label.setAlignmentX(Component.CENTER_ALIGNMENT);

                            JLabel nome_imagem = new JLabel(arquivo.getName(), JLabel.CENTER);
                            nome_imagem.setFont(nome_imagem.getFont().deriveFont(10f));

                            // Ao clicar na miniatura...
                            label.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    painel_principal.removeAll();

                                    // Imagem central maior
                                    ImageIcon iconFull = new ImageIcon(imagem.getScaledInstance(400, 400, Image.SCALE_SMOOTH));
                                    JLabel fullLabel = new JLabel(iconFull, JLabel.CENTER);
                                    painel_principal.setLayout(new BorderLayout());
                                    painel_principal.add(fullLabel, BorderLayout.CENTER);

                                    painel_principal.revalidate();
                                    painel_principal.repaint();
                                }
                            });

                            painel_imagem.add(label, BorderLayout.CENTER);
                            painel_imagem.add(nome_imagem, BorderLayout.SOUTH);

                            painel_lateral.add(painel_imagem);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            painel_lateral.revalidate();
            painel_lateral.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main tela = new Main();
            tela.setVisible(true);
        });
    }
}
