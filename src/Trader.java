import com.sun.jdi.PrimitiveValue;
import javax.sound.sampled.Port;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trader extends Person{
    private double soldInitial;
    private Portfolio<Double, Integer,Asset> portfolio;
    private List<Transaction> transactions;

    public Trader(int Identifiant, String Nom, double soldInitial, Portfolio<Double, Integer,Asset> portfolio){
        super(Identifiant,Nom);
        this.soldInitial = soldInitial;
        this.portfolio = portfolio;
        this.transactions = new ArrayList<>();
    }
    public Trader(){
    }
    public double getSoldInitial(){
        return soldInitial;
    }
    public void setSoldInitial(double soldInitial){
        this.soldInitial = soldInitial;
    }

    public Portfolio<Double, Integer, Asset> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio<Double, Integer, Asset> portfolio) {
        this.portfolio = portfolio;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void acheterActif(TradingPlatform t){
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        boolean trouve = false;
        Trader trader = null;
        int id;
        do {
            System.out.println("Entrer votre Identifiant : ");
            id = sc.nextInt();
            trouve = false;
            for (Trader tr : t.getTraders()) {
                if (tr.getIdentifiant() == id) {
                    trader = tr;
                    trouve = true;
                    break;
                }
            }
            if (!trouve){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (!trouve);
        // liste Assets
        System.out.println("Liste des Actifs :");
            System.out.println("Stock : ");
            for (Asset a : t.getAssets()) {
                if (a.getTypeActif().equals("Stock")) {
                    System.out.println("   " + a.getNom() + ", code : " + a.getCode() + ", Prix Unitaire : " + a.getPrixUnitaire());
                }
            }

            System.out.println("Crypto Currency : ");
                for (Asset a2 : t.getAssets()){
            if (a2.getTypeActif().equals("Crypto Currency")){
                System.out.println("   "+a2.getNom()+", code : "+a2.getCode()+", Prix Unitaire : "+a2.getPrixUnitaire());
            }
        }

        System.out.println("Entrez le code d’actif:");
        int code = sc.nextInt();
        System.out.println("Entrez le nombre d’unités à acheter:");
        int nombre = sc.nextInt();
        Asset asset = null;
        for (Asset a : t.getAssets()){
            if (a.getCode() == code) {
                double total = a.getPrixUnitaire() * nombre;
                if (trader.getSoldInitial() >= total){
                    asset = a;
                } else {
                    System.out.println("Solde insuffisant pour acheter cet actif.");
                    return;
                }
                break;
            }
            }
        if (asset == null) {
            System.out.println("Aucun actif trouvé pour ce code.");
            return;
        }
        //quantité
        int quantité = trader.getPortfolio().getQuantité() + nombre;
        trader.getPortfolio().setQuantité(quantité);
        // solde
        trader.setSoldInitial(trader.getSoldInitial()- (asset.getPrixUnitaire() * nombre));
        //Assets
        boolean trouver = false;
        for (Asset a : trader.getPortfolio().getAssets()){
            if (a.getCode() == (asset.getCode())){
                a.addnombreAsset(nombre);
                trouver = true;
                break;
            }
        }
        if(!trouver) {
            Asset asset1 = new Asset(asset.getNom(),asset.getCode(),asset.getPrixUnitaire(),asset.getTypeActif(),nombre);
            trader.getPortfolio().getAssets().add(asset1);
        }

        System.out.println("L'Actif : "+ asset.getNom()+" a été achetée avec succès");
        //Historique transaction
        LocalDate date = LocalDate.now();
        Transaction tr = new Transaction("Achat",asset,nombre, asset.getPrixUnitaire(),date);
        trader.getTransactions().add(tr);
    }

    public void vendreActif(TradingPlatform t) {
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        boolean trouve = false;
        Trader trader = null;
        int id;
        do {
            System.out.println("Entrer votre Identifiant : ");
            id = sc.nextInt();
            trouve = false;
            for (Trader tr : t.getTraders()) {
                if (tr.getIdentifiant() == id) {
                    trader = tr;
                    trouve = true;
                    break;
                }
            }
            if (!trouve){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (!trouve);
        //aficher Liste Actif
        System.out.println("Liste des Actifs :");
        for (Asset a : trader.getPortfolio().getAssets()) {
            System.out.println("   Type : " + a.getTypeActif() + ", Nom : " + a.getNom() + ", quantité : " + a.getnombreAsset() + ", code : " + a.getCode());
        }
        System.out.println("Entrez le code d’actif:");
        int code = sc.nextInt();
        System.out.println("Entrez le nombre d’unités à vendre:");
        int nombre = sc.nextInt();

        Asset assetg = null;
        for (Asset ag : t.getAssets()) {
            if (code == ag.getCode()) {
                assetg = ag;
            }
        }
        int quantité = 0;
        boolean trouve1 = false;
        for (Asset a : trader.getPortfolio().getAssets()) {
            if (code == a.getCode()) {
                trouve1 = true;
                if (nombre <= a.getnombreAsset()) {
                    // set nombreAsset
                    a.vendreAsset(nombre);
                    // set ValeurTotal
                    double newValeurTotal = (trader.getPortfolio().getValeurTotale() - (a.getPrixUnitaire() * nombre)) + (assetg.getPrixUnitaire() * nombre);
                    trader.getPortfolio().setValeurTotale(newValeurTotal);
                    // set quantité
                    trader.getPortfolio().setQuantité(trader.getPortfolio().getQuantité() - nombre);
                    // suprimer if quantité asset = 0
                    if (a.getnombreAsset() == 0) {
                        trader.getPortfolio().getAssets().remove(a);
                    }
                } else {
                    System.out.println("Le nombre d’actifs à vendre est inférieur à celui que vous possédez.");
                    return;
                }
            }
        }
         if(!trouve1) {
            System.out.println("Aucun actif trouvé pour ce code.");
            return;
        }
        //Historique transaction
        LocalDate date = LocalDate.now();
        Transaction tr = new Transaction("Vente",assetg,nombre, assetg.getPrixUnitaire(),date);
        trader.getTransactions().add(tr);
    }

    public void ConsulterPortefeuille(TradingPlatform t){
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        boolean trouve = false;
        Trader trader = null;
        int id;
        do {
            System.out.println("Entrer votre Identifiant : ");
            id = sc.nextInt();
            trouve = false;
            for (Trader tr : t.getTraders()) {
                if (tr.getIdentifiant() == id) {
                    trader = tr;
                    trouve = true;
                    break;
                }
            }
            if (!trouve){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (!trouve);

        //affichage
        System.out.println("Valeur Totale : "+trader.getPortfolio().getValeurTotale()+" DH");
        System.out.println("Quantité Totale : "+trader.getPortfolio().getQuantité()+" Assets");
        System.out.println("Listes Assets : ");
        for (Asset a : trader.getPortfolio().getAssets()){
            System.out.println("Nom : "+a.getNom()+"Quantité : "+a.getnombreAsset());
        }
    }

    public void  affichageHistoriqueOpérations(TradingPlatform t){
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        boolean trouve = false;
        Trader trader = null;
        int id;
        do {
            System.out.println("Entrer votre Identifiant : ");
            id = sc.nextInt();
            trouve = false;
            for (Trader tr : t.getTraders()) {
                if (tr.getIdentifiant() == id) {
                    trader = tr;
                    trouve = true;
                    break;
                }
            }
            if (!trouve){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (!trouve);

        // affichage transaction
        for (Transaction tra : trader.getTransactions()){
            System.out.println("Date : "+tra.getDate()+", Type d’opération : "+tra.getTypeOpération());
            System.out.println("Actif : "+tra.getActif().getNom());
            System.out.println("Quantité : "+tra.getQuantité());
            System.out.println("--------------------------------------");
        }
    }
}
