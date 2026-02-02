import java.util.ArrayList;
import java.util.List;

public class Portfolio<V,Q,A> {
    private V valeurTotale;
    private Q quantité;
    private List<A> assets;

    public Portfolio(){
        this.valeurTotale = null;
        this.quantité = null;
        this.assets = new ArrayList<>();
    }

    public V getValeurTotale() {
        return valeurTotale;
    }

    public void setValeurTotale(V valeur) {
        valeurTotale = valeur;
    }

    public Q getQuantité() {
        return quantité;
    }

    public void setQuantité(Q quantité) {
        this.quantité = quantité;
    }

    public List<A> getAssets() {
        return assets;
    }

    public void setAssets(List<A> assets) {
        this.assets = assets;
    }

}
