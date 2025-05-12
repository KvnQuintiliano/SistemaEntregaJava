import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SistemaEntregaGUI {
    private JFrame frame;
    private DefaultListModel<String> listaModel;
    private JList<String> listaEntregas;
    private ArrayList<Entrega> entregas;

    public SistemaEntregaGUI() {
        entregas = new ArrayList<>();

        // Criando a janela
        frame = new JFrame("Sistema de Entregas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLayout(new BorderLayout());

        // Criando lista de entregas
        listaModel = new DefaultListModel<>();
        listaEntregas = new JList<>(listaModel);
        JScrollPane scrollPane = new JScrollPane(listaEntregas);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Criando painel de botões com layout melhor
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10)); // GridLayout melhora a organização
        JButton btnAdicionar = new JButton("Cadastrar Entrega");
        JButton btnAtualizar = new JButton("Atualizar Status");
        JButton btnExcluir = new JButton("Excluir Entrega");

        panel.add(btnAdicionar);
        panel.add(btnAtualizar);
        panel.add(btnExcluir);
        frame.add(panel, BorderLayout.SOUTH);

        // Ação para cadastrar entrega
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String destinatario = JOptionPane.showInputDialog("Digite o nome do destinatário:");
                String endereco = JOptionPane.showInputDialog("Digite o endereço da entrega:");
                if (destinatario != null && endereco != null) {
                    Entrega entrega = new Entrega(destinatario, endereco);
                    entregas.add(entrega);
                    listaModel.addElement(entrega.toString());
                }
            }
        });

        // Ação para atualizar status
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = listaEntregas.getSelectedIndex();
                if (index >= 0) {
                    String novoStatus = JOptionPane.showInputDialog("Digite o novo status da entrega:");
                    entregas.get(index).atualizarStatus(novoStatus);
                    listaModel.set(index, entregas.get(index).toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma entrega para atualizar!");
                }
            }
        });

        // Ação para excluir entrega
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = listaEntregas.getSelectedIndex();
                if (index >= 0) {
                    entregas.remove(index);
                    listaModel.remove(index);
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma entrega para excluir!");
                }
            }
        });

        // Exibir janela
        frame.setVisible(true);
    }

   public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new SistemaEntregaGUI();
        }
    });
}