package view;

import main.Main;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;


/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;

    private static int count = 0;//判断行棋方用的东西

    private static ArrayList<String> chess = new ArrayList<>();

    public static ArrayList<String> getChess() {
        return chess;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);//放车

        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, 6, ChessColor.BLACK);
        initKnightOnBoard(7, 1, ChessColor.WHITE);
        initKnightOnBoard(7, 6, ChessColor.WHITE);//放马

        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, 5, ChessColor.BLACK);
        initBishopOnBoard(7, 2, ChessColor.WHITE);
        initBishopOnBoard(7, 5, ChessColor.WHITE);

        for (int t = 0; t < 8; t++) {
            initPawnOnBoard(1, t, ChessColor.BLACK);
            initPawnOnBoard(6, t, ChessColor.WHITE);
        }

        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(7, 4, ChessColor.WHITE);

        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(7, 3, ChessColor.WHITE);
        chess.add("RNBQKBNR");
        chess.add("PPPPPPPP");
        chess.add("________");
        chess.add("________");
        chess.add("________");
        chess.add("________");
        chess.add("pppppppp");
        chess.add("rnbqkbnr");
        chess.add("w");
    }

    public void reset() {
        chess = new ArrayList<>();
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                remove(chessComponents[m][n]);
                chessComponents[m][n].repaint();
            }
        }
        initiateEmptyChessboard();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);//放车

        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, 6, ChessColor.BLACK);
        initKnightOnBoard(7, 1, ChessColor.WHITE);
        initKnightOnBoard(7, 6, ChessColor.WHITE);//放马

        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, 5, ChessColor.BLACK);
        initBishopOnBoard(7, 2, ChessColor.WHITE);
        initBishopOnBoard(7, 5, ChessColor.WHITE);

        for (int t = 0; t < 8; t++) {
            initPawnOnBoard(1, t, ChessColor.BLACK);
            initPawnOnBoard(6, t, ChessColor.WHITE);
        }

        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(7, 4, ChessColor.WHITE);

        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(7, 3, ChessColor.WHITE);
        currentColor = ChessColor.BLACK;
        ChessGameFrame.getTextArea().setText("行棋方：黑");
        repaint();
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }


    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();

        //判断行棋方
//        TimeKeeper timeKeeper = new TimeKeeper();
//        count++;
//        if (count % 2 == 0) {
//            ChessGameFrame.getTextArea().setText("行棋方：黑");
//            timeKeeper.interrupt();
//            timeKeeper = new TimeKeeper();
//            timeKeeper.start();
//        } else {
//            ChessGameFrame.getTextArea().setText("行棋方：白");
//            timeKeeper.interrupt();
//            timeKeeper = new TimeKeeper();
//            timeKeeper.start();
//        }
        saveSteps();
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        count++;
        if (count % 2 == 0) {
            ChessGameFrame.getTextArea().setText("行棋方：白");
        } else {
            ChessGameFrame.getTextArea().setText("行棋方：黑");
        }
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }


    //下面的什么init是用来加棋子的
    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

