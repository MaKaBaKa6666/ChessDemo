package view;

import javax.swing.*;

public class TimeKeeper extends Thread {
    public void run() {//计时器启动的方法
        while (true) {
            for (int i = 60; i >= 0; i--) {
                if (i == 0) {
                    JOptionPane.showMessageDialog(null, "超时");
                } else {
                    try {
                        ChessGameFrame.getTimeKeeper().setValue(i);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

