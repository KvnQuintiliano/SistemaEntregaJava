import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class SistemaEntregaGUI {
    private JFrame frame;
    private DefaultListModel<String> listaModel;
    private JList<String> listaEntregas;
    private ArrayList<Entrega> entregas;
    private static final String ARQUIVO_SALVAMENTO = "entregas.txt";

    public SistemaEntregaGUI() {
        entregas = new ArrayList<>();
        carregarEntregas(); // Carregar histórico

        // Criando a janela
        frame = new JFrame("Sistema de Entregas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Configurando a cor de fundo da janela
        frame.getContentPane().setBackground(new Color(230, 230, 250)); // Lavanda

        // Criando lista de entregas
        listaModel = new DefaultListModel<>();
        listaEntregas = new JList<>(listaModel);
        listaEntregas.setFont(new Font("Verdana", Font.PLAIN, 12)); // Melhorando a fonte
        JScrollPane scrollPane = new JScrollPane(listaEntregas);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Criando painel de botões com layout melhor
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBackground(new Color(200, 200, 255)); // Cor de fundo do painel

        JButton btnAdicionar = new JButton("Cadastrar Entrega", new ImageIcon("icons/add.png"));
        JButton btnAtualizar = new JButton("Atualizar Status", new ImageIcon("icons/update.png"));
        JButton btnExcluir = new JButton("Excluir Entrega", new ImageIcon("icons/delete.png"));

        // Configurando cores e fontes dos botões
        Font fonteBotao = new Font("Arial", Font.BOLD, 14);
        btnAdicionar.setFont(fonteBotao);
        btnAdicionar.setBackground(new Color(144, 238, 144)); // Verde claro
        
        btnAtualizar.setFont(fonteBotao);
        btnAtualizar.setBackground(new Color(255, 165, 0)); // Laranja
        
        btnExcluir.setFont(fonteBotao);
        btnExcluir.setBackground(new Color(255, 99, 71)); // Vermelho claro

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
                    salvarEntregas(); // Salvar no arquivo
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
                    salvarEntregas(); // Atualizar no arquivo
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
                    salvarEntregas(); // Remover do arquivo
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma entrega para excluir!");
                }
            }
        });

        // Garante que a interface seja carregada corretamente na Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }

    // Método para salvar entregas em um arquivo
    private void salvarEntregas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_SALVAMENTO))) {
            for (Entrega entrega : entregas) {
                writer.println(entrega.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar entregas de um arquivo
    private void carregarEntregas() {
        File arquivo = new File(ARQUIVO_SALVAMENTO);
        if (arquivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_SALVAMENTO))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] partes = linha.split(" - ");
                    if (partes.length == 3) {
                        Entrega entrega = new Entrega(partes[0], partes[1]);
                        entrega.atualizarStatus(partes[2].replace("[", "").replace("]", ""));
                        entregas.add(entrega);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new SistemaEntregaGUI();
    }
}