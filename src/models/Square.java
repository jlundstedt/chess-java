package models;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

@SuppressWarnings("serial")
public class Square extends JComponent {
    private Board board;

    private final int color;
    private Piece occupyingPiece;
    private boolean willDisplayPiece;

    private int xCoordinate;
    private int yCoordinate;

    public Square(Board board, int color, int xCoordinate, int yCoordinate) {

        this.board = board;
        this.color = color;
        this.willDisplayPiece = true;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public int getColor() {
        return this.color;
    }

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    boolean isOccupied() {
        return (this.occupyingPiece != null);
    }

    public int getXNum() {
        return this.xCoordinate;
    }

    public int getYNum() {
        return this.yCoordinate;
    }

    void setDisplay(boolean display) {
        this.willDisplayPiece = display;
    }

    public void put(Piece piece) {
        this.occupyingPiece = piece;
        piece.setPosition(this);
    }

    void removePiece() {
        this.occupyingPiece = null;
    }

    void capture(Piece piece) {
        Piece k = getOccupyingPiece();
        if (k.getColor() == 0) board.blackPieces.remove(k);
        if (k.getColor() == 1) board.whitePieces.remove(k);
        this.occupyingPiece = piece;
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (this.color == 1) {
            graphics.setColor(new Color(221, 192, 127));
        } else {
            graphics.setColor(new Color(101, 67, 33));
        }

        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        if (occupyingPiece != null && willDisplayPiece) {
            occupyingPiece.draw(graphics);
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + xCoordinate;
        result = prime * result + yCoordinate;
        return result;
    }

}
