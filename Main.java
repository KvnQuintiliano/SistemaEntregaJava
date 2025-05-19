import src.controller.SistemaEventos;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            SistemaEventos sistema = new SistemaEventos();

            System.out.println("=====================================");
            System.out.println("     BEM-VINDO AO EVENTOS CIDADE!    ");
            System.out.println("=====================================");

            int opcao = -1;
            while (opcao != 0) {
                System.out.println("\nMenu Principal:");
                System.out.println("1. Cadastrar Usuário");
                System.out.println("2. Cadastrar Evento");
                System.out.println("3. Listar Eventos");
                System.out.println("4. Participar de Evento");
                System.out.println("5. Cancelar Participação");
                System.out.println("6. Ver Meus Eventos");
                System.out.println("7. Remover Evento");
                System.out.println("8. Atualizar Evento");
                System.out.println("9. Buscar Eventos por Nome");
                System.out.println("10. Buscar Eventos por Categoria");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    scanner.nextLine(); // limpar entrada inválida
                    continue;
                }
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpar buffer

                switch (opcao) {
                    case 1 -> {
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Cidade: ");
                        String cidade = scanner.nextLine();
                        sistema.cadastrarUsuario(nome, email, cidade);
                    }
                    case 2 -> {
                        System.out.print("Nome do Evento: ");
                        String nomeEvento = scanner.nextLine();
                        System.out.print("Endereço: ");
                        String endereco = scanner.nextLine();
                        System.out.print("Categoria (ex: show, festa, esporte): ");
                        String categoria = scanner.nextLine();
                        System.out.print("Data e Hora (dd/MM/yyyy HH:mm): ");
                        String dataHora = scanner.nextLine();
                        System.out.print("Descrição: ");
                        String descricao = scanner.nextLine();
                        sistema.cadastrarEvento(nomeEvento, endereco, categoria, dataHora, descricao);
                    }
                    case 3 -> sistema.listarEventos();
                    case 4 -> {
                        sistema.listarEventos();
                        System.out.print("Digite seu email: ");
                        String emailPart = scanner.nextLine();
                        System.out.print("Digite o número do evento para participar: ");
                        if (scanner.hasNextInt()) {
                            int indexPart = scanner.nextInt();
                            scanner.nextLine();
                            sistema.participarDeEvento(indexPart, emailPart);
                        } else {
                            System.out.println("Entrada inválida. Por favor, digite um número válido para o evento.");
                            scanner.nextLine();
                        }
                    }
                    case 5 -> {
                        sistema.listarEventos();
                        System.out.print("Digite seu email: ");
                        String emailCancel = scanner.nextLine();
                        System.out.print("Digite o número do evento para cancelar participação: ");
                        if (scanner.hasNextInt()) {
                            int indexCancel = scanner.nextInt();
                            scanner.nextLine();
                            sistema.cancelarParticipacao(indexCancel, emailCancel);
                        } else {
                            System.out.println("Entrada inválida. Por favor, digite um número válido para o evento.");
                            scanner.nextLine();
                        }
                    }
                    case 6 -> {
                        System.out.print("Digite seu email para ver seus eventos: ");
                        String emailConsulta = scanner.nextLine();
                        sistema.listarEventosDoUsuario(emailConsulta);
                    }
                    case 7 -> {
                        sistema.listarEventos();
                        System.out.print("Digite o número do evento para remover: ");
                        if (scanner.hasNextInt()) {
                            int indexRemover = scanner.nextInt();
                            scanner.nextLine();
                            sistema.removerEvento(indexRemover);
                        } else {
                            System.out.println("Entrada inválida. Por favor, digite um número válido para o evento.");
                            scanner.nextLine();
                        }
                    }
                    case 8 -> {
                        sistema.listarEventos();
                        System.out.print("Digite o número do evento para atualizar: ");
                        if (scanner.hasNextInt()) {
                            int indexAtualizar = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Novo nome do Evento: ");
                            String novoNome = scanner.nextLine();
                            System.out.print("Novo endereço: ");
                            String novoEndereco = scanner.nextLine();
                            System.out.print("Nova categoria: ");
                            String novaCategoria = scanner.nextLine();
                            System.out.print("Nova data e hora (dd/MM/yyyy HH:mm): ");
                            String novaDataHora = scanner.nextLine();
                            System.out.print("Nova descrição: ");
                            String novaDescricao = scanner.nextLine();
                            sistema.atualizarEvento(indexAtualizar, novoNome, novoEndereco, novaCategoria, novaDataHora, novaDescricao);
                        } else {
                            System.out.println("Entrada inválida. Por favor, digite um número válido para o evento.");
                            scanner.nextLine();
                        }
                    }
                    case 9 -> {
                        System.out.print("Digite o nome para buscar eventos: ");
                        String nomeBusca = scanner.nextLine();
                        sistema.buscarEventosPorNome(nomeBusca);
                    }
                    case 10 -> {
                        System.out.print("Digite a categoria para buscar eventos: ");
                        String categoriaBusca = scanner.nextLine();
                        sistema.buscarEventosPorCategoria(categoriaBusca);
                    }
                    case 0 -> System.out.println("Até a próxima, Kevão!");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
    }
}
