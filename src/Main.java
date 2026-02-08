import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TradingPlatform tradingPlatform = new TradingPlatform();
        Trader trader = new Trader();
        int choix = 0;
        do {

            System.out.println("=========  XTrade  =========");
            System.out.println("1. Ajouter un actif         2. Afficher les Actifs");
            System.out.println("3. Ajouter un trader        4. Consulter le portefeuille");
            System.out.println("5. Acheter un actif         6. Vendre un actif");
            System.out.println("7. Historique des transactions       ");
            System.out.println("8. Afficher toutes les transactions d’un trader donné");
            System.out.println("9. Filtrer les transactions par : type (BUY / SELL)");
            System.out.println("10. Trier les transactions par : date, montant ");
            System.out.println("11. Calculer : le volume total échangé par actif ");
            System.out.println("12. Calculer : le montant total des achats et des ventes ");
            System.out.println("13. Calcul du volume total échangé par trader ");
            System.out.println("14. Calcul du nombre total d’ordres passés ");
            System.out.println("15. Classement des traders par volume (top N traders) ");
            System.out.println("16. Calcul du volume total échangé par instrument financier ");
            System.out.println("17. Identification de l’instrument le plus échangé ");
            System.out.println("18. Calcul du montant total des BUY et des SELL séparément");
            System.out.println("0. Quitter");
            System.out.println("==============================");
            System.out.println("Entrer votre choix :");

            Scanner sc = new Scanner(System.in);
            choix = sc.nextInt();
            switch (choix) {
                case 1:
                    tradingPlatform.AjouterActif();
                    System.out.println("-----------------------------------");
                    break;
                case 2:
                    tradingPlatform.afficherListeActifs();
                    System.out.println("-----------------------------------");
                    break;
                case 3:
                    tradingPlatform.AjouterTrader();
                    System.out.println("-----------------------------------");
                    break;
                case 4:
                    trader.ConsulterPortefeuille(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 5:
                    trader.acheterActif(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 6:
                    trader.vendreActif(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 7:
                    trader.affichageHistoriqueOpérations(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 8:
                    trader.transactionsUnTrader(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 9:
                    trader.transactionsUnTraderParType(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 10:
                    trader.transactionsParDateMontant(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 11:
                    trader.volumeTotalEchangeParActif(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 12:
                    trader.MontantTotalEchangeParActif(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 13:
                    trader.volumeTotalEchangéParTrader(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 14:
                    trader.nombreTotalOrdresPassés(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 15:
                    trader.topNombreTraders(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 16:
                    trader.volumeTotalEchangéParInstrument(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 17:
                    trader.IdentificationInstrumentPlusEchangé(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
                case 18:
                    trader.CalculMontantTotalBUYetSELL(tradingPlatform);
                    System.out.println("-----------------------------------");
                    break;
            }


        } while (choix != 0);

    }
}
