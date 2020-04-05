package models;

import utils.CheckmateDetector;
import view.GameWindow;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import static utils.ImageFileName.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    public static final int DIMENSION_SIZE = 400;
    public static final int BOARD_DIMENSION = 8;
    public static final int NUMBER_OF_PLAYERS = 2;
    public static final int COORDINATE_ADJUSTMENT = 24;
    // Resource location constants for piece images

    // Logical and graphical representations of board
    private final Square[][] board;
    private final GameWindow gameWindow;

    // List of pieces and whether they are movable
    final LinkedList<Piece> blackPieces;
    final LinkedList<Piece> whitePieces;

    private boolean whiteTurn;

    private Piece currentPiece;
    private int currentX;
    private int currentY;

    private CheckmateDetector checkmateDetector;

    public Board(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        board = new Square[BOARD_DIMENSION][BOARD_DIMENSION];
        blackPieces = new LinkedList<>();
        whitePieces = new LinkedList<>();
        setLayout(new GridLayout(BOARD_DIMENSION, BOARD_DIMENSION, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < BOARD_DIMENSION; x++) {
            for (int y = 0; y < BOARD_DIMENSION; y++) {
                int xMod = x % NUMBER_OF_PLAYERS;
                int yMod = y % NUMBER_OF_PLAYERS;

                if ((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                    this.add(board[x][y]);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                    this.add(board[x][y]);
                }
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(DIMENSION_SIZE, DIMENSION_SIZE));
        this.setMaximumSize(new Dimension(DIMENSION_SIZE, DIMENSION_SIZE));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(DIMENSION_SIZE, DIMENSION_SIZE));

        whiteTurn = true;
    }

    private void initializePieces() {
        for (int x = 0; x < BOARD_DIMENSION; x++) {
            board[1][x].put(new Pawn(0, board[1][x], BPAWN_PNG));
            board[6][x].put(new Pawn(1, board[6][x], WPAWN_PNG));
        }

        board[7][3].put(new Queen(1, board[7][3], WQUEEN_PNG));
        board[0][3].put(new Queen(0, board[0][3], BQUEEN_PNG));

        King blackKing = new King(0, board[0][4], BKING_PNG);
        King whiteKing = new King(1, board[7][4], WKING_PNG);
        board[0][4].put(blackKing);
        board[7][4].put(whiteKing);

        board[0][0].put(new Rook(0, board[0][0], BROOK_PNG));
        board[0][7].put(new Rook(0, board[0][7], BROOK_PNG));
        board[7][0].put(new Rook(1, board[7][0], WROOK_PNG));
        board[7][7].put(new Rook(1, board[7][7], WROOK_PNG));

        board[0][1].put(new Knight(0, board[0][1], BKNIGHT_PNG));
        board[0][6].put(new Knight(0, board[0][6], BKNIGHT_PNG));
        board[7][1].put(new Knight(1, board[7][1], WKNIGHT_PNG));
        board[7][6].put(new Knight(1, board[7][6], WKNIGHT_PNG));

        board[0][2].put(new Bishop(0, board[0][2], BBISHOP_PNG));
        board[0][5].put(new Bishop(0, board[0][5], BBISHOP_PNG));
        board[7][2].put(new Bishop(1, board[7][2], WBISHOP_PNG));
        board[7][5].put(new Bishop(1, board[7][5], WBISHOP_PNG));


        for (int y = 0; y < NUMBER_OF_PLAYERS; y++) {
            for (int x = 0; x < BOARD_DIMENSION; x++) {
                blackPieces.add(board[y][x].getOccupyingPiece());
                whitePieces.add(board[7 - y][x].getOccupyingPiece());
            }
        }

        checkmateDetector = new CheckmateDetector(this, whitePieces, blackPieces, whiteKing, blackKing);
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        for (int x = 0; x < BOARD_DIMENSION; x++) {
            for (int y = 0; y < BOARD_DIMENSION; y++) {
                Square square = board[y][x];
                square.paintComponent(graphics);
            }
        }

        if (currentPiece != null) {
            if ((currentPiece.getColor() == 1 && whiteTurn)
                    || (currentPiece.getColor() == 0 && !whiteTurn)) {
                final Image i = currentPiece.getImage();
                graphics.drawImage(i, currentX, currentY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();

        Square square = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (square.isOccupied()) {
            currentPiece = square.getOccupyingPiece();
            if (currentPiece.getColor() == 0 && whiteTurn)
                return;
            if (currentPiece.getColor() == 1 && !whiteTurn)
                return;
            square.setDisplay(false);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        Square square = (Square) this.getComponentAt(new Point(mouseEvent.getX(), mouseEvent.getY()));

        if (currentPiece != null) {
            if (currentPiece.getColor() == 0 && whiteTurn)
                return;
            if (currentPiece.getColor() == 1 && !whiteTurn)
                return;

            List<Square> legalMoves = currentPiece.getLegalMoves(this);
            List<Square> movable = checkmateDetector.getAllowableSquares();

            if (legalMoves.contains(square) && movable.contains(square)
                    && checkmateDetector.testMove(currentPiece, square)) {
                square.setDisplay(true);
                currentPiece.canMove(square);
                checkmateDetector.update();

                if (checkmateDetector.blackCheckMated()) {
                    currentPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    gameWindow.checkmateOccurred(0);
                } else if (checkmateDetector.whiteCheckMated()) {
                    currentPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    gameWindow.checkmateOccurred(1);
                } else {
                    currentPiece = null;
                    whiteTurn = !whiteTurn;
                    checkmateDetector.getAllowableSquares();
                }

            } else {
                currentPiece.getPosition().setDisplay(true);
                currentPiece = null;
            }
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        currentX = mouseEvent.getX() - COORDINATE_ADJUSTMENT;
        currentY = mouseEvent.getY() - COORDINATE_ADJUSTMENT;

        repaint();
    }

    // Irrelevant methods, do nothing for these mouse behaviors
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

}