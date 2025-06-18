package com.github.pursspoint;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

public class Galer extends JFrame {

    private final JFileChooser selec_image;
    private final JButton b_selec_image;

    // Painel scrollável na direita
    private final JPanel painelDireito;

    public Galer() {
        // Configuração da janela principal
        this.setTitle("Navegador de imagens");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.setLayout(new BorderLayout());

        // Painel principal (centro)
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(painelPrincipal, BorderLayout.CENTER);

        // Botão de selecionar imagem
        b_selec_image = new JButton("Selecionar foto");
        b_selec_image.setPreferredSize(new Dimension(200, 40));
        painelPrincipal.add(b_selec_image);

        // FileChooser configurado
        selec_image = new JFileChooser();
        selec_image.setCurrentDirectory(new File(""));
        selec_image.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Painel direito scrollável (coluna)
        painelDireito = new JPanel();
        painelDireito.setLayout(new BoxLayout(painelDireito, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(painelDireito);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        scrollPane.setPreferredSize(new Dimension(200, getHeight()));
        this.add(scrollPane, BorderLayout.EAST);

        // Ação do botão
        b_selec_image.addActionListener(e -> OpenFile());
    }

    private void OpenFile() {
        selec_image.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                String name = f.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return "Imagens (.jpg, .jpeg, .png, .gif)";
            }
        });

        int result = selec_image.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File arq = selec_image.getSelectedFile();

            // Adiciona o nome da imagem no painel direito (pode ser substituído por thumbnail futuramente)
            JLabel label = new JLabel(arq.getName());
            painelDireito.add(label);
            painelDireito.revalidate(); // Atualiza layout
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Galer tela = new Galer();
            tela.setVisible(true);
        });
    }
}
