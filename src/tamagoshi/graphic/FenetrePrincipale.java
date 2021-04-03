package tamagoshi.graphic;

import tamagoshi.controller.OptionController;
import tamagoshi.controller.TamaGameController;
import tamagoshi.service.LanguageService;
import tamagoshi.service.LanguageServiceObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FenetrePrincipale extends FenetreAbstract implements LanguageServiceObserver {

    private JButton btnNewGame;
    private JButton btnOptions;
    private JButton btnHelp;
    private JButton btnExit;
    private JLabel welcomeMessage;


    private OptionController optionController;
    private TamaGameController tamaGameController;

    private ResourceBundle messages;

    private static Logger logger = Logger.getLogger("tamagoshi.graphic.FenetrePrincipale");


    public FenetrePrincipale(OptionController optionController, TamaGameController tamaGameController)
    {
        this.optionController = optionController;
        this.tamaGameController = tamaGameController;
        LanguageService languageService = LanguageService.getInstance();

        String languageProperty = optionController.loadDefaultIfCurrentPropertyNotExists("language");
        String countryProperty = optionController.loadDefaultIfCurrentPropertyNotExists("country");

        languageService.setCurrentLocale(languageProperty, countryProperty);
        messages = languageService.getMessages();


        setTitle(messages.getString("fenetreMenuTitle"));
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initJLabel();
        initJButton();
        appendElements();
        setAlwaysOnTop (true);

        setVisible(true);

        languageService.attacher(this);
    }

    @Override
    protected void setButtonBehaviour(JButton button)
    {
        super.setButtonBehaviour(button);


        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getSource().equals(btnNewGame)) {
                    logger.log(Level.INFO, messages.getString("newGame"));

                    tamaGameController.launchGame();
                }

                if (e.getSource().equals(btnOptions)) {
                    logger.info( messages.getString("option"));

                    optionController.createFenetreOption();
                }

                if (e.getSource().equals(btnHelp)) {
                    logger.info(messages.getString("help"));

                    new FenetreAide();

                }

                if (e.getSource().equals(btnExit)) {
                    logger.info(messages.getString("exit"));

                    System.exit(0);
                }

            }
        });
    }

    @Override
    protected void initJButton()
    {
        btnNewGame = new JButton(messages.getString("btnNewGame"));
        btnOptions = new JButton(messages.getString("btnOptions"));
        btnHelp = new JButton(messages.getString("btnHelp"));
        btnExit = new JButton(messages.getString("btnExit"));

        setButtonBehaviour(btnNewGame);
        setButtonBehaviour(btnOptions);
        setButtonBehaviour(btnHelp);
        setButtonBehaviour(btnExit);
    }

    @Override
    protected void initJLabel()
    {
        welcomeMessage = new JLabel(messages.getString("welcomeMessage"));
        welcomeMessage.setFont(welcomeMessage.getFont().deriveFont(25f));
    }

    @Override
    protected void appendElements()
    {
        Container contentPane = getContentPane();

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(getBgColor());

        BoxLayout boxlayout = new BoxLayout(menuPanel, BoxLayout.Y_AXIS);

        menuPanel.setLayout(boxlayout);

        menuPanel.setBorder(new EmptyBorder(new Insets(100, 150, 100, 150)));
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(welcomeMessage);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        menuPanel.add(btnNewGame);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        menuPanel.add(btnOptions);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        menuPanel.add(btnHelp);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        menuPanel.add(btnExit);


        contentPane.add(menuPanel);

    }

    @Override
    public void mettreAJour()
    {
        messages =  LanguageService.getInstance().getMessages();
        resetJButtonTitle();
    }


    private void resetJButtonTitle()
    {
        btnNewGame.setText(messages.getString("btnNewGame"));
        btnOptions.setText(messages.getString("btnOptions"));
        btnHelp.setText(messages.getString("btnHelp"));
        btnExit.setText(messages.getString("btnExit"));
    }


}
