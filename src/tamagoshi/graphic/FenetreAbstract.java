package tamagoshi.graphic;

import javax.swing.*;
import java.awt.*;

public abstract class FenetreAbstract extends JFrame {
    private Color normalColor = Color.decode("#A9CCE3");
    private Color hoverColor = Color.decode("#7FB3D5");
    private Color bgColor = Color.decode("#EBDEF0");


    FenetreAbstract() {
    }

    /**
     * initialise le style et le comportement de base d'un JButton
     * @param button à initialiser
     */
    protected void setButtonBehaviour(JButton button)
    {


        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(normalColor);
        button.setBorderPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }

        });
    }

    /**
     * initialise les JButtons
     */
    protected void initJButton() {}

    /**
     * initialise les JLabels
     */
    protected void initJLabel() {}

    /**
     * initialise les JRadioButtons
     */
    protected void initJRadioButton() {}

    /**
     * ajoute les éléments à la fenêtre
     */
    protected void appendElements() {}

    /**
     * @return la couleur d'un bouton en état normal
     */
    public Color getNormalColor() {
        return normalColor;
    }

    /**
     * @return la couleur d'un bouton en état hover
     */
    public Color getHoverColor() {
        return hoverColor;
    }

    /**
     * @return la couleur de fond des fenêtres
     */
    Color getBgColor() {
        return bgColor;
    }
}
