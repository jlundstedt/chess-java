package models;

import java.util.LinkedList;
import java.util.List;

public class Rook extends Piece {

    public Rook(int color, Square initialSquare, String imageFile) {
        super(color, initialSquare, imageFile);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] gameBoard = board.getSquareArray();

        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        int[] linearOccupations = getLinearOccupations(gameBoard, x, y);

        for (int i = linearOccupations[0]; i <= linearOccupations[1]; i++) {
            if (i != y) legalMoves.add(gameBoard[i][x]);
        }

        for (int i = linearOccupations[2]; i <= linearOccupations[3]; i++) {
            if (i != x) legalMoves.add(gameBoard[y][i]);
        }

        return legalMoves;
    }

}
