package models;

import utils.ImageFileName;

import java.util.List;

public class Bishop extends Piece {

    Bishop(int color, Square initialSquare, ImageFileName imageFile) {
        super(color, initialSquare, imageFile);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        Square[][] gameBoard = board.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        return getDiagonalOccupations(gameBoard, x, y);
    }
}
