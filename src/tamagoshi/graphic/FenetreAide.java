package tamagoshi.graphic;

import tamagoshi.service.LanguageService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ResourceBundle;

public class FenetreAide extends FenetreAbstract {

    public FenetreAide()
    {

        LanguageService languageService = LanguageService.getInstance();
        ResourceBundle messages = languageService.getMessages();

        setTitle(messages.getString("help"));
        setSize(500,500);
        setLocationRelativeTo(null);

        JTextArea helpTextArea = new JTextArea(5, 20);
        helpTextArea.setText(messages.getString("helpText"));

        JTextArea creditTextArea = new JTextArea(2, 20);
        creditTextArea.setText("Icons made by Freepik - www.flaticon.com");

        helpTextArea.setEditable(false);
        helpTextArea.setOpaque(false);

        creditTextArea.setEditable(false);
        creditTextArea.setOpaque(false);

        JPanel helpPanel = new JPanel();

        BoxLayout boxlayout = new BoxLayout(helpPanel, BoxLayout.Y_AXIS);

        helpPanel.setLayout(boxlayout);

        helpPanel.setBorder(new EmptyBorder(new Insets(150, 100, 100, 150)));

        helpTextArea.setLineWrap(true);
        helpTextArea.setWrapStyleWord(true);
        creditTextArea.setLineWrap(true);
        creditTextArea.setWrapStyleWord(true);

        helpPanel.add(helpTextArea);
        helpPanel.add(creditTextArea);
        helpPanel.setBackground(getBgColor());
        creditTextArea.setBackground(getBgColor());

        add(helpPanel);

        setVisible(true);
        setAlwaysOnTop (true);

    }




}
