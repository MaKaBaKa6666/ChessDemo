package view;

import javax.swing.*;
import java.awt.*;

public class jPanelBackGroundImage extends JPanel {
    private static String backGround;

    public jPanelBackGroundImage(String str) {
        backGround = str;
    }

    public void paintComponent(Graphics g) {
        Image img = new ImageIcon("./images/" + backGround + ".jpg").getImage();
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
