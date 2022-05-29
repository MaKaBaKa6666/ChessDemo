package model;

import controller.ClickController;
import main.Main;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PawnChessComponent extends ChessComponent {

    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;

    private Image pawnImage;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/" + Main.getChessForm() + "/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/" + Main.getChessForm() + "/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor() == ChessColor.BLACK) {
            if (source.getY() == destination.getY()) {
                if (source.getX() == 1 && (destination.getX() - source.getX() == 2 || destination.getX() - source.getX() == 1)) {
                    for (int t = 1; t <= destination.getX() - source.getX(); t++) {
                        if (!(chessComponents[source.getX() + t][source.getY()] instanceof EmptySlotComponent)) {
                            return false;
                        }
                    }
                    return true;
                } else
                    return (destination.getX() - source.getX() == 1 && chessComponents[source.getX() + 1][source.getY()] instanceof EmptySlotComponent);
            } else if ((source.getY() == destination.getY() - 1 || source.getY() == destination.getY() + 1) && destination.getX() == source.getX() + 1) {
                if (source.getX() == 4 && chessComponents[source.getX()][destination.getY()] instanceof model.PawnChessComponent && chessComponents[source.getX()][destination.getY()].getChessColor() == ChessColor.WHITE) {
                    return true;
                } else return !(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent);
            } else return false;
        } else {
            if (source.getY() == destination.getY()) {
                if (source.getX() == 6 && (destination.getX() - source.getX() == -2 || destination.getX() - source.getX() == -1)) {
                    for (int t = 1; t <= destination.getX() - source.getX(); t++) {
                        if (!(chessComponents[source.getX() - t][source.getY()] instanceof EmptySlotComponent)) {
                            return false;
                        }
                    }
                    return true;
                } else
                    return (destination.getX() - source.getX() == -1 && chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent);
            } else if ((source.getY() == destination.getY() - 1 || source.getY() == destination.getY() + 1) && destination.getX() == source.getX() - 1) {
                if (source.getX() == 3 && chessComponents[source.getX()][destination.getY()] instanceof model.PawnChessComponent && chessComponents[source.getX()][destination.getY()].getChessColor() == ChessColor.BLACK) {
                    return true;
                } else return !(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent);
            } else return false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}