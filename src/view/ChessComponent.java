package view;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;



public abstract class ChessComponent extends JComponent {
    protected PlayerColor owner;
    private int size;
    private boolean selected;

    public ChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.size = size;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    protected abstract void paintComponent(Graphics g) ;
}