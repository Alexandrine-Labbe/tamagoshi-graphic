package tamagoshi.graphic;

import tamagoshi.controller.OptionController;
import tamagoshi.service.LanguageService;
import tamagoshi.service.LanguageServiceObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;


public class FenetreOption extends FenetreAbstract implements LanguageServiceObserver {


    private JLabel welcomeMessage;
    private JLabel difficultyLabel;
    private JLabel languageLabel;
    private JLabel hardModeLabel;
    private JButton btnSave;
    private JPanel languagePanel;
    private JPanel hardModePanel;
    private JPanel difficultyPanel;
    private ButtonGroup hardModeButtonGroup;
    private ButtonGroup languageButtonGroup;
    private ButtonGroup difficultyButtonGroup;
    private ResourceBundle messages;

    private OptionController optionController;


    public FenetreOption(OptionController optionController)
    {
        this.optionController = optionController;

        LanguageService languageService = LanguageService.getInstance();
        languageService.attacher(this);

        messages = languageService.getMessages();

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                optionController.deleteFenetreOption();
            }
        });

        setTitle(messages.getString("fenetreOptionTitle"));
        setSize(500,500);
        setLocationRelativeTo(null);

        initJLabel();
        initJRadioButton();
        initJButton();
        appendElements();
        setAlwaysOnTop (true);

        setVisible(true);
    }

    @Override
    protected void setButtonBehaviour(JButton button)
    {
        super.setButtonBehaviour(button);

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getSource().equals(btnSave)) {
                    String language = languageButtonGroup.getSelection().getActionCommand();
                    String difficultyLevel = difficultyButtonGroup.getSelection().getActionCommand();
                    String hardMode = hardModeButtonGroup.getSelection().getActionCommand();

                    optionController.saveProperties(language, difficultyLevel, hardMode);
                    optionController.deleteFenetreOption();
                }
            }
        });
    }

    @Override
    protected void initJLabel()
    {
        welcomeMessage = new JLabel(messages.getString("welcomeMessage"));
        welcomeMessage.setFont(welcomeMessage.getFont().deriveFont(25f));

        languageLabel = new JLabel(messages.getString("languageLabel"));
        difficultyLabel = new JLabel(messages.getString("difficultyLabel"));
        hardModeLabel = new JLabel(messages.getString("hardModeLabel"));
    }

    @Override
    protected void initJButton()
    {
        btnSave = new JButton(messages.getString("btnSave"));

        setButtonBehaviour(btnSave);
    }

    @Override
    protected void initJRadioButton()
    {
        JRadioButton languageFR, languageUS;
        String languageProperty =  optionController.loadDefaultIfCurrentPropertyNotExists("language");

        languageFR = new JRadioButton(messages.getString("languageFR"));
        languageFR.setActionCommand("fr");

        languageUS = new JRadioButton(messages.getString("languageEN"));
        languageUS.setActionCommand("en");

        if (languageProperty.equals("fr")) {
            languageFR.setSelected(true);
        } else {
            languageUS.setSelected(true);
        }

        List<JRadioButton> listJRadioButtonLanguage = new ArrayList<>(
                List.of(languageFR,
                        languageUS));

        languagePanel = new JPanel();
        languageButtonGroup = new ButtonGroup();

        appendJRadioButtonToJPanel(languagePanel, languageLabel, listJRadioButtonLanguage, languageButtonGroup);

        JRadioButton level_3, level_4, level_5, level_6, level_7, level_8;

        level_3 = new JRadioButton("3");
        level_3.setActionCommand("3");

        level_4 = new JRadioButton("4");
        level_4.setActionCommand("4");

        level_5 = new JRadioButton("5");
        level_5.setActionCommand("5");

        level_6 = new JRadioButton("6");
        level_6.setActionCommand("6");

        level_7 = new JRadioButton("7");
        level_7.setActionCommand("7");

        level_8 = new JRadioButton("8");
        level_8.setActionCommand("8");

        Map<String, JRadioButton> listJRadioButtonDifficulty = new HashMap<>() {{
            put("3", level_3);
            put("4", level_4);
            put("5", level_5);
            put("6", level_6);
            put("7", level_7);
            put("8", level_8);
        }};

        String difficultyProperty =  optionController.loadDefaultIfCurrentPropertyNotExists("difficulty");
        JRadioButton selectedJRadioButtonIndex = listJRadioButtonDifficulty.get(difficultyProperty);

        selectedJRadioButtonIndex.setSelected(true);

        difficultyPanel = new JPanel();
        difficultyButtonGroup = new ButtonGroup();

        appendJRadioButtonToJPanel(difficultyPanel, difficultyLabel,  new ArrayList<>(listJRadioButtonDifficulty.values()), difficultyButtonGroup);


        String hardModeProperty =  optionController.loadDefaultIfCurrentPropertyNotExists("hardMode");

        JRadioButton hardMode, easyMode;

        hardMode = new JRadioButton(messages.getString("activate"));
        hardMode.setActionCommand("true");

        easyMode = new JRadioButton(messages.getString("deactivate"));
        easyMode.setActionCommand("false");

        if (hardModeProperty.equals("true")) {
            hardMode.setSelected(true);
        } else {
            easyMode.setSelected(true);
        }


        List<JRadioButton> listJRadioButtonHardMode = new ArrayList<>(
                List.of(hardMode,
                        easyMode));

        hardModePanel = new JPanel();
        hardModeButtonGroup = new ButtonGroup();

        appendJRadioButtonToJPanel(hardModePanel, hardModeLabel, listJRadioButtonHardMode, hardModeButtonGroup);


    }

    private void appendJRadioButtonToJPanel(JPanel jPanel, JLabel jLabel, List<JRadioButton> JRadioList, ButtonGroup buttonGroup)
    {
        jPanel.setOpaque(false);

        jPanel.add(jLabel);


        for (JRadioButton jRadioButton : JRadioList) {
            jRadioButton.setOpaque(false);

            buttonGroup.add(jRadioButton);
            jPanel.add(jRadioButton);
        }

    }

    @Override
    protected void appendElements()
    {

        JPanel optionPanel = new JPanel();
        optionPanel.setBackground(getBgColor());

        BoxLayout boxlayout = new BoxLayout(optionPanel, BoxLayout.Y_AXIS);

        optionPanel.setLayout(boxlayout);

        optionPanel.setBorder(new EmptyBorder(new Insets(100, 50, 100, 50)));
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        Container contentPane = getContentPane();

        optionPanel.add(welcomeMessage);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        optionPanel.add(difficultyPanel);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        optionPanel.add(languagePanel);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        optionPanel.add(hardModePanel);
        optionPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        optionPanel.add(btnSave);

        contentPane.add(optionPanel);


    }

    @Override
    public void mettreAJour()
    {
        messages =  LanguageService.getInstance().getMessages();
    }
}
