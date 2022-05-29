package model;

import controller.ClickController;
import main.Main;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BishopChessComponent extends ChessComponent {

    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;

    private Image bishopImage;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/" + Main.getChessForm() + "/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/" + Main.getChessForm() + "/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
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
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}