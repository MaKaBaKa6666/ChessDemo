package view;

import javax.swing.*;
import java.awt.*;
//开始界面的背景
public class jpanelStartImage extends JPanel {
    private final Image img= new ImageIcon("./images/开始界面2副本.jpg").getImage();

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),this);
    }
}
