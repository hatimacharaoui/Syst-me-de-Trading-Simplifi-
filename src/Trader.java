import com.sun.jdi.PrimitiveValue;
import javax.sound.sampled.Port;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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
        this.portfolio = new Portfolio<>();
        this.transactions = new ArrayList<>();
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
        Transaction tr = new Transaction("Achat",asset,nombre, asset.getPrixUnitaire());
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

        boolean trouve1 = false;

        for (int i = 0; i < trader.getPortfolio().getAssets().size(); i++) {
            Asset a = trader.getPortfolio().getAssets().get(i);

            if (a.getCode() == code) {
                trouve1 = true;
                // Vérifier la quantité
                if (nombre > a.getnombreAsset()) {
                    System.out.println("Quantité insuffisante pour la vente.");
                    return;
                }
                // Vendre l'actif
                a.vendreAsset(nombre);
                // Mettre à jour la quantité totale
                trader.getPortfolio().setQuantité(
                        trader.getPortfolio().getQuantité() - nombre
                );
                // Mettre à jour le solde
                trader.setSoldInitial(
                        trader.getSoldInitial() + (a.getPrixUnitaire() * nombre)
                );
                // Mettre à jour la valeur totale
                trader.getPortfolio().setValeurTotale(trader.getSoldInitial());
                // Supprimer l’actif si quantité = 0
                if (a.getnombreAsset() == 0) {
                    trader.getPortfolio().getAssets().remove(i);
                }
                // Historique
                trader.getTransactions().add(
                        new Transaction("Vente", a, nombre, a.getPrixUnitaire())
                );

                System.out.println("Vente réussie : " + nombre + " " + a.getNom());
                break;
            }
        }

        if (!trouve1) {
            System.out.println("Aucun actif trouvé pour ce code.");
        }
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
        System.out.println("Historique des transactions : ");
        for (Transaction tra : trader.getTransactions()){
            System.out.println("Date : "+tra.getDate()+", Type d’opération : "+tra.getTypeOpération());
            System.out.println("Actif : "+tra.getActif().getNom());
            System.out.println("Quantité : "+tra.getQuantité());
            System.out.println("--------------------------------------");
        }
    }
}