//    public void loadGame(List<String> chessData) {
//        chessData.forEach(System.out::println);//这里没弄完 输入save1的绝对路径后会把save1中的文本打印出来  要自己写储存棋盘
//    }

    public void saveSteps() {
        String str = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (chessComponents[i][j].getClass().toString().replaceAll("class model.", "")) {
                    case "RookChessComponent":
                        if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                            str = str.concat("R");
                        else str = str.concat("r");
                        break;
                    case "KnightChessComponent":
                        if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                            str = str.concat("N");
                        else str = str.concat("n");
                        break;
                    case "BishopChessComponent":
                        if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                            str = str.concat("B");
                        else str = str.concat("b");
                        break;
                    case "QueenChessComponent":
                        if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                            str = str.concat("Q");
                        else str = str.concat("q");
                        break;
                    case "KingChessComponent":
                        if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                            str = str.concat("K");
                        else str = str.concat("k");
                        break;
                    case "PawnChessComponent":
                        if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                            str = str.concat("P");
                        else str = str.concat("p");
                        break;
                    default:
                        str = str.concat("_");
                }
            }
            chess.add(str);
            str = "";
        }
        if (currentColor == ChessColor.BLACK) {
            chess.add("w");
        } else chess.add("b");
    }

    public void saveIn() {
        String str = "resource/" + ChessGameFrame.getSaveName() + "(" + Main.getUserName() + ")" + ".txt";
        File file = new File(str); // 创建文件对象,可以再加个异常分析，是否已存在该文件
        // 写入
        try {
            FileWriter fileWriter = new FileWriter(file);// 创建FileWriter类对象
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int k = 0; k < Chessboard.getChess().size(); k++) {// 循环遍历content数组的内容
                bufferedWriter.write(Chessboard.getChess().get(k));// 将字符串数组中的每个元素写入到磁盘文件中
                bufferedWriter.newLine();// 实现换行，将数组中的单个元素以单行的形式写入文件
            }
            fileWriter.flush();
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //读取
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s;
            int i = 0;// 先判断要读的文件的文本行数不为null，才进入循环
            while ((s = bufferedReader.readLine()) != null) {// 读取文本行，并将其返回为字符串。若无数据可读，则返回null。
                i++;
                System.out.println("第" + i + "行：" + s);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadChessboard() throws IOException {
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                remove(chessComponents[m][n]);
                chessComponents[m][n].repaint();
            }
        }
        initiateEmptyChessboard();
        String pathname = "resource/" + ChessGameFrame.getLoadName() + "(" + Main.getUserName() + ")" + ".txt";// 读取以上路径的input.txt
        int count = 0;
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader)
        ) {
            while (br.readLine() != null) {
//                System.out.println(line);
                count++;
            }
//            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (count % 9 != 0) {
            JOptionPane.showMessageDialog(this, "错误的存档", "错误", JOptionPane.WARNING_MESSAGE);
            reset();
            return;
        }
        load(count - 8);
