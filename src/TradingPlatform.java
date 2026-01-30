import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TradingPlatform {
    private ArrayList<Trader> traders;
    private ArrayList<Stock> stocks;
    private ArrayList<Asset> assets;

    public TradingPlatform(){
        this.traders = new ArrayList<>();
        this.stocks = new ArrayList<>();
        this.assets = new ArrayList<>();
    }

    public ArrayList<Trader> getTraders() {
        return traders;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public void AjouterActif(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le type d’actif:");
        System.out.println("  1. Stock");
        System.out.println("  2. Crypto Currency");
        int choix = sc.nextInt();
        System.out.println("Entrez le Nom d’actif:");
        String Nom = sc.nextLine();
        System.out.println("Entrez le Code d’actif:");
        int Code = sc.nextInt();
        System.out.println("Entrez le prix Unitaire d’actif:");
        double prix = sc.nextDouble();
        String type = "";
        if(choix == 1){
            type = "Stock";
        } else if (choix == 2) {
            type = "Crypto Currency";
        }
        Asset a = new Asset(Nom,Code,prix, type);
        assets.add(a);
        System.out.println("L'Asset a été ajouté avec succès.");
    }
    public void AjouterTrader(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrer votre Nom: ");
        String nom = sc.nextLine();
        System.out.println("entrer votre identifiant: ");
        int id = sc.nextInt();
        double solde;
        do {
            System.out.println("Entrer un solde initial: ");
            solde = sc.nextDouble();
            if (solde < 100) {
                System.out.println("Le montant à déposer doit être supérieur à 100 DH\n");
            }
        } while (solde < 100);

        Portfolio<Double, Integer,Asset> p = null;
        p.setValeurTotale(solde);
        Trader trader = new Trader(id,nom,solde,p);
        traders.add(trader);
        System.out.println("Le trader a été ajouté avec succès.");
    }

    public void afficherListeActifs(){
        System.out.println("Liste des Actifs :");
        for (Asset a : assets) {
            a.afficherAsset();
        }
    }

    public void ConsulterPortefeuille(){
        Scanner sc = new Scanner(System.in);
        System.out.println("entrer votre identifiant: ");
        int id = sc.nextInt();
        boolean trouve = false;
            for (Trader t : traders) {
                if (t.getIdentifiant() == id) {
                    trouve = true;
                    System.out.println("Portefeuille : ");
                    System.out.println("   Valeur Totale : "+t.getPortfolio().getValeurTotale());
                    System.out.println("   Quantités associées : "+t.getPortfolio().getQuantité());
                    System.out.println("   Actifs détenus : ");
                    for (Asset a : t.getPortfolio().getAssets()){
                        System.out.println(a.getNom()+" : ");
                    }
                    }
                }
        if (!trouve) {
            System.out.println("Ce identifiant n'existe pas.");
        }


    }
    public void HistoriqueTransactions(){

    }
}
