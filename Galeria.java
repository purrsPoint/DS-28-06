import javax.swing.*;

class Galeria extends JFrame{    

    public void fazerTela(){
        // Cria a janela (JFrame)
        JFrame frame = new JFrame("Minha Página Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // largura x altura

        // Cria um painel (JPanel) onde os componentes vão ficar
        JPanel panel = new JPanel();
        
        // Cria componentes
        JLabel label = new JLabel("Bem-vindo à minha página Swing!");
        JButton button = new JButton("Clique aqui");
        JTextField textField = new JTextField(20);

        // Adiciona os componentes ao painel
        panel.add(label);
        panel.add(textField);
        panel.add(button);

        // Adiciona o painel à janela
        frame.add(panel);

        // Torna a janela visível
        frame.setVisible(true);

        //deixa a janela visivel
        setVisible(true);

    }

    private OpenFile(){
         int result = selectfile.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File arq = selectfile.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(arq))) {
                text.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Não foi possível abrir o arquivo: " + ex.getMessage());
            }
        }
    }
}
