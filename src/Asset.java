public class Asset {
    private String Nom;
    private int code;
    private double prixUnitaire;
    private String typeActif;
    private int nombreAsset;

    public Asset( String Nom, int code, double prixUnitaire, String typeActif){
        this.Nom = Nom;
        this.code = code;
        this.prixUnitaire = prixUnitaire;
        this.typeActif = typeActif;
    }
    public Asset( String Nom, int code, double prixUnitaire, String typeActif, int nombreAsset){
        this.Nom = Nom;
        this.code = code;
        this.prixUnitaire = prixUnitaire;
        this.typeActif = typeActif;
        this.nombreAsset = nombreAsset;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getTypeActif() {
        return typeActif;
    }

    public void setTypeActif(String typeActif) {
        this.typeActif = typeActif;
    }

    public int getnombreAsset() {
        return nombreAsset;
    }

    public void addnombreAsset(int nombreAsset) {
        this.nombreAsset += nombreAsset;
    }
    public void vendreAsset(int nombreAsset) {
        this.nombreAsset -= nombreAsset;
    }

    public void afficherAsset(){
        System.out.println("Nom : "+getNom());
        System.out.println("   Code : "+getCode());
        System.out.println("   Prix Unitaire : "+getPrixUnitaire());
        System.out.println("   Type d'Actif : "+getTypeActif());
        System.out.println("-----------------------------------");
    }
}
