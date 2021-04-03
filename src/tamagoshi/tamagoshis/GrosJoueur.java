package tamagoshi.tamagoshis;

public class GrosJoueur extends Tamagoshi {
    public GrosJoueur(String name) {
        super(name);
        this.type = "player";

    }

    @Override
    public void consommeFun() {
        this.setFun(this.getFun() - 1);
        super.consommeFun();

    }

}
