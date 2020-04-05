package models;

import java.util.List;
import java.util.LinkedList;

public class Pawn extends Piece {
    private boolean wasMoved;

    public Pawn(int color, Square initialSquare, String imageFile) {
        super(color, initialSquare, imageFile);
    }

    @Override
    public boolean canMove(Square square) {
        boolean b = super.canMove(square);
        wasMoved = true;
        return b;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();

        Square[][] gameBoard = board.getSquareArray();

        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        int c = this.getColor();

        if (c == 0) {
            if (!wasMoved) {
                if (!gameBoard[y + 2][x].isOccupied()) {
                    legalMoves.add(gameBoard[y + 2][x]);
                }
            }

            if (y + 1 < 8) {
                if (!gameBoard[y + 1][x].isOccupied()) {
                    legalMoves.add(gameBoard[y + 1][x]);
                }
            }

            if (x + 1 < 8 && y + 1 < 8) {
                if (gameBoard[y + 1][x + 1].isOccupied()) {
                    legalMoves.add(gameBoard[y + 1][x + 1]);
                }
            }

            if (x - 1 >= 0 && y + 1 < 8) {
                if (gameBoard[y + 1][x - 1].isOccupied()) {
                    legalMoves.add(gameBoard[y + 1][x - 1]);
                }
            }
        }

        if (c == 1) {
            if (!wasMoved) {
                if (!gameBoard[y - 2][x].isOccupied()) {
                    legalMoves.add(gameBoard[y - 2][x]);
                }
            }

            if (y - 1 >= 0) {
                if (!gameBoard[y - 1][x].isOccupied()) {
                    legalMoves.add(gameBoard[y - 1][x]);
                }
            }

            if (x + 1 < 8 && y - 1 >= 0) {
                if (gameBoard[y - 1][x + 1].isOccupied()) {
                    legalMoves.add(gameBoard[y - 1][x + 1]);
                }
            }

            if (x - 1 >= 0 && y - 1 >= 0) {
                if (gameBoard[y - 1][x - 1].isOccupied()) {
                    legalMoves.add(gameBoard[y - 1][x - 1]);
                }
            }
        }

        return legalMoves;
    }
}
