package tamagoshi;

import tamagoshi.controller.OptionController;
import tamagoshi.controller.TamaGameController;
import tamagoshi.graphic.FenetrePrincipale;

public class Main {

    public static void main(String[] args) {
        new FenetrePrincipale(new OptionController(), new TamaGameController());

    }
}
