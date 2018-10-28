package co.dimitriongoua.badolrouteur.model;

public class Recharge {

    private String reference;
    private String montant;
    private String numero;
    private String timestamp;

    public Recharge() {
    }

    public Recharge(String reference, String montant, String numero) {
        this.reference = reference;
        this.montant = montant;
        this.numero = numero;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
