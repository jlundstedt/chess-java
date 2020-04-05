package models;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public abstract class Piece {
    private final int color;
    private Square currentSquare;
    private BufferedImage image;

    public Piece(int color, Square initialSquare, String imageFile) {
        this.color = color;
        this.currentSquare = initialSquare;

        try {
            this.image = ImageIO.read(getClass().getResource(imageFile));
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean canMove(Square square) {
        Piece occupyingPiece = square.getOccupyingPiece();

        if (occupyingPiece != null) {
            if (occupyingPiece.getColor() == this.color) {
                return false;
            } else square.capture(this);
        }

        currentSquare.removePiece();
        this.currentSquare = square;
        currentSquare.put(this);
        return true;
    }

    public Square getPosition() {
        return currentSquare;
    }

    void setPosition(Square square) {
        this.currentSquare = square;
    }

    public int getColor() {
        return color;
    }

    Image getImage() {
        return image;
    }

    void draw(Graphics graphics) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();

        graphics.drawImage(this.image, x, y, null);
    }

    int[] getLinearOccupations(Square[][] board, int x, int y) {
        int lastYabove = 0;
        int lastXright = 7;
        int lastYbelow = 7;
        int lastXleft = 0;

        for (int i = 0; i < y; i++) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                    lastYabove = i;
                } else lastYabove = i + 1;
            }
        }

        for (int i = 7; i > y; i--) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                    lastYbelow = i;
                } else lastYbelow = i - 1;
            }
        }

        for (int i = 0; i < x; i++) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                    lastXleft = i;
                } else lastXleft = i + 1;
            }
        }

        for (int i = 7; i > x; i--) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                    lastXright = i;
                } else lastXright = i - 1;
            }
        }

        return new int[]{lastYabove, lastYbelow, lastXleft, lastXright};
    }

    List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        LinkedList<Square> diagonalOccupations = new LinkedList<>();

        int xNW = x - 1;
        int xSW = x - 1;
        int xNE = x + 1;
        int xSE = x + 1;
        int yNW = y - 1;
        int ySW = y + 1;
        int yNE = y - 1;
        int ySE = y + 1;

        while (xNW >= 0 && yNW >= 0) {
            if (board[yNW][xNW].isOccupied()) {
                if (board[yNW][xNW].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagonalOccupations.add(board[yNW][xNW]);
                    break;
                }
            } else {
                diagonalOccupations.add(board[yNW][xNW]);
                yNW--;
                xNW--;
            }
        }

        while (xSW >= 0 && ySW < 8) {
            if (board[ySW][xSW].isOccupied()) {
                if (board[ySW][xSW].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagonalOccupations.add(board[ySW][xSW]);
                    break;
                }
            } else {
                diagonalOccupations.add(board[ySW][xSW]);
                ySW++;
                xSW--;
            }
        }

        while (xSE < 8 && ySE < 8) {
            if (board[ySE][xSE].isOccupied()) {
                if (board[ySE][xSE].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagonalOccupations.add(board[ySE][xSE]);
                    break;
                }
            } else {
                diagonalOccupations.add(board[ySE][xSE]);
                ySE++;
                xSE++;
            }
        }

        while (xNE < 8 && yNE >= 0) {
            if (board[yNE][xNE].isOccupied()) {
                if (board[yNE][xNE].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagonalOccupations.add(board[yNE][xNE]);
                    break;
                }
            } else {
                diagonalOccupations.add(board[yNE][xNE]);
                yNE--;
                xNE++;
            }
        }

        return diagonalOccupations;
    }

    // No implementation, to be implemented by each subclass
    public abstract List<Square> getLegalMoves(Board board);
}