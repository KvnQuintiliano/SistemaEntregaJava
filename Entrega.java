public class Entrega {
    private String destinatario;
    private String endereco;
    private String status;

    public Entrega(String destinatario, String endereco) {
        this.destinatario = destinatario;
        this.endereco = endereco;
        this.status = "Pendente";
    }

    public void atualizarStatus(String novoStatus) {
        this.status = novoStatus;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getStatus() {
        return status;
    }

    public void exibirEntrega() {
        System.out.println("Destinatário: " + destinatario);
        System.out.println("Endereço: " + endereco);
        System.out.println("Status: " + status);
    }

    @Override
    public String toString() {
        return destinatario + " - " + endereco + " [" + status + "]";
    }
}