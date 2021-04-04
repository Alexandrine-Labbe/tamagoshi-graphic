package tamagoshi.controller;

import tamagoshi.graphic.FenetreOption;
import tamagoshi.service.LanguageService;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class OptionController{

    private FenetreOption fenetreOption;
    private String propertiesFileLocation;
    private HashMap<String, String> languageByCountry;
    private Properties props;

    public OptionController()
    {
        this.propertiesFileLocation = "/config.properties";
        this.languageByCountry = new HashMap<>();
        languageByCountry.put("fr", "FR");
        languageByCountry.put("en", "US");
        this.props = new Properties();
    }

    /**
     *
     * @param propertyName nom de la propriété à charger
     * @param defaultProperty valeur pas défaut si non trouvée
     * @return la propriété demandée sinon sa valeur par défaut
     */
    public String loadProperties(String propertyName, String defaultProperty)
    {
        try {


            InputStream in = this.getClass().getResourceAsStream(propertiesFileLocation);
            props.load(in);

            String propertyValue =  props.getProperty(propertyName);

            if (propertyValue == null) {
                 return defaultProperty;
            }

            return propertyValue;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return defaultProperty;
    }

    /**
     *
     * @param propertyName nom de la propriétée
     * @return la valeur courante de la propriétée sinon sa valeur par défaut
     */
    public String loadDefaultIfCurrentPropertyNotExists(String propertyName)
    {
        String defaultProperty = loadProperties("default." + propertyName, null);

        if (defaultProperty == null) {
            return null;
        }

        return loadProperties("current." + propertyName, defaultProperty);
    }

    /**
     * Ouvre une nouvelle fenêtre option si aucune n'est déjà ouverte
     */
    public void createFenetreOption()
    {
        if (fenetreOption == null) {
            fenetreOption = new FenetreOption(this);
        }
    }

    /**
     * ferme la fenetre option
     */
    public void deleteFenetreOption()
    {
        fenetreOption.dispose();
        fenetreOption = null;
    }

    /**
     * Enregistre les propriétés courrantes
     *
     * @param language nouvelle langue
     * @param difficulty nouveau niveau de difficulté
     * @param hardMode niveau difficile activé ou non
     */
    public void saveProperties(String language, String difficulty, String hardMode)
    {

        props.setProperty("current.difficulty", difficulty);
        props.setProperty("current.language", language);
        props.setProperty("current.country", languageByCountry.get(language));
        props.setProperty("current.hardMode", hardMode);

        LanguageService languageService = LanguageService.getInstance();

        languageService.setCurrentLocale(language, languageByCountry.get(language));

        try {
            OutputStream out = new FileOutputStream(propertiesFileLocation);
            props.store(out, null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
