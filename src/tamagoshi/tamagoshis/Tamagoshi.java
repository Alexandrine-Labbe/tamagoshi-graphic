package tamagoshi.tamagoshis;

import tamagoshi.service.LanguageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tamagoshi {
    private int age;
    private int maxEnergy;
    private int maxFun;
    private String name;

    private static int lifeTime = 10;
    private int energy;
    private int fun;
    String type;
    private String reaction;

    private Random generator = new Random();

    private List<TamagoshiObserver> observerList;
    private ResourceBundle messages;
    private static Logger logger = Logger.getLogger("tamagoshi.tamagoshis.Tamagoshi");




    public Tamagoshi(String name) {
        messages = LanguageService.getInstance().getMessages();

        this.name = name;
        this.age = 0;
        this.type = "normal";
        this.reaction = "";

        this.maxEnergy = generator.nextInt(5) + 5;
        this.maxFun = generator.nextInt(5) + 5;
        this.energy = generator.nextInt(5) + 3;
        this.fun = generator.nextInt(5) + 3;
        this.observerList = new ArrayList<>();

    }


    /**
     * Fais parler le tamagoshi pour savoir son état, il peut avoir faim, s'enuyer ou etre mort
     *
     * @return l'état du tamagoshi
     */
    public String parle()
    {
        String etat = "";

        if(isDead()) {
            return messages.getString("isDead");
        } else {
            if (energy < 5)
                etat += messages.getString("isHungry");
            if (fun < 5) {
                etat += messages.getString("isBored");
            }

            if (etat.isEmpty()) {
                etat = messages.getString("isFine");
            }
        }

        return etat;
    }

    /**
     * Remonte l'énergie du tamagoshi. Met à jour ses réactions et trigger la mise à jour de la fenêtre tamagoshi
     *
     * @return si le tamagoshi a mangé ou non
     */
    public boolean mange()
    {
        if (this.energy < this.maxEnergy) {
            this.energy += generator.nextInt(3) + 1;

            if(this.energy > this.maxEnergy) {
                this.energy = this.maxEnergy;
            }

            logger.log(Level.INFO, name+" : " + messages.getString("wellFed"));

            reaction = messages.getString("wellFed");

            avertir();

            return true;

        } else {
            logger.info(name+" : " + messages.getString("notHungry"));

            reaction = messages.getString("notHungry");

            avertir();

            return false;

        }
    }

    /**
     * Remonte le fun du tamagoshi. Met à jour ses réactions et trigger la mise à jour de la fenêtre tamagoshi
     *
     * @return si le tamagoshi a joué ou non
     */
    public boolean joue()
    {
        if (fun < maxFun) {
            fun += generator.nextInt(3) + 1;

            if(fun > maxFun) {
                fun = maxFun;
            }

            reaction = messages.getString("isHavingFun");

            logger.info(name+" : " + messages.getString("isHavingFun"));

            avertir();

            return true;

        } else {
            reaction = messages.getString("isBusy");

            logger.info( name+" : " + messages.getString("isBusy"));

            avertir();

            return false;

        }


    }

    /**
     * @return si le tamagoshi est vivant
     */
    public boolean isDead()
    {
        return energy <= 0 || fun <= 0 || age == lifeTime;
    }


    /**
     * fait baisser l'énergie du tamagoshi
     * Trigger la mise à jour de la fenêtre tamagoshi
     */
    public void consommeEnergie()
    {
        this.energy --;
        if( energy < 0) {
            energy = 0;
        }
        avertir();

    }

    /**
     * fait baisser le fun du tamagoshi
     * Trigger la mise à jour de la fenêtre tamagoshi
     */
    public void consommeFun()
    {
        fun --;
        if( fun < 0) {
            fun = 0;
        }

        avertir();

    }

    @Override
    public String toString() {
        return "Tamagoshi{" +
                "age=" + age +
                ", maxEnergy=" + maxEnergy +
                ", energy=" + energy +
                ", name='" + name + '\'' +
                ", generator=" + generator +
                '}';
    }

    public int getAge() {
        return age;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }

    public static int getLifeTime() {
        return lifeTime;
    }

    /**
     * Augmente l'age du tamagoshi
     */
    public void vieillit()
    {
        age += 1;
    }

    /**
     * @return string afin de signaler si le tamagoshi à survécu jusqu'à la fin de la partie
     */
    public String hasSurvived()
    {
        if(age == lifeTime) {
            return name + " " + messages.getString("type." +type) + " " + messages.getString("hasSurvived");
        } else {
            return name + " " + messages.getString("type." +type)  + " " + messages.getString("hasNotSurvived");
        }
    }


    void setEnergy(int energy) {
        this.energy = energy;
    }

    void setFun(int fun) {
        this.fun = fun;
    }

    public int getFun() {
        return fun;
    }

    public int getMaxFun() {
        return maxFun;
    }

    /**
     * Ajoute un element qui observe les changement du tamagoshi
     *
     * @param observer les fenetres qui observeront le tamagoshi
     */
    public void attacher(TamagoshiObserver observer)
    {
        observerList.add(observer);
    }

    /**
     * avertir les éléments de la liste d'observer que le tamagoshi a été modifié et lancer la mise a jour de ces éléments
     */
    void avertir()
    {
        for (TamagoshiObserver o : observerList) {
            o.mettreAJourTama();
        }
    }


    public String getReaction() {
        return reaction;
    }
}
