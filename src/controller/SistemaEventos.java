package src.controller;

import src.Usuario;
import src.Evento;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SistemaEventos {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    private Map<Usuario, List<Evento>> participacoes = new HashMap<>();

    private static final String ARQUIVO_EVENTOS = "data/events.data";
    private static final String ARQUIVO_USUARIOS = "data/users.data";
    private static final String ARQUIVO_PARTICIPACOES = "data/participacoes.data";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public SistemaEventos() {
        carregarUsuarios();
        carregarEventos();
        carregarParticipacoes();
    }

    public void cadastrarUsuario(String nome, String email, String cidade) {
        if (buscarUsuarioPorEmail(email) != null) {
            System.out.println("Usuário com este email já cadastrado.");
            return;
        }
        Usuario usuario = new Usuario(nome, email, cidade);
        usuarios.add(usuario);
        participacoes.put(usuario, new ArrayList<>());
        salvarUsuarios();
        salvarParticipacoes();
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public void cadastrarEvento(String nome, String endereco, String categoria, String dataHora, String descricao) {
        LocalDateTime horario;
        try {
            horario = LocalDateTime.parse(dataHora, formatter);
        } catch (Exception e) {
            System.out.println("Data e hora inválidas. Use o formato dd/MM/yyyy HH:mm");
            return;
        }
        Evento evento = new Evento(nome, endereco, categoria, horario, descricao);
        eventos.add(evento);
        salvarEventos();
        System.out.println("Evento cadastrado com sucesso!");
    }

    public void listarEventos() {
        eventos.sort(Comparator.comparing(Evento::getHorario));
        System.out.println("\n=== EVENTOS DISPONÍVEIS ===");
        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            System.out.println(i + " - " + e);
        }
    }

    public void participarDeEvento(int indexEvento, String emailUsuario) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        if (indexEvento < 0 || indexEvento >= eventos.size()) {
            System.out.println("Índice de evento inválido.");
            return;
        }
        Evento evento = eventos.get(indexEvento);
        List<Evento> eventosDoUsuario = participacoes.get(usuario);
        if (eventosDoUsuario.contains(evento)) {
            System.out.println("Você já está participando deste evento.");
            return;
        }
        eventosDoUsuario.add(evento);
        salvarParticipacoes();
        System.out.println("Participação confirmada!");
    }

    public void cancelarParticipacao(int indexEvento, String emailUsuario) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        if (indexEvento < 0 || indexEvento >= eventos.size()) {
            System.out.println("Índice de evento inválido.");
            return;
        }
        Evento evento = eventos.get(indexEvento);
        List<Evento> eventosDoUsuario = participacoes.get(usuario);
        if (!eventosDoUsuario.contains(evento)) {
            System.out.println("Você não está participando deste evento.");
            return;
        }
        eventosDoUsuario.remove(evento);
        salvarParticipacoes();
        System.out.println("Participação cancelada.");
    }

    public void listarEventosDoUsuario(String emailUsuario) {
        Usuario usuario = buscarUsuarioPorEmail(emailUsuario);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        List<Evento> meusEventos = participacoes.get(usuario);
        System.out.println("\n=== MEUS EVENTOS ===");
        if (meusEventos.isEmpty()) {
            System.out.println("Você não está participando de nenhum evento.");
        } else {
            for (Evento e : meusEventos) {
                System.out.println(e);
            }
        }
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    private void salvarEventos() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_EVENTOS))) {
            for (Evento e : eventos) {
                writer.println(e.getNome() + ";" + e.getEndereco() + ";" + e.getCategoria() + ";" + e.getHorario().format(formatter) + ";" + e.getDescricao());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos.");
        }
    }

    private void carregarEventos() {
        File arquivo = new File(ARQUIVO_EVENTOS);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    String nome = partes[0];
                    String endereco = partes[1];
                    String categoria = partes[2];
                    LocalDateTime horario = LocalDateTime.parse(partes[3], formatter);
                    String descricao = partes[4];
                    eventos.add(new Evento(nome, endereco, categoria, horario, descricao));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos.");
        }
    }

    private void salvarUsuarios() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario u : usuarios) {
                writer.println(u.getNome() + ";" + u.getEmail() + ";" + u.getCidade());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários.");
        }
    }

    private void carregarUsuarios() {
        File arquivo = new File(ARQUIVO_USUARIOS);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    String nome = partes[0];
                    String email = partes[1];
                    String cidade = partes[2];
                    Usuario usuario = new Usuario(nome, email, cidade);
                    usuarios.add(usuario);
                    participacoes.put(usuario, new ArrayList<>());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários.");
        }
    }

    private void salvarParticipacoes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_PARTICIPACOES))) {
            for (Map.Entry<Usuario, List<Evento>> entry : participacoes.entrySet()) {
                Usuario usuario = entry.getKey();
                List<Evento> eventosUsuario = entry.getValue();
                for (Evento evento : eventosUsuario) {
                    writer.println(usuario.getEmail() + ";" + evento.getNome());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar participações.");
        }
    }

    private void carregarParticipacoes() {
        File arquivo = new File(ARQUIVO_PARTICIPACOES);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    String emailUsuario = partes[0];
                    String nomeEvento = partes[1];
                    Usuario usuario = buscarUsuarioPorEmail(emailUsuario);
                    Evento evento = buscarEventoPorNome(nomeEvento);
                    if (usuario != null && evento != null) {
                        participacoes.get(usuario).add(evento);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar participações.");
        }
    }

    private Evento buscarEventoPorNome(String nome) {
        for (Evento e : eventos) {
            if (e.getNome().equalsIgnoreCase(nome)) {
                return e;
            }
        }
        return null;
    }

    public void removerEvento(int indexEvento) {
        if (indexEvento >= 0 && indexEvento < eventos.size()) {
            Evento evento = eventos.remove(indexEvento);
            for (List<Evento> participacoesDoEvento : participacoes.values()) {
                participacoesDoEvento.remove(evento);
            }
            salvarEventos();
            salvarParticipacoes();
            System.out.println("Evento removido com sucesso!");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void atualizarEvento(int indexEvento, String nome, String endereco, String categoria, String dataHora, String descricao) {
        if (indexEvento >= 0 && indexEvento < eventos.size()) {
            Evento evento = eventos.get(indexEvento);
            try {
                LocalDateTime horario = LocalDateTime.parse(dataHora, formatter);
                evento.setNome(nome);
                evento.setEndereco(endereco);
                evento.setCategoria(categoria);
                evento.setHorario(horario);
                evento.setDescricao(descricao);
                salvarEventos();
                System.out.println("Evento atualizado com sucesso!");
            } catch (Exception e) {
                System.out.println("Data e hora inválidas. Use o formato dd/MM/yyyy HH:mm");
            }
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void buscarEventosPorNome(String nome) {
        List<Evento> eventosEncontrados = new ArrayList<>();
        for (Evento e : eventos) {
            if (e.getNome().toLowerCase().contains(nome.toLowerCase())) {
                eventosEncontrados.add(e);
            }
        }
        if (!eventosEncontrados.isEmpty()) {
            System.out.println("\n=== EVENTOS ENCONTRADOS ===");
            for (Evento e : eventosEncontrados) {
                System.out.println(e);
            }
        } else {
            System.out.println("Nenhum evento encontrado.");
        }
    }

    public void buscarEventosPorCategoria(String categoria) {
        List<Evento> eventosEncontrados = new ArrayList<>();
        for (Evento e : eventos) {
            if (e.getCategoria().toLowerCase().contains(categoria.toLowerCase())) {
                eventosEncontrados.add(e);
            }
        }
        if (!eventosEncontrados.isEmpty()) {
            System.out.println("\n=== EVENTOS ENCONTRADOS ===");
            for (Evento e : eventosEncontrados) {
                System.out.println(e);
            }
        } else {
            System.out.println("Nenhum evento encontrado.");
        }
    }
}
