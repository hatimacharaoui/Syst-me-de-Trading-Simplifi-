public class Person {
    private int Identifiant;
    private String Nom;
    public Person(int Identifiant, String Nom) {
        this.Identifiant = Identifiant;
        this.Nom = Nom;
    }
    public int getIdentifiant(){
        return Identifiant;
    }
    public void setIdentifiant(int Identifiant){
        this.Identifiant = Identifiant;
    }
    public String getNom(){
        return Nom;
    }
    public void setNom(String Nom){
        this.Nom = Nom;
    }
}
