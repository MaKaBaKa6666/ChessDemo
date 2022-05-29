package model;

import controller.ClickController;
import main.Main;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class QueenChessComponent extends ChessComponent {

    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    private Image queenImage;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/" + Main.getChessForm() + "/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/" + Main.getChessForm() + "/queen-black.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (destination.getX() - source.getX() == destination.getY() - source.getY()) {
            if (destination.getX() - source.getX() > 0) {
                for (int t = 1; t <= destination.getX() - source.getX(); t++) {
                    if (!(chessComponents[source.getX() + t][source.getY() + t] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
                return true;
            } else if (destination.getX() - source.getX() < 0) {
                for (int t = 1; t <= source.getX() - destination.getX(); t++) {
                    if (!(chessComponents[source.getX() - t][source.getY() - t] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
                return true;
            } else return false;
        } else if (destination.getX() - source.getX() + destination.getY() - source.getY() == 0) {
            if (destination.getX() - source.getX() > 0) {
                for (int t = 1; t <= destination.getX() - source.getX(); t++) {
                    if (!(chessComponents[source.getX() + t][source.getX() - t] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
                return true;
            } else if (destination.getY() - source.getY() > 0) {
                for (int t = 1; t <= destination.getY() - source.getY(); t++) {
                    if (!(chessComponents[source.getX() - t][source.getX() + t] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
                return true;
            }
        } else if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(queenImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}