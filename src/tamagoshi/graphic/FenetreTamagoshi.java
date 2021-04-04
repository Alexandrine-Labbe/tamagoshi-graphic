package tamagoshi.graphic;

import tamagoshi.controller.TamaGameController;
import tamagoshi.service.LanguageService;
import tamagoshi.service.LanguageServiceObserver;
import tamagoshi.tamagoshis.Tamagoshi;
import tamagoshi.tamagoshis.TamagoshiObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class FenetreTamagoshi extends FenetreAbstract implements LanguageServiceObserver, TamagoshiObserver {

    private TamaGameController tamaGameController;
    private URL imagePath = this.getClass().getResource("/images/dinosaur.png");
    private URL imageTombstonesPath = this.getClass().getResource("/images/tombstones.png");
    private ResourceBundle messages;
    private JButton btnFeed;
    private JButton btnPlay;
    private JLabel imageLabel;
    private JLabel hungerLabel;
    private JLabel funLabel;
    private String statusLabel;
    private String reactionLabel;
    private Tamagoshi tamagoshi;
    private Container contentPane;
    private LanguageService languageService;



    public FenetreTamagoshi(Tamagoshi tamagoshi, int xPosition, int yPosition, TamaGameController tamaGameController) throws HeadlessException
    {
        this.tamagoshi = tamagoshi;
        this.tamaGameController = tamaGameController;

        languageService = LanguageService.getInstance();
        messages = languageService.getMessages();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setTitle(tamagoshi.getName());
        setSize(300,350);
        setResizable(false);

        initJButton();
        initJLabel();
        appendElements();

        setLocation(xPosition, yPosition);
        setVisible(true);
        setAlwaysOnTop (true);

        tamagoshi.attacher(this);
        languageService.attacher(this);
    }

    @Override
    protected void initJButton()
    {
        btnFeed = new JButton(messages.getString("btnFeed"));
        btnPlay = new JButton(messages.getString("btnPlay"));

        setButtonBehaviour(btnFeed);
        setButtonBehaviour(btnPlay);
    }


    @Override
    protected void initJLabel()
    {
        ImageIcon image = new ImageIcon(imagePath);

        imageLabel = new JLabel(image);

        funLabel = new JLabel(messages.getString("fun") + " " +tamagoshi.getFun() + "/" + tamagoshi.getMaxFun() + " " );
        hungerLabel = new JLabel(messages.getString("hunger") + " " +tamagoshi.getEnergy() + "/" + tamagoshi.getMaxEnergy() + " " );

    }

    public void updateImage()
    {
        ImageIcon image = new ImageIcon(imageTombstonesPath);

        imageLabel.setIcon(image);
    }


    @Override
    public void appendElements()
    {
        statusLabel = tamagoshi.parle();
        reactionLabel = "";

        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());


        JPanel tamaPanel = new JPanel();
        JPanel tamaButtonsPanel = new JPanel();
        JPanel tamaStatusPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(tamaPanel, BoxLayout.Y_AXIS);

        tamaPanel.setLayout(boxlayout);
        tamaPanel.setBackground(getBgColor());

        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (!tamaGameController.isHardMode()) {
            tamaPanel.add(tamaStatusPanel);
        }

        tamaPanel.add(imageLabel);
        tamaPanel.add(tamaButtonsPanel);

        imageLabel.setVisible(true);


        tamaButtonsPanel.add(btnFeed);
        tamaButtonsPanel.add(btnPlay);
        tamaButtonsPanel.setOpaque(false);

        tamaStatusPanel.add(hungerLabel);
        tamaStatusPanel.add(funLabel);
        tamaStatusPanel.setOpaque(false);


        contentPane.add(tamaPanel, BorderLayout.NORTH);

        JPanel tamaInfoPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                g.setColor(Color.white);
                g.fillRoundRect(15, 2, 250, 90, 100, 100);

                g.setColor(Color.black);
                g.drawString(statusLabel, 40, 40);
                g.drawString(reactionLabel, 40, 60);
                this.setBackground(getBgColor());
            }
        };
        contentPane.add(tamaInfoPanel,  BorderLayout.CENTER);

    }


    @Override
    protected void setButtonBehaviour(JButton button)
    {
        super.setButtonBehaviour(button);


        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getSource().equals(btnFeed) && btnFeed.isEnabled()) {
                    tamagoshi.mange();
                    tamaGameController.actionPerformed("btnFeed");
                } else if(button.equals(btnPlay) && btnPlay.isEnabled()) {


                    tamagoshi.joue();
                    tamaGameController.actionPerformed("btnPlay");
                }
            }
        });

    }

    @Override
    public void mettreAJour()
    {
        messages =  LanguageService.getInstance().getMessages();
        resetText();
    }

    @Override
    public void mettreAJourTama()
    {
        statusLabel = tamagoshi.parle();
        reactionLabel = tamagoshi.getReaction();
        funLabel.setText(messages.getString("fun") + " " + tamagoshi.getFun() + "/" + tamagoshi.getMaxFun() + " " );
        hungerLabel.setText(messages.getString("hunger") + " " + tamagoshi.getEnergy() + "/" + tamagoshi.getMaxEnergy() + " " );

        contentPane.getComponent(1).repaint();
    }

    /**
     * Met à jour le tamagoshi et la langue des boutons
     */
    private void resetText()
    {
        mettreAJourTama();

        btnFeed.setText(messages.getString("btnFeed"));
        btnPlay.setText(messages.getString("btnPlay"));
    }

    /**
     * désactive un bouton
     * @param button a désactiver
     */
    public void disableButton(String button)
    {
        if(button.equals("btnFeed")) {
            btnFeed.setEnabled(false);

        } else if(button.equals("btnPlay")) {
            btnPlay.setEnabled(false);
        }
    }

    /**
     * Active tous les boutons
     */
    public void enableButtons()
    {
        btnFeed.setEnabled(true);
        btnPlay.setEnabled(true);

    }

    public Tamagoshi getTamagoshi() {
        return tamagoshi;
    }

    @Override
    public void dispose() {
        languageService.enlever(this);

        super.dispose();
    }
}