//        ChessGameFrame.getButtonForSave().setEnabled(false);
        repaint();
    }

    public void load(int txtLines) throws IOException {
        File file = new File("resource/" + ChessGameFrame.getLoadName() + "(" + Main.getUserName() + ")" + ".txt");//文件路径
        FileReader fileReader1 = new FileReader(file);
        LineNumberReader reader1 = new LineNumberReader(fileReader1);
        String txt = "";
        int number = txtLines;
        int lines = 0;
        FileReader fileReader = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(fileReader);
        while (txt != null) {
            lines++;
            txt = reader.readLine();
            if (lines == number & number < txtLines + 9) {
                System.out.print("第" + reader.getLineNumber() + "行:" + txt + "\n");
                if (txt.length() == 8) {
                    for (int j = 0; j < 8; j++) {
                        switch (txt.charAt(j)) {
                            case 'R':
                                initRookOnBoard((lines - 1) % 9, j, ChessColor.BLACK);
                                break;
                            case 'r':
                                initRookOnBoard((lines - 1) % 9, j, ChessColor.WHITE);
                                break;
                            case 'N':
                                initKnightOnBoard((lines - 1) % 9, j, ChessColor.BLACK);
                                break;
                            case 'n':
                                initKnightOnBoard((lines - 1) % 9, j, ChessColor.WHITE);
                                break;
                            case 'B':
                                initBishopOnBoard((lines - 1) % 9, j, ChessColor.BLACK);
                                break;
                            case 'b':
                                initBishopOnBoard((lines - 1) % 9, j, ChessColor.WHITE);
                                break;
                            case 'Q':
                                initQueenOnBoard((lines - 1) % 9, j, ChessColor.BLACK);
                                break;
                            case 'q':
                                initQueenOnBoard((lines - 1) % 9, j, ChessColor.WHITE);
                                break;
                            case 'K':
                                initKingOnBoard((lines - 1) % 9, j, ChessColor.BLACK);
                                break;
                            case 'k':
                                initKingOnBoard((lines - 1) % 9, j, ChessColor.WHITE);
                                break;
                            case 'P':
                                initPawnOnBoard((lines - 1) % 9, j, ChessColor.BLACK);
                                break;
                            case 'p':
                                initPawnOnBoard((lines - 1) % 9, j, ChessColor.WHITE);
                                break;
                            case '_':
                                break;
                            default:
                                JOptionPane.showMessageDialog(this, "错误的存档", "错误", JOptionPane.WARNING_MESSAGE);
                                reset();
                                return;
                        }
                    }
                    number++;
                } else if (txt.length() == 1) {
                    if (txt.charAt(0) == 'w') {
                        currentColor = ChessColor.WHITE;
                        ChessGameFrame.getTextArea().setText("行棋方：白");
                    } else if (txt.charAt(0) == 'b') {
                        currentColor = ChessColor.BLACK;
                        ChessGameFrame.getTextArea().setText("行棋方：黑");
                    }
                } else JOptionPane.showMessageDialog(this, "错误的存档", "错误", JOptionPane.WARNING_MESSAGE);
            }
        }
        reader.close();
        fileReader.close();
        repaint();
    }

    public void regret() {
        for (int m = 0; m < 8; m++) {
            for (int n = 0; n < 8; n++) {
                remove(chessComponents[m][n]);
                chessComponents[m][n].repaint();
            }
        }
        initiateEmptyChessboard();
        if (chess.size() >= 18) {
            for (int i = chess.size() - 18; i < chess.size() - 9; i++) {
                if (chess.get(i).length() == 8 & i < chess.size() - 10) {
                    for (int j = 0; j < 8; j++) {
                        switch (chess.get(i).charAt(j)) {
                            case 'R':
                                initRookOnBoard(i % 9, j, ChessColor.BLACK);
                                break;
                            case 'r':
                                initRookOnBoard(i % 9, j, ChessColor.WHITE);
                                break;
                            case 'N':
                                initKnightOnBoard(i % 9, j, ChessColor.BLACK);
                                break;
                            case 'n':
                                initKnightOnBoard(i % 9, j, ChessColor.WHITE);
                                break;
                            case 'B':
                                initBishopOnBoard(i % 9, j, ChessColor.BLACK);
                                break;
                            case 'b':
                                initBishopOnBoard(i % 9, j, ChessColor.WHITE);
                                break;
                            case 'Q':
                                initQueenOnBoard(i % 9, j, ChessColor.BLACK);
                                break;
                            case 'q':
                                initQueenOnBoard(i % 9, j, ChessColor.WHITE);
                                break;
                            case 'K':
                                initKingOnBoard(i % 9, j, ChessColor.BLACK);
                                break;
                            case 'k':
                                initKingOnBoard(i % 9, j, ChessColor.WHITE);
                                break;
                            case 'P':
                                initPawnOnBoard(i % 9, j, ChessColor.BLACK);
                                break;
                            case 'p':
                                initPawnOnBoard(i % 9, j, ChessColor.WHITE);
                                break;
                            case '_':
                                break;
                            default:
                                JOptionPane.showMessageDialog(this, "错误的存档", "错误", JOptionPane.WARNING_MESSAGE);
                        }
//                        if (currentColor == ChessColor.BLACK) {
//                            currentColor = ChessColor.WHITE;
//                            ChessGameFrame.getTextArea().setText("行棋方：白");
//                        } else {
//                            currentColor = ChessColor.BLACK;
//                            ChessGameFrame.getTextArea().setText("行棋方：黑");
//                        }
                    }
                }
            }
            if (currentColor == ChessColor.BLACK) {
                currentColor = ChessColor.WHITE;
                ChessGameFrame.getTextArea().setText("行棋方：白");
            } else if (currentColor == ChessColor.WHITE) {
                currentColor = ChessColor.BLACK;
                ChessGameFrame.getTextArea().setText("行棋方：黑");
            }
            repaint();
            for (int i = 0; i < 9; i++) {
//                System.out.println(chess.get(chess.size() - 1));
                chess.remove(chess.size() - 1);
//                System.out.println();
            }
//            for (int m = 0; m < chess.size(); m++) {
//                System.out.println(chess.get(m));
//            }
//            System.out.println();
        } else {
            reset();
            JOptionPane.showMessageDialog(this, "悔到头了", "错误", JOptionPane.WARNING_MESSAGE);
        }
    }
}