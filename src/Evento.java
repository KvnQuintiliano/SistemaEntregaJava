package src;

public class Evento {
    private String nome;
    private String endereco;
    private String categoria;
    private java.time.LocalDateTime horario;
    private String descricao;

    public Evento(String nome, String endereco, String categoria, java.time.LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public java.time.LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(java.time.LocalDateTime horario) {
        this.horario = horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return nome + " | " + categoria + " | " + endereco + " | " + horario + " | " + descricao;
    }
}
