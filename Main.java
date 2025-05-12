import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Entrega> entregas = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🚚 Bem-vindo ao Sistema de Entregas!");

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Cadastrar entrega");
            System.out.println("2 - Exibir todas as entregas");
            System.out.println("3 - Atualizar status de entrega");
            System.out.println("4 - Sair");

            int opcao;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número.");
                scanner.nextLine(); // Limpa o buffer inválido
                continue;
            }

            if (opcao == 1) {
                cadastrarEntrega();
            } else if (opcao == 2) {
                exibirEntregas();
            } else if (opcao == 3) {
                atualizarStatusEntrega();
            } else if (opcao == 4) {
                System.out.println("Encerrando o sistema...");
                break;
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public static void cadastrarEntrega() {
        System.out.print("Digite o nome do destinatário: ");
        String destinatario = scanner.nextLine();
        System.out.print("Digite o endereço da entrega: ");
        String endereco = scanner.nextLine();

        Entrega novaEntrega = new Entrega(destinatario, endereco);
        entregas.add(novaEntrega);
        System.out.println("✅ Entrega cadastrada com sucesso!");
    }

    public static void exibirEntregas() {
        if (entregas.isEmpty()) {
            System.out.println("Nenhuma entrega cadastrada.");
        } else {
            System.out.println("\n📜 Lista de Entregas:");
            for (int i = 0; i < entregas.size(); i++) {
                System.out.println("Entrega #" + i);
                entregas.get(i).exibirEntrega();
                System.out.println("---------------------");
            }
        }
    }

    public static void atualizarStatusEntrega() {
        if (entregas.isEmpty()) {
            System.out.println("Nenhuma entrega cadastrada.");
            return;
        }

        System.out.println("\n✏️ Lista de entregas:");
        for (int i = 0; i < entregas.size(); i++) {
            System.out.println(i + " - " + entregas.get(i).getDestinatario() + " | Status: " + entregas.get(i).getStatus());
        }

        System.out.print("Escolha o número da entrega que deseja atualizar: ");
        int indice;
        try {
            indice = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
        } catch (java.util.InputMismatchException e) {
            System.out.println("Entrada inválida! Por favor, digite um número.");
            scanner.nextLine(); // Limpa o buffer inválido
            return;
        }

        if (indice < 0 || indice >= entregas.size()) {
            System.out.println("❌ Número inválido! Tente novamente.");
            return;
        }

        System.out.print("Digite o novo status (Ex: 'Em andamento', 'Entregue', 'Cancelada'): ");
        String novoStatus = scanner.nextLine();

        entregas.get(indice).atualizarStatus(novoStatus);
        System.out.println("✅ Status atualizado com sucesso!");
    }
}