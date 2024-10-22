public class Pay {
    private int id;
    private String nom;
    private String capitale;
    private int pupilation;
    private String cantinent;

    public Pay(String nom, String capitale, int pupilation, String cantinent) {
        super();
        this.nom = nom;
        this.capitale = capitale;
        this.pupilation = pupilation;
        this.cantinent = cantinent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCapitale() {
        return capitale;
    }

    public void setCapitale(String capitale) {
        this.capitale = capitale;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPupilation() {
        return pupilation;
    }
    public void setPupilation(int pupilation) {
        this.pupilation = pupilation;
    }

    public String getCantinent() {
        return cantinent;
    }
    public void setCantinent(String cantinent) {
        this.cantinent = cantinent;
    }

    @Override
    public String toString() {
        return "Pay{" +
                "nom='" + nom + '\'' +
                ", capitale='" + capitale + '\'' +
                ", pupilation=" + pupilation +
                ", cantinent='" + cantinent + '\'' +
                '}';
    }
}
