package models;

import java.util.LinkedList;
import java.util.List;

public class King extends Piece {

    King(int color, Square initialSquare, String imageFile) {
        super(color, initialSquare, imageFile);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();

        Square[][] gameBoard = board.getSquareArray();

        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if (!(i == 0 && k == 0)) {
                    try {
                        if (!gameBoard[y + k][x + i].isOccupied() ||
                                gameBoard[y + k][x + i].getOccupyingPiece().getColor()
                                        != this.getColor()) {
                            legalMoves.add(gameBoard[y + k][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }

        return legalMoves;
    }

}
