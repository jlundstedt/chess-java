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

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    // Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";


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
        board = new Square[8][8];
        blackPieces = new LinkedList<>();
        whitePieces = new LinkedList<>();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

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

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    private void initializePieces() {
    	
        for (int x = 0; x < 8; x++) {
            board[1][x].put(new Pawn(0, board[1][x], RESOURCES_BPAWN_PNG));
            board[6][x].put(new Pawn(1, board[6][x], RESOURCES_WPAWN_PNG));
        }
        
        board[7][3].put(new Queen(1, board[7][3], RESOURCES_WQUEEN_PNG));
        board[0][3].put(new Queen(0, board[0][3], RESOURCES_BQUEEN_PNG));
        
        King bk = new King(0, board[0][4], RESOURCES_BKING_PNG);
        King wk = new King(1, board[7][4], RESOURCES_WKING_PNG);
        board[0][4].put(bk);
        board[7][4].put(wk);

        board[0][0].put(new Rook(0, board[0][0], RESOURCES_BROOK_PNG));
        board[0][7].put(new Rook(0, board[0][7], RESOURCES_BROOK_PNG));
        board[7][0].put(new Rook(1, board[7][0], RESOURCES_WROOK_PNG));
        board[7][7].put(new Rook(1, board[7][7], RESOURCES_WROOK_PNG));

        board[0][1].put(new Knight(0, board[0][1], RESOURCES_BKNIGHT_PNG));
        board[0][6].put(new Knight(0, board[0][6], RESOURCES_BKNIGHT_PNG));
        board[7][1].put(new Knight(1, board[7][1], RESOURCES_WKNIGHT_PNG));
        board[7][6].put(new Knight(1, board[7][6], RESOURCES_WKNIGHT_PNG));

        board[0][2].put(new Bishop(0, board[0][2], RESOURCES_BBISHOP_PNG));
        board[0][5].put(new Bishop(0, board[0][5], RESOURCES_BBISHOP_PNG));
        board[7][2].put(new Bishop(1, board[7][2], RESOURCES_WBISHOP_PNG));
        board[7][5].put(new Bishop(1, board[7][5], RESOURCES_WBISHOP_PNG));
        
        
        for(int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                blackPieces.add(board[y][x].getOccupyingPiece());
                whitePieces.add(board[7-y][x].getOccupyingPiece());
            }
        }
        
        checkmateDetector = new CheckmateDetector(this, whitePieces, blackPieces, wk, bk);
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[y][x];
                sq.paintComponent(g);
            }
        }

        if (currentPiece != null) {
            if ((currentPiece.getColor() == 1 && whiteTurn)
                    || (currentPiece.getColor() == 0 && !whiteTurn)) {
                final Image i = currentPiece.getImage();
                g.drawImage(i, currentX, currentY, null);
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
            List<Square> movable = checkmateDetector.getAllowableSquares(whiteTurn);

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
                    checkmateDetector.getAllowableSquares(whiteTurn);
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
        currentX = mouseEvent.getX() - 24;
        currentY = mouseEvent.getY() - 24;

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