package view;

import controller.GameController;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private Chessboard chessboard;
    private JPanel jPanel;
    private String backGroundName;
    private static JTextArea textArea;
    private static JButton buttonForSave, buttonForLoad;
    private static JProgressBar timeKeeper = new JProgressBar();
    private JFrame saveWindow;
    private JFrame loadWindow;
    private JFrame rankWindow;
    private static String saveName;
    private static String loadName;

    public static JTextArea getTextArea() {
        return textArea;
    }

    public static JProgressBar getTimeKeeper() {
        return timeKeeper;
    }

    public static JButton getButtonForSave() {
        return buttonForSave;
    }

    public static String getSaveName() {
        return saveName;
    }

    public static String getLoadName() {
        return loadName;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public ChessGameFrame(int width, int height, String str) {
        backGroundName = str;
        jPanel = new jPanelBackGroundImage(str);
        setTitle("国际象棋"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
//        addChessboard();
//        addLabel();
//        addResetButton();
//        addLoadButton();
//        addDeleteButton();
//        addHelpButton();
        addBackground();
        loadWindow();
        saveWindow();
        rankWindow();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    private void addBackground() {
        jPanel.setLayout(null);
        jPanel.setSize(1000, 760);
        setBackground(Color.BLACK);
        addChessboard();
        addLabel();

        addResetButton();
        addSaveButton();
        addLoadButton();
        addRegretButton();
        addHelpButton();
        backToMainMenu();
        addPlayerButton();
        addRank();

        decideTurn();
        timeKeeper();

        add(jPanel);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("选项");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setForeground(Color.lightGray);
        statusLabel.setFont(new Font("宋体", Font.BOLD, 40));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addResetButton() {
        JButton button = new JButton("新游戏");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "游戏重置"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 60);
        button.setSize(200, 60);
        button.setFont(new Font("宋体", Font.BOLD, 20));
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                dispose();
//                SwingUtilities.invokeLater(() -> {
//                    ChessGameFrame mainFrame = new ChessGameFrame(1000, 760, backGroundName);
//                    mainFrame.setVisible(true);
                chessboard.reset();
//                });
            }
        });
        add(button);
    }

    private void addSaveButton() {
        buttonForSave = new JButton("保存游戏");
        buttonForSave.setLocation(HEIGTH, HEIGTH / 10 + 120);
        buttonForSave.setSize(200, 60);
        buttonForSave.setFont(new Font("宋体", Font.BOLD, 20));
        add(buttonForSave);

        buttonForSave.addActionListener(e -> {
            saveWindow.setVisible(true);
        });
    }

    //读取游戏存档
    private void addLoadButton() {
        buttonForLoad = new JButton("载入游戏");
        buttonForLoad.setLocation(HEIGTH, HEIGTH / 10 + 180);
        buttonForLoad.setSize(200, 60);
        buttonForLoad.setFont(new Font("宋体", Font.BOLD, 20));
        add(buttonForLoad);

        buttonForLoad.addActionListener(e -> {
            loadWindow.setVisible(true);
        });
    }

    private void saveWindow() {
        saveWindow = new JFrame("保存游戏");
        saveWindow.setLayout(null);
        saveWindow.setLocation(300, 300);
        saveWindow.setSize(300, 300);

        JLabel jLabel1 = new JLabel("现有存档");
        jLabel1.setLocation(0, 0);
        jLabel1.setSize(60, 30);
        jLabel1.setVisible(true);

        JLabel jLabel2 = new JLabel("输入存档名");
        jLabel2.setLocation(0, 110);
        jLabel2.setSize(60, 30);
        jLabel2.setVisible(true);

        JTextArea jTextArea1 = new JTextArea();
        jTextArea1.setLocation(60, 0);
        jTextArea1.setSize(200, 100);
        jTextArea1.setEditable(false);
        jTextArea1.setVisible(true);
        String path = "resource";
        File file = new File(path);
//        if (!file.exists()) {
//            jTextArea1.setText("没有存档");
//        }
        File[] result = file.listFiles();
        for (int i = 0; i < Objects.requireNonNull(result).length; i++) {
            File fs = result[i];
            int m = fs.getName().indexOf("(");
            int n = fs.getName().indexOf(")");
            if (fs.isFile()) {
                if (fs.getName().substring(m + 1, n).equals(Main.getUserName())) {
                    String fileName = fs.getName().replaceAll("\\(" + Main.getUserName() + "\\)" + "\\.txt", "");
                    jTextArea1.append(fileName + "\n");
                }
            }
        }

        JTextArea jTextArea2 = new JTextArea("--请输入存档名称--");
        jTextArea2.setLocation(60, 110);
        jTextArea2.setSize(200, 30);
        jTextArea2.setVisible(true);

        JButton jButton = new JButton("保存");
        jButton.setLocation(60, 150);
        jButton.setSize(60, 30);
        jButton.setVisible(true);
        jButton.addActionListener(e -> {
            if (jTextArea2.getText() != null) {
                saveName = jTextArea2.getText();
                chessboard.saveIn();
                saveWindow.dispose();
            }
        });

        saveWindow.add(jTextArea1);
        saveWindow.add(jTextArea2);
        saveWindow.add(jLabel1);
        saveWindow.add(jLabel2);
        saveWindow.add(jButton);
        saveWindow.setVisible(false);
        saveWindow.setResizable(false);
        saveWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void loadWindow() {
        loadWindow = new JFrame("载入游戏");
        loadWindow.setLayout(null);
        loadWindow.setLocation(300, 300);
        loadWindow.setSize(300, 300);

        JLabel jLabel1 = new JLabel("现有存档");
        jLabel1.setLocation(0, 0);
        jLabel1.setSize(60, 30);
        jLabel1.setVisible(true);

        JLabel jLabel2 = new JLabel("输入存档名");
        jLabel2.setLocation(0, 110);
        jLabel2.setSize(60, 30);
        jLabel2.setVisible(true);

        JTextArea jTextArea1 = new JTextArea();
        jTextArea1.setLocation(60, 0);
        jTextArea1.setSize(200, 100);
        jTextArea1.setEditable(false);
        jTextArea1.setVisible(true);
        String path = "resource";
        File file = new File(path);
//        if (!file.exists()) {
//            jTextArea1.setText("没有存档");
//        }
        File[] result = file.listFiles();
        for (int i = 0; i < Objects.requireNonNull(result).length; i++) {
            File fs = result[i];
            int m = fs.getName().indexOf("(");
            int n = fs.getName().indexOf(")");
            if (fs.isFile()) {
                if (fs.getName().substring(m + 1, n).equals(Main.getUserName())) {
                    if (fs.getName().substring(n + 1).equals(".txt")) {
                        String fileName = fs.getName().replaceAll("\\(" + Main.getUserName() + "\\)" + "\\.txt", "");
                        jTextArea1.append(fileName + "\n");
                    }
                }
            }
        }

        JTextArea jTextArea2 = new JTextArea("--请输入存档名称--");
        jTextArea2.setLocation(60, 110);
        jTextArea2.setSize(200, 30);
        jTextArea2.setVisible(true);

        JButton jButton = new JButton("读取");
        jButton.setLocation(60, 150);
        jButton.setSize(60, 30);
        jButton.setVisible(true);
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadName = jTextArea2.getText();
                try {
                    chessboard.loadChessboard();
//                    buttonForSave.setEnabled(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                loadWindow.dispose();
            }
        });

        loadWindow.add(jTextArea1);
        loadWindow.add(jTextArea2);
        loadWindow.add(jLabel1);
        loadWindow.add(jLabel2);
        loadWindow.add(jButton);
        loadWindow.setVisible(false);
        loadWindow.setResizable(false);
        loadWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void addRegretButton() {
        JButton button = new JButton("悔棋");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("宋体", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> chessboard.regret());
    }

    private void addHelpButton() {
        JButton button = new JButton("帮助");
        button.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("宋体", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> JOptionPane.showMessageDialog(this, "怎么下自己上网查", "Help", 1));
    }

    private void backToMainMenu() {
        JButton button = new JButton("返回主菜单");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("宋体", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
                    dispose();
                    Main.main(null);
                }
        );
    }

    private void addRank() {
        JButton button = new JButton("排行榜");
        button.setLocation(HEIGTH, HEIGTH / 10 + 420);
        button.setSize(200, 60);
        button.setFont(new Font("宋体", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
            rankWindow.setVisible(true);
        });
    }

    private void rankWindow() {
        rankWindow = new JFrame("排行榜");
        rankWindow.setLayout(null);
        rankWindow.setLocation(300, 300);
        rankWindow.setSize(300, 300);

        JLabel jLabel1 = new JLabel("用户名");
        jLabel1.setLocation(10, 0);
        jLabel1.setSize(60, 30);
        jLabel1.setVisible(true);

        JLabel jLabel2 = new JLabel("游戏次数");
        jLabel2.setLocation(200, 0);
        jLabel2.setSize(60, 30);
        jLabel2.setVisible(true);

        JTextArea jTextArea1 = new JTextArea();
        jTextArea1.setLocation(10, 30);
        jTextArea1.setSize(270, 240);
        jTextArea1.setEditable(false);
        jTextArea1.setVisible(true);

        rankWindow.add(jTextArea1);
        rankWindow.add(jLabel1);
        rankWindow.add(jLabel2);
        rankWindow.setVisible(false);
        rankWindow.setResizable(false);
        rankWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    private void addPlayerButton() {//播放音乐
        JButton button = new JButton("背景音乐");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(100, 50);
        button.setVisible(true);
        add(button);

        BackGroundMusic backGroundMusic = new BackGroundMusic();
        button.addActionListener(e -> backGroundMusic.run());
    }


    private void decideTurn() {//判断行棋方
        textArea = new JTextArea("行棋方：白");
        textArea.setFont(new Font("宋体", Font.BOLD, 10));
        textArea.setLocation(HEIGTH, HEIGTH / 10 - 30);
        textArea.setSize(60, 20);
        textArea.setEditable(false);
        textArea.setVisible(true);
        add(textArea);
    }

    private void timeKeeper() {//计时器
        timeKeeper.setMinimum(0);
        timeKeeper.setMaximum(60);
        timeKeeper.setValue(60);
        timeKeeper.setForeground(new Color(70, 70, 70));
        timeKeeper.setBackground(new Color(188, 188, 188));
        timeKeeper.setLocation(HEIGTH + 70, HEIGTH / 10 - 30);
        timeKeeper.setSize(110, 20);
        add(timeKeeper);
    }
}