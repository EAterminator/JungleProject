package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CellComponent extends JPanel {
    private Color background;
    private Image image;

    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = background;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(background);
        g.fillRect(1, 1, getWidth()-1, getHeight()-1);

        if (image != null) {
            int x = (getWidth() - image.getWidth(null)) / 2;
            int y = (getHeight() - image.getHeight(null)) / 2;
            g.drawImage(image, x, y, null);
        }
    }
}
