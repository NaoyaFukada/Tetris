package ui.panel;
import model.TetrisShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Timer;

public class BoardPanel extends JPanel implements KeyListener {
    public static int STATE_GAME_PLAY = 0;
    public static int STATE_GAME_PAUSE = 1;
    public static int STATE_GAME_OVER = 2;

    private int state = STATE_GAME_PLAY;

    private static int FPS = 60;
    private static int delay = 1000 / FPS;

    private Timer looper;
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 29;
    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];

    private Random random;

    private TetrisShape[] shapes = new TetrisShape[7];
    private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"), Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};
    private TetrisShape currentShape;

    private int score = 0;

    public void incrementScore() {
        score += 100; // Increment score by 100 for each line cleared
    }

    public BoardPanel() {
        random = new Random();

        System.out.println("random: " + random);

        addKeyListener(this);

        // I-shape (Straight Tetromino)
        shapes[0] = new TetrisShape(new int[][]{
                {1, 1, 1, 1}
        }, this, colors[0]);

// T-shape (T-Tetromino)
        shapes[1] = new TetrisShape(new int[][]{
                {1, 1, 1},
                {0, 1, 0}
        }, this, colors[1]);

// L-shape (L-Tetromino)
        shapes[2] = new TetrisShape(new int[][]{
                {1, 1, 1},
                {1, 0, 0}
        }, this, colors[2]);

// J-shape (Reverse L-Tetromino)
        shapes[3] = new TetrisShape(new int[][]{
                {1, 1, 1},
                {0, 0, 1}
        }, this, colors[3]);

// S-shape (Skew Tetromino)
        shapes[4] = new TetrisShape(new int[][]{
                {0, 1, 1},
                {1, 1, 0}
        }, this, colors[4]);

// Z-shape (Reverse Skew Tetromino)
        shapes[5] = new TetrisShape(new int[][]{
                {1, 1, 0},
                {0, 1, 1}
        }, this, colors[5]);

// O-shape (Square Tetromino)
        shapes[6] = new TetrisShape(new int[][]{
                {1, 1},
                {1, 1}
        }, this, colors[6]);

        currentShape = shapes[0];

        // Function in here will run repeatedly with the specified delay.
        looper = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        looper.start();
    }

    private void update() {
        if (state == STATE_GAME_PLAY) {
            currentShape.update();
        }
    }

    public void setCurrentShape() {
        currentShape = shapes[random.nextInt(shapes.length)];
        currentShape.reset();
        checkOverGame();
    }

    private void checkOverGame() {
        int[][] coords = currentShape.getCoords();
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                int boardRow = row + currentShape.getY();
                int boardCol = col + currentShape.getX();

                // Check if the boardRow is within the valid range
                if (coords[row][col] != 0 && boardRow >= 0 && boardRow < BOARD_HEIGHT) {
                    if (board[boardRow][boardCol] != null) {
                        state = STATE_GAME_OVER;
                        return;
                    }
                }
            }
        }
    }


    public void setGameOver() {
        state = STATE_GAME_OVER;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // // Set the drawing color to black and fill the entire panel with it (background)
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Render the current falling shape (tetromino)
        currentShape.render(g);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    // Syntax: g.fillRect(int x, int y, int width, int height)
                    // X: The x-coordinate of the top-left corner of the rectangle.
                    // Y: The y-coordinate of the top-left corner of the rectangle.
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        // Draw the board grid
        g.setColor(Color.white);
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * row);
        }

        for (int col = 0; col < BOARD_WIDTH + 1; col++) {
            g.drawLine(col * BLOCK_SIZE, 0, col * BLOCK_SIZE, BLOCK_SIZE * BOARD_HEIGHT);
        }

        // Draw the score
        g.setColor(Color.white);
        // Syntax: g.drawString(String str, int x, int y);
        g.drawString("Score: " + score, 350, 50);

        if (state == STATE_GAME_OVER) {
            g.setColor(Color.white);
            g.drawString("GAME OVER (space key to restart)", 50, 200);
        }

        if (state == STATE_GAME_PAUSE) {
            g.setColor(Color.white);
            g.drawString("GAME PAUSED", 50, 200);
        }
    }

    public void resetBoard() {
        // Clear the board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = null;
            }
        }
    }

    public void setGamePlayState() {
        state = STATE_GAME_PLAY;
    }

    public Color[][] getBoard() {
        return board;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("Attempted to speed up");
            currentShape.speedUp(); // Move the shape down by 1 unit
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotateShape();
        }
        // Clean the board
        if (state == STATE_GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                resetBoard();
                setCurrentShape();
                setGamePlayState();
            }
        }

        // Pause the game
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (state == STATE_GAME_PLAY) {
                state = STATE_GAME_PAUSE;
            } else if (state == STATE_GAME_PAUSE) {
                state = STATE_GAME_PLAY;
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        // Empty method
    }
}
