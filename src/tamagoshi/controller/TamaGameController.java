package tamagoshi.controller;

import tamagoshi.graphic.FenetreResultats;
import tamagoshi.graphic.FenetreTamagoshi;
import tamagoshi.service.LanguageService;
import tamagoshi.tamagoshis.GrosJoueur;
import tamagoshi.tamagoshis.GrosMangeur;
import tamagoshi.tamagoshis.Tamagoshi;

import java.awt.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class TamaGameController {

    private OptionController optionController;
    private ArrayList<FenetreTamagoshi> starters, alive;
    private int xPositionLast, yPositionLast;
    private int windowWidth = 300;
    private int windowHeight = 300;
    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private int nbActionPerformed = 0;
    private ResourceBundle messages;
    private static Logger logger = Logger.getLogger("tamagoshi.controller.TamaGameController");

    public TamaGameController()
    {
        this.optionController = new OptionController();
        starters = new ArrayList<>();
        this.xPositionLast = 0;
        this.yPositionLast = 0;

        messages = LanguageService.getInstance().getMessages();

    }

    /**
     * Lance une nouvelle partie
     */
    public void launchGame()
    {
        logger.info(messages.getString("newGame"));

        closeAllFenetreTama();
        starters.clear();

        int nbTamagoshi = Integer.parseInt(optionController.loadDefaultIfCurrentPropertyNotExists("difficulty"));
        starters = new ArrayList<>();
        this.xPositionLast = 0;
        this.yPositionLast = 0;

        for (int i = 0;i < nbTamagoshi; i++){

            newTamagoshi(i);

        }
        alive = new ArrayList<>(starters);

        newCycle();

    }

    /**
     * Créé un nouveau tamagoshi
     * @param n numéro de création
     */
    private void newTamagoshi(int n)
    {
        Tamagoshi tamagoshi;

        String name = optionController.loadProperties("generatedName." + n, "Cuddle");

        if (Math.random() < .5) {
            tamagoshi = new GrosJoueur(name);

        } else {
            tamagoshi = new GrosMangeur(name);
        }

        if (xPositionLast + windowWidth > screen.width) {
            xPositionLast = 0;
            yPositionLast += windowHeight + 30;
        }

        FenetreTamagoshi fenetreTamagoshi = new FenetreTamagoshi(tamagoshi, xPositionLast, yPositionLast, this);


        starters.add(fenetreTamagoshi);

        xPositionLast = xPositionLast  + windowWidth + 30;

    }

    /**
     * lance un nouveau cycle dans la partie
     */
    private void newCycle()
    {
        logger.info(messages.getString("cycleStart"));

        this.nbActionPerformed = 0;

        for (FenetreTamagoshi f : alive) {
            Tamagoshi tamagoshi = f.getTamagoshi();
            tamagoshi.consommeFun();
            tamagoshi.consommeEnergie();
            tamagoshi.vieillit();
        }

        alive.removeIf(f -> f.getTamagoshi().isDead());


        if (alive.isEmpty()) {
            logger.info(messages.getString("endGame"));

            resultat();

            closeAllFenetreTama();
        }
        for (FenetreTamagoshi f : alive) {
            f.enableButtons();
        }

    }

    /**
     * @return score de la partie
     */
    private int score()
    {
        int score=0;
        for (FenetreTamagoshi t : starters)
            score += t.getTamagoshi().getAge();
        return score * 100 / (Tamagoshi.getLifeTime() * starters.size());
    }

    /**
     * Ouvre la fenêtre de résultats
     */
    private void resultat()
    {

        String result = "";

        for (FenetreTamagoshi t : starters){
            result = result + t.getTamagoshi().hasSurvived() + "\n";
        }

        result = result + "\n" + messages.getString("total") + " : " + score() + "%";

        new FenetreResultats(result);
    }

    /**
     * enregistre une action au cours d'un cycle. Si deux actions ont été réalsiées, lancer un nouveau cycle
     * @param button sur lequel l'action a été faite
     */
    public void actionPerformed(String button)
    {

        this.nbActionPerformed++;

        for (FenetreTamagoshi f : alive) {
            f.disableButton(button);
        }

        if (this.nbActionPerformed == 2) {
            newCycle();
        }

    }

    /**
     * Ferme toutes les fenêtres tamagoshi de la partie
     */
    private void closeAllFenetreTama()
    {
        for (FenetreTamagoshi f : starters) {
            f.dispose();
        }
    }

    /**
     * @return si la partie est en mode facile ou difficile
     */
    public boolean isHardMode()
    {
        String hardModeProperty = optionController.loadDefaultIfCurrentPropertyNotExists("hardMode");

        return hardModeProperty.equals("true");
    }

}
