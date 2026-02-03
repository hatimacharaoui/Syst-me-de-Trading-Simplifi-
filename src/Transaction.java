import java.time.LocalDate;
import java.util.Date;

public class Transaction {
    private String TypeOpération;
    private Asset actif;
    private int quantité;
    private double prix;
    private LocalDate date;

    public Transaction(String typeOpération, Asset actif, int quantité, double prix) {
        TypeOpération = typeOpération;
        this.actif = actif;
        this.quantité = quantité;
        this.prix = prix;
        this.date = LocalDate.now();
    }

    public String getTypeOpération() {
        return TypeOpération;
    }

    public void setTypeOpération(String typeOpération) {
        TypeOpération = typeOpération;
    }

    public Asset getActif() {
        return actif;
    }

    public void setActif(Asset actif) {
        this.actif = actif;
    }

    public int getQuantité() {
        return quantité;
    }

    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public LocalDate getDate() {
        return date;
    }
}
