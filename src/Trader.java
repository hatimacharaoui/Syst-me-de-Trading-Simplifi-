import com.sun.jdi.PrimitiveValue;
import javax.sound.sampled.Port;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public void transactionsUnTrader(TradingPlatform t){
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        Trader trader;
        do {
            System.out.println("Entrer votre Identifiant : ");
            int id = sc.nextInt();
            trader = t.getTraders().stream()
                    .filter(tr -> tr.getIdentifiant() == id).findFirst().orElse(null);
            if (trader == null){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (trader == null);

        // affichage transaction
        System.out.println("Historique des transactions : ");
        trader.getTransactions().stream().forEach( tra -> {
            System.out.println("Date : "+tra.getDate()+", Type d’opération : "+tra.getTypeOpération());
            System.out.println("Actif : "+tra.getActif().getNom());
            System.out.println("Quantité : "+tra.getQuantité());
            System.out.println("--------------------------------------");
        });

    }

    public void transactionsUnTraderParType(TradingPlatform t){
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        Trader trader = null;
        do {
            System.out.println("Entrer votre Identifiant : ");
            int id = sc.nextInt();

            trader = t.getTraders().stream()
                    .filter(tr -> tr.getIdentifiant() == id).findFirst().orElse(null);

            if (trader == null){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (trader == null);

        System.out.println("Entrer le type de transaction BUY/SELL : ");
        String type  = sc.next();
        System.out.println("Entrer le nom de l'actif financier (ex : BTC, EUR/USD): ");
        String nom = sc.next();
        System.out.println("Saisir la date de début (yyyy-MM-dd) : ");
        String dateDebut = sc.next();
        LocalDate debut = LocalDate.parse(dateDebut);

        System.out.println("Saisir la date de fin (yyyy-MM-dd): ");
        String dateFin = sc.next();
        LocalDate fin = LocalDate.parse(dateFin);

        List<Transaction> transaction = trader.getTransactions().stream().
                filter(tr -> tr.getTypeOpération().equalsIgnoreCase(type)).filter( tr -> tr.getActif().getNom().equalsIgnoreCase(nom))
                .filter(tr -> !tr.getDate().isAfter(fin) && !tr.getDate().isBefore(debut))
                .toList();

        // affichage transaction
        System.out.println("Historique des transactions : ");
        transaction.stream().forEach( tra -> {
            System.out.println("Date : "+tra.getDate()+", Type d’opération : "+tra.getTypeOpération());
            System.out.println("Actif : "+tra.getActif().getNom());
            System.out.println("Quantité : "+tra.getQuantité());
            System.out.println("--------------------------------------");
        });
    }

    public void transactionsParDateMontant(TradingPlatform t){
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        Trader trader = null;
        do {
            System.out.println("Entrer votre Identifiant : ");
            int id = sc.nextInt();

            trader = t.getTraders().stream()
                    .filter(tr -> tr.getIdentifiant() == id).findFirst().orElse(null);

            if (trader == null){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (trader == null);

        List<Transaction> transactions1 = trader.getTransactions()
                .stream().sorted((tr1,tr2) -> tr1.getDate().compareTo(tr2.getDate())).toList();

        // affichage par date
        System.out.println("Les transactions par date : ");
        transactions1.stream().forEach( tra -> {
            System.out.println("   Date : "+tra.getDate()+", Type d’opération : "+tra.getTypeOpération());
            System.out.println("   Actif : "+tra.getActif().getNom());
            System.out.println("   Quantité : "+tra.getQuantité());
            System.out.println("--------------------------------------");
        });

        List<Transaction> transactions2 = trader.getTransactions()
                .stream().sorted((tr1,tr2) -> Double.compare((tr1.getPrix()*tr1.getQuantité()),(tr2.getPrix())*tr2.getQuantité())).toList();

        // affichage par montant
        System.out.println("Les transactions par montant : ");
        transactions2.stream().forEach( tra -> {
            System.out.println("   Date : "+tra.getDate()+", Type d’opération : "+tra.getTypeOpération());
            System.out.println("   Actif : "+tra.getActif().getNom());
            System.out.println("   Quantité : "+tra.getQuantité());
            System.out.println("--------------------------------------");
        });
    }

    public void volumeTotalEchangeParActif(TradingPlatform t) {
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        Trader trader;
        do {
            System.out.println("Entrer votre Identifiant : ");
            int id = sc.nextInt();

            trader = t.getTraders().stream()
                    .filter(tr -> tr.getIdentifiant() == id).findFirst().orElse(null);

            if (trader == null) {
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (trader == null);

        // Volume total échangé
        Trader finalTrader = trader;
        Map<Asset, Double> Total = trader.getTransactions().stream().collect(Collectors.groupingBy(tr -> tr.getActif(), Collectors.summingDouble(tr -> tr.getQuantité())));

        Total.forEach((asset, volume) -> {
            System.out.println("Actif : " + asset.getNom() + " , Volume total échangé : " + volume);
        });
    }

    public void MontantTotalEchangeParActif(TradingPlatform t){
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        Trader trader;
        do {
            System.out.println("Entrer votre Identifiant : ");
            int id = sc.nextInt();

            trader = t.getTraders().stream()
                    .filter(tr -> tr.getIdentifiant() == id).findFirst().orElse(null);

            if (trader == null){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (trader == null);

        // Montant total Achat/Vente
        System.out.println("Le montant total des achats et des ventes : ");
        double montantTotalAchat = trader.getTransactions().stream()
                .filter(tr -> tr.getTypeOpération() == "Achat")
                .mapToDouble(tr -> tr.getPrix()*tr.getQuantité())
                .sum();
        double montantTotalVente = trader.getTransactions().stream()
                .filter(tr -> tr.getTypeOpération() == "Vente")
                .mapToDouble(tr -> tr.getPrix() * tr.getQuantité())
                .sum();

        System.out.println("Montant total Achat : "+montantTotalAchat);
        System.out.println("Montant total vente : "+montantTotalVente);
    }

    public void volumeTotalEchangéParTrader(TradingPlatform t){
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        Trader trader;
        do {
            System.out.println("Entrer votre Identifiant : ");
            int id = sc.nextInt();

            trader = t.getTraders().stream()
                    .filter(tr -> tr.getIdentifiant() == id).findFirst().orElse(null);

            if (trader == null){
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (trader == null);

//        int total = trader.getTransactions().stream()
//                .mapToInt(tr -> tr.getQuantité())
//                .sum();

        Map<String,Double> totale = t.getTraders().stream().collect(Collectors.toMap(tr -> tr.getNom(),
                tr -> tr.getTransactions().stream().mapToDouble(tra -> tra.getQuantité()).sum()));

        System.out.println("trader : "+trader.getNom()+" , volume total : "+totale);
    }

    public void nombreTotalOrdresPassés(TradingPlatform t) {
        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        Trader trader;
        do {
            System.out.println("Entrer votre Identifiant : ");
            int id = sc.nextInt();

            trader = t.getTraders().stream()
                    .filter(tr -> tr.getIdentifiant() == id).findFirst().orElse(null);

            if (trader == null) {
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
            }
        } while (trader == null);

        long ordreTotal = trader.getTransactions().stream().count();
        System.out.println("trader : "+trader.getNom()+" , Ordres totales : "+ordreTotal);
    }

    public void topNombreTraders(TradingPlatform t) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrer le top nombre de traders ");
        int nombre = sc.nextInt();
        List<Trader> traders = t.getTraders().stream().sorted((t1,t2) -> Integer.compare(
                t2.getTransactions().stream().mapToInt(tr -> tr.getQuantité()).sum(),
                t1.getTransactions().stream().mapToInt(tr -> tr.getQuantité()).sum()
        )).limit(nombre).toList();

        traders.stream().forEach(trader -> {

        });
    }

    public void volumeTotalEchangéParInstrument(TradingPlatform t){

        Map<String,Double> volume = t.getTraders().stream().flatMap(tr -> tr.getTransactions()
                .stream()).collect(Collectors.groupingBy(tra -> tra.getActif().getNom(), Collectors.summingDouble(a -> a.getQuantité()*a.getPrix())));

        volume.forEach((nom,volum)-> {
            System.out.println("Nom : "+nom+", ");
        });
    }

    public void IdentificationInstrumentPlusEchangé(TradingPlatform t){
        Asset assetPlusEchanger = t.getAssets().stream().max((a1, a2) -> Integer.compare(
                t.getTraders().stream().flatMap(trader -> trader.getTransactions().stream().filter(transaction -> transaction.getActif().getCode() == a1.getCode())).mapToInt(tr ->tr.getQuantité()).sum(),
                t.getTraders().stream().flatMap(trader -> trader.getTransactions().stream().filter(transaction -> transaction.getActif().getCode() == a2.getCode())).mapToInt(tr -> tr.getQuantité()).sum()
        )).orElse(null);
    }

    public void CalculMontantTotalBUYetSELL(TradingPlatform t){

        Scanner sc = new Scanner(System.in);
        // verifier l'identifiant
        Trader trader;
            System.out.println("Entrer votre Identifiant : ");
            int id = sc.nextInt();

            trader = t.getTraders().stream()
                    .filter(tr -> tr.getIdentifiant() == id).findFirst().orElse(null);

            if (trader == null) {
                System.out.println("Aucun Identifiant ne correspond à ce numéro.");
                return;
            }
            //return achat et vente du trader
         Double TotalAchat = trader.getTransactions().stream().filter(tr->tr.getTypeOpération().equals("Achat")).mapToDouble(tr->tr.getQuantité()).sum();
            Double TotalVente = trader.getTransactions().stream().filter(tr->tr.getTypeOpération().equals("Vente")).mapToDouble(tr->tr.getQuantité()).sum();

        System.out.println("Nom : "+trader.getNom()+", TotalAchat : "+TotalAchat+", TotalVente : "+TotalVente);

            // return List : nom, total
        Map<String,Double> TotalBuy = t.getTraders().stream().collect(Collectors.toMap(tr-> tr.getNom(),tr->tr.getTransactions().stream().filter(tra->tra.getTypeOpération().equals("Achat")).mapToDouble(tra-> tra.getQuantité()).sum()));
        Map<String,Double> TotalSell = t.getTraders().stream().collect(Collectors.toMap(tr->tr.getNom(),tr->tr.getTransactions().stream().filter(tra->tra.getTypeOpération().equals("Vente")).mapToDouble(tra->tra.getQuantité()).sum()));

        System.out.println("List des Achat par trader : ");
        TotalBuy.forEach((nom,Totalbay)->{
            System.out.println("Nom : "+nom+", Total Achat : "+Totalbay);
        });

        System.out.println("List des Achat par trader : ");
        TotalSell.forEach((nom,totalsell)->{
            System.out.println("Nom : "+nom+", Total Achat : "+totalsell);
        });

    }

}
