import java.util.ArrayList;
import java.util.List;

public class Portfolio<V,Q,A> {
    private V valeurTotale;
    private int quantité;
    private List<A> assets;

    public Portfolio(){
        this.valeurTotale = null;
        this.quantité = 0;
        this.assets = new ArrayList<>();
    }

    public V getValeurTotale() {
        return valeurTotale;
    }

    public void setValeurTotale(V valeur) {
        valeurTotale = valeur;
    }

    public int getQuantité() {
        return quantité;
    }

    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }

    public List<A> getAssets() {
        return assets;
    }

    public void setAssets(List<A> assets) {
        this.assets = assets;
    }

}
