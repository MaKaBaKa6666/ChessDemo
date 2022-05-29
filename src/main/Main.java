package main;

import model.ChessComponent;
import view.ChessGameFrame;
import view.jpanelStartImage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

//开始界面
public class Main {
    private static String string = "背景";

    private static String chessForm = "棋子";

    private static String userName;

    public static String getChessForm() {
        return chessForm;
    }

    public static void setChessForm(String chessForm) {
        Main.chessForm = chessForm;
    }

    public static void setString(String string) {
        Main.string = string;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Main.userName = userName;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }//使风格变成windows的风格

        JFrame jf = new JFrame("国际象棋");
        jf.setSize(1000, 760);
        jf.setLocationRelativeTo(null);

        JPanel jPanel1 = new jpanelStartImage();
        jPanel1.setLayout(null);

        JLabel jLabel1 = new JLabel("国际象棋");
        jLabel1.setBounds(375, 100, 250, 150);
        jLabel1.setFont(new Font("楷体", 1, 60));
//        jLabel1.setOpaque(true);//设置背景颜色不透明
        jLabel1.setForeground(Color.LIGHT_GRAY);//设置字体颜色
        Border border = BorderFactory.createLineBorder(Color.lightGray);
        jLabel1.setBorder(border);
        jLabel1.setVisible(true);


        JButton jButton1 = new JButton("开始游戏");
        jButton1.setSize(100, 50);
        jButton1.setLocation(450, 350);
//        jButton1.setBorder(BorderFactory.createLoweredBevelBorder());//使按钮凹下去
//        jButton1.setBorder(BorderFactory.createRaisedBevelBorder());//使按钮凸起来
//        jButton1.setContentAreaFilled(false);//使这个按钮的背景透明
//        jButton1.setFont(new java.awt.Font("华文行楷", 1, 15));//改变按钮的字体
//        jButton1.setBackground(Color.lightGray);//改变按钮的颜色
//        jButton1.setBorderPainted(false);//去掉按钮边框
        jButton1.setVisible(true);
//        jButton1.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                jf.dispose();
//            }
//        });
        jButton1.addActionListener(e -> {
            JDialog login = new JDialog(jf, "登录");
            login.setLocationRelativeTo(null);
            login.setSize(200, 200);
            login.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel account = new JLabel("账号");
            JTextField userAccount = new JTextField("", 20);
//            JLabel password = new JLabel("密码");
//            JPasswordField passwordField = new JPasswordField("", 20);
            JButton loginButton = new JButton("登录");
            JButton registerButton = new JButton("注册");
            loginButton.addActionListener(e1 -> {
                File file = new File("account/" + userAccount.getText() + ".txt");
                if (!file.exists()) {
                    userAccount.setText("用户不存在");
//                    passwordField.setText("");
                } else {
//                    try {
//                        FileReader reader = new FileReader("account/" + userAccount.getText() + ".txt");
//                        BufferedReader br = new BufferedReader(reader);
////                        if (new String(passwordField.getPassword()).equals(br.readLine())) {
//                        FileWriter fileWriter = new FileWriter("account/" + userAccount.getText() + ".txt");
//                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//                        bufferedWriter.write("1");
//                        bufferedWriter.newLine();
//                        fileWriter.flush();
//                        bufferedWriter.flush();
//                        fileWriter.close();
//                        bufferedWriter.close();
                        Start();
                        jf.dispose();
                        setUserName(userAccount.getText());
//                        } else {
//                            userAccount.setText("密码错误");
//                            passwordField.setText("");
//                        }
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
                }
            });

            registerButton.addActionListener(e1 -> {
                login.dispose();
                JDialog register = new JDialog(jf, "注册");
                register.setLocationRelativeTo(null);
                register.setSize(200, 200);
                register.setLayout(new FlowLayout(FlowLayout.LEFT));

                JLabel accountCreated = new JLabel("账号");
                JTextField userAccountCreated = new JTextField("", 20);
//                JLabel passwordCreated = new JLabel("密码");
//                JPasswordField passwordFieldCreated = new JPasswordField("", 20);
                JButton createAccount = new JButton("创建账户");
                createAccount.addActionListener(e2 -> {
                    File file = new File("account/" + userAccountCreated.getText() + ".txt");
                    if (file.exists()) {
                        userAccountCreated.setText("用户已存在");
//                        passwordFieldCreated.setText("");
                    } else {
                        register.dispose();
//                        try {
//                            FileWriter fileWriter = new FileWriter(file);
//                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//                            bufferedWriter.write("1");
//                            bufferedWriter.newLine();
//                            if (new String(passwordFieldCreated.getPassword()).length() != 0) {
//                                bufferedWriter.write(new String(passwordFieldCreated.getPassword()));
//                                bufferedWriter.newLine();
//                                register.dispose();
////                                jf.dispose();
//                            fileWriter.close();
//                            bufferedWriter.close();
////                            } else {
////                                userAccountCreated.setText("密码不能为空");
////                                passwordFieldCreated.setText("");
////                            }
//                        } catch (IOException ex) {
//                            throw new RuntimeException(ex);
//                        }
                    }
                });

                register.add(accountCreated);
                register.add(userAccountCreated);
//                register.add(passwordCreated);
//                register.add(passwordFieldCreated);
                register.add(createAccount);
                register.setResizable(false);
                register.setVisible(true);
                register.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            });

            login.add(account);
            login.add(userAccount);
//            login.add(password);
//            login.add(passwordField);
            login.add(loginButton);
            login.add(registerButton);
            login.setResizable(false);
            login.setVisible(true);
            login.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        });

        JButton jButton2 = new JButton("退出游戏");
        jButton2.setSize(100, 50);
        jButton2.setLocation(450, 450);
        jButton2.setVisible(true);
        jButton2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.setSize(1000, 30);
        jMenuBar.setLocation(0, 0);

        JMenu jMenu1 = new JMenu("更换背景");
        JMenuItem item1 = new JMenuItem("1");
        JMenuItem item2 = new JMenuItem("2");
        jMenu1.add(item1);
        jMenu1.add(item2);
        item1.addActionListener(e -> setString("背景1"));
        item2.addActionListener(e -> setString("背景"));

        JMenu jMenu2 = new JMenu("更换棋子样式");
        JMenuItem item4 = new JMenuItem("1");
        JMenuItem item5 = new JMenuItem("2");
        jMenu2.add(item4);
        jMenu2.add(item5);
        item4.addActionListener(e -> setChessForm("棋子"));
        item5.addActionListener(e -> setChessForm("棋子1"));

        JMenu jMenu3 = new JMenu("更换棋盘样式");
        JMenuItem item6 = new JMenuItem("1");
        JMenuItem item7 = new JMenuItem("2");
        jMenu3.add(item6);
        jMenu3.add(item7);
        item6.addActionListener(e -> ChessComponent.setBackgroundColors(new Color[]{Color.BLACK, Color.WHITE}));
        item7.addActionListener(e -> ChessComponent.setBackgroundColors(new Color[]{Color.GRAY, Color.WHITE}));

        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        jMenuBar.add(jMenu3);
        jMenuBar.setVisible(true);

        jPanel1.add(jLabel1);
        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        jPanel1.add(jMenuBar);

        jf.add(jPanel1);
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void Start() {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760, string);
            mainFrame.setVisible(true);
        });
    }
}