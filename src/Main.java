import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TradingPlatform tradingPlatform = new TradingPlatform();
        Trader trader = new Trader();
        int choix = 0;
        do {
            try {

                System.out.println("=========  XTrade  =========");
                System.out.println("1. Ajouter un actif");
                System.out.println("2. Afficher les Actifs");
                System.out.println("3. Ajouter un trader");
                System.out.println("4. Consulter le portefeuille");
                System.out.println("5. Acheter un actif");
                System.out.println("6. Vendre un actif");
                System.out.println("7. Historique des transactions");
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
                }
            } catch (Exception e) {
                System.out.println("\nVeuillez entrer un nombre valide.");
                choix = -1; }

        } while (choix != 0);

    }
}