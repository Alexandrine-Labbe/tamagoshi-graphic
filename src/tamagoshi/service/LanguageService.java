package tamagoshi.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageService {

    private String language;
    private String country;
    private Locale currentLocale;
    private ResourceBundle messages;
    private  static LanguageService instance;
    private List<LanguageServiceObserver> observerList;


    private LanguageService() {
        this.language = "fr";
        this.country = "FR";
        this.observerList = new ArrayList<>();

        currentLocale = new Locale(this.language, this.country);

        messages = ResourceBundle.getBundle("MessagesBundle", this.currentLocale);
    }

    /**
     * Change la langue de l'interface
     *
     * @param language langue désirée
     * @param country pays associé
     */
    public void setCurrentLocale(String language, String country)
    {
        this.language = language;
        this.country = country;

        this.currentLocale = new Locale(this.language, this.country);

        messages = ResourceBundle.getBundle("MessagesBundle", this.currentLocale);

        avertir();

    }

    /**
     *
     * @return le ResourceBundle de la langue locale actuelle de l'utilisateur
     */
    public ResourceBundle getMessages()
    {
        return messages;
    }

    /**
     *
     * @return l'instance de ce service
     */
    public static synchronized LanguageService getInstance()
    {
        if (instance == null) {
            instance = new LanguageService();
        }

        return instance;
    }

    /**
     * ajoute un élément qui observe les changements de cette instance
     * @param observer instance qui surveille les changements d'états
     */
    public void attacher(LanguageServiceObserver observer)
    {
        observerList.add(observer);
    }

    /**
     * supprime un élément qui observe les changements de cette instance
     * @param observer instance qui surveille les changements d'états
     */
    public void enlever(LanguageServiceObserver observer)
    {
        observerList.remove(observer);
    }

    /**
     * Préviens la liste des élément observateur d'une mise à jour de l'instance
     */
    private void avertir()
    {
        for (LanguageServiceObserver o : observerList) {
            o.mettreAJour();
        }
    }
}
