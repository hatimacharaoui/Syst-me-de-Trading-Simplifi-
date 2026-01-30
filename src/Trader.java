import com.sun.jdi.PrimitiveValue;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trader extends Person{
    private double soldInitial;
    private Portfolio<Double, Integer,Asset> portfolio;

    public Trader(int Identifiant, String Nom, double soldInitial, Portfolio<Double, Integer,Asset> portfolio){
        super(Identifiant,Nom);
        this.soldInitial = soldInitial;
        this.portfolio = portfolio;
    }
    public Trader(int Identifiant, String Nom){
        super(Identifiant,Nom);
        this.soldInitial = soldInitial;
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

    public void acheterActif(){
        Scanner sc = new Scanner(System.in);
        TradingPlatform t = new TradingPlatform();
        // verifier l'identifiant
        int Identifiant = 0;
        int id;
        boolean trouve = false;
        Trader trader = null;
        do {
            System.out.println("Entrer votre Identifiant : ");
            id = sc.nextInt();
            for (Trader tr : t.getTraders()) {
                if (tr.getIdentifiant() == id) {
                    Identifiant = id;
                    trader = tr;
                    trouve = true;

                }
            }
            if (!trouve){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (id != Identifiant);
        // liste Assets
        System.out.println("Liste des Actifs :");
        for (Asset a : getPortfolio().getAssets()){
            System.out.println("Stock : ");
            if (a.getTypeActif() == "Stock"){
                System.out.println("   "+a.getNom()+", code : "+a.getCode()+", Prix Unitaire : "+a.getPrixUnitaire());
            }
            System.out.println("Crypto Currency : ");
            if (a.getTypeActif() == "Crypto Currency"){
                System.out.println("   "+a.getNom()+", code : "+a.getCode()+", Prix Unitaire : "+a.getPrixUnitaire());
            }
        }

        System.out.println("Entrez le code d’actif:");
        int code = sc.nextInt();
        System.out.println("Entrez le nombre d’unités à acheter:");
        int nombre = sc.nextInt();
        Asset asset = null;
        for (Asset a : t.getAssets()){
            if (code == a.getCode()) {
                if (trader.soldInitial > (a.getPrixUnitaire() * nombre)){
                    asset = a;
                } else {
                    System.out.println("Solde insuffisant pour acheter cet actif.");
                    return;
                }
            } else {
                System.out.println("Aucun actif trouvé pour ce code.");
                return;
            }
        }
        //quantité
        int quantité = trader.portfolio.getQuantité() + nombre;
        trader.portfolio.setQuantité(quantité);
        // solde
        trader.soldInitial = trader.soldInitial - (asset.getPrixUnitaire() * nombre);
        //Assets
        for (Asset a : trader.portfolio.getAssets()){
            if (a.getNom() == asset.getNom()){
                a.setnombreAsset(nombre);
                break;
            } else {
                Asset asset1 = new Asset(asset.getNom(),asset.getCode(),asset.getPrixUnitaire(),asset.getTypeActif(),0);
                asset1.setnombreAsset(nombre);
                trader.portfolio.getAssets().add(asset1);
                break;
            }
        }

        System.out.println("L'Actif : "+asset.getNom()+" a été achetée avec succès");
    };

    public void vendreActif() {
        Scanner sc = new Scanner(System.in);
        TradingPlatform t = new TradingPlatform();
        // verifier l'identifiant
        int Identifiant = 0;
        int id;
        boolean trouve = false;
        Trader trader = null;
        do {
            System.out.println("Entrer votre Identifiant : ");
            id = sc.nextInt();
            for (Trader tr : t.getTraders()) {
                if (tr.getIdentifiant() == id) {
                    Identifiant = id;
                    trader = tr;
                    trouve = true;
                }
            }
            if (!trouve) {
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (id != Identifiant);
        //aficher Liste Actif
        System.out.println("Liste des Actifs :");
        for (Asset a : trader.portfolio.getAssets()) {
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
        for (Asset a : trader.portfolio.getAssets()) {
            if (code == a.getCode()) {
                if (nombre <= a.getnombreAsset()) {
                    // set nombreAsset
                    a.vendreAsset(nombre);
                    // set ValeurTotal
                    double newValeurTotal = (trader.portfolio.getValeurTotale() - (a.getPrixUnitaire() * nombre)) + (assetg.getPrixUnitaire() * nombre);
                    trader.portfolio.setValeurTotale(newValeurTotal);
                    // set quantité
                    int quantité = trader.portfolio.getQuantité() - nombre;
                    trader.portfolio.setQuantité(quantité);
                    // suprimer if quantité asset = 0
                    if (a.getnombreAsset() == 0) {
                        t.getAssets().remove(a);
                    }
                } else {
                    System.out.println("Le nombre d’actifs à vendre est inférieur à celui que vous possédez.");
                    return;
                }
            } else {
                System.out.println("Aucun actif trouvé pour ce code.");
                return;
            }
        }
    }

    public void ConsulterPortefeuille(){
        Scanner sc = new Scanner(System.in);
        TradingPlatform t = new TradingPlatform();
        // verifier l'identifiant
        int Identifiant = 0;
        int id;
        boolean trouve = false;
        Trader trader = null;
        do {
            System.out.println("Entrer votre Identifiant : ");
            id = sc.nextInt();
            for (Trader tr : t.getTraders()) {
                if (tr.getIdentifiant() == id) {
                    Identifiant = id;
                    trader = tr;
                    trouve = true;
                }
            }
            if (!trouve) {
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (id != Identifiant);

        //affichage
        trader.portfolio.
    }
}
