public class Entrega {
    String destinatario;
    String endereco;
    String status;

    // Construtor
    public Entrega(String destinatario, String endereco) {
        this.destinatario = destinatario;
        this.endereco = endereco;
        this.status = "Pendente";
    }

    // Atualiza o status
    public void atualizarStatus(String novoStatus) {
        this.status = novoStatus;
    }

    // Exibe informações da entrega
    public void exibirEntrega() {
        System.out.println("Destinatário: " + destinatario);
        System.out.println("Endereço: " + endereco);
        System.out.println("Status: " + status);
    }
}
public class Entrega {
    String destinatario;
    String endereco;
    String status;

    public Entrega(String destinatario, String endereco) {
        this.destinatario = destinatario;
        this.endereco = endereco;
        this.status = "Pendente";
    }

    public void atualizarStatus(String novoStatus) {
        this.status = novoStatus;
    }

    @Override
    public String toString() {
        return destinatario + " - " + endereco + " | Status: " + status;
    }
}