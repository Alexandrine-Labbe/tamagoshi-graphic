package tamagoshi.tamagoshis;

public class GrosMangeur extends Tamagoshi {
    public GrosMangeur(String name) {
        super(name);
        this.type = "eater";

    }

    @Override
    public void consommeEnergie() {
        this.setEnergy(this.getEnergy() - 1);

        super.consommeEnergie();

    }

}
