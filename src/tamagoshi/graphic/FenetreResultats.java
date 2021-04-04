package tamagoshi.graphic;

import tamagoshi.service.LanguageService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ResourceBundle;

public class FenetreResultats extends FenetreAbstract {

    public FenetreResultats(String resultats)
    {

        LanguageService languageService = LanguageService.getInstance();
        ResourceBundle messages = languageService.getMessages();

        setTitle(messages.getString("fenetreResults"));
        setSize(500,500);
        setLocationRelativeTo(null);

        JTextArea resultsTextArea = new JTextArea(5, 15);
        resultsTextArea.setText(resultats);

        resultsTextArea.setEditable(false);
        resultsTextArea.setOpaque(false);


        JPanel resultsPanel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(resultsPanel, BoxLayout.Y_AXIS);

        resultsPanel.setLayout(boxlayout);

        resultsPanel.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));

        resultsTextArea.setLineWrap(true);
        resultsTextArea.setWrapStyleWord(true);

        resultsPanel.add(resultsTextArea);
        resultsPanel.setBackground(getBgColor());

        add(resultsPanel);

        setVisible(true);
        setAlwaysOnTop (true);

    }




}
