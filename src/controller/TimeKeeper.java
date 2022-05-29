package controller;

import view.ChessGameFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TimeKeeper extends JFrame implements ActionListener {

    private JLabel showTime = new JLabel();
    private Timer timer;
    private int minute=1;
    private int second=0;
    private ChessGameFrame frame;
    private boolean show=false;

    public void setShow(boolean show){
        this.show=show;
    }

    public JLabel getShowTime(){
        return showTime;
    }

    public TimeKeeper(ChessGameFrame chessGameFrame){
        this.frame =chessGameFrame;
        timer = new Timer(1000,this);
        timer.start();
    }

    public void restart(){
        minute=1;
        second=0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.show) {
            showTime.setFont(new Font("方正舒体", Font.BOLD, 25));
            if (second != 0) {
                second = second - 1;
            } else {
                minute = minute - 1;
                second = 59;
            }
            if (second < 10) {
                showTime.setText(String.format("剩余：0%d:0%d", minute, second));
                System.out.printf("剩余：0%d:0%d%n", minute, second);
            } else {
                showTime.setText(String.format("剩余：0%d:%d", minute, second));
                System.out.printf("剩余：0%d:%d%n", minute, second);
            }
            if (minute < 1) {
                showTime.setForeground(Color.RED);
            }
            if (minute == 0 && second == 0) {
                minute = 1;
                frame.getChessboard().swapColor();
                JOptionPane.showMessageDialog(null, "超时！");
            }
        }
    }
}