package models;

import utils.ImageFileName;

import java.util.List;
import java.util.LinkedList;

import static models.Board.BOARD_DIMENSION;

public class Pawn extends Piece {
    private boolean wasMoved;

    Pawn(int color, Square initialSquare, ImageFileName imageFile) {
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
        LinkedList<Square> legalMoves = new LinkedList<>();

        Square[][] gameBoard = board.getSquareArray();

        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        int color = this.getColor();

        if (color == 0) {
            if (!wasMoved) {
                if (!gameBoard[y + 2][x].isOccupied()) {
                    legalMoves.add(gameBoard[y + 2][x]);
                }
            }

            if (y + 1 < BOARD_DIMENSION) {
                if (!gameBoard[y + 1][x].isOccupied()) {
                    legalMoves.add(gameBoard[y + 1][x]);
                }
            }

            if (x + 1 < BOARD_DIMENSION && y + 1 < BOARD_DIMENSION) {
                if (gameBoard[y + 1][x + 1].isOccupied()) {
                    legalMoves.add(gameBoard[y + 1][x + 1]);
                }
            }

            if (x - 1 >= 0 && y + 1 < BOARD_DIMENSION) {
                if (gameBoard[y + 1][x - 1].isOccupied()) {
                    legalMoves.add(gameBoard[y + 1][x - 1]);
                }
            }
        }

        if (color == 1) {
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

            if (x + 1 < BOARD_DIMENSION && y - 1 >= 0) {
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
