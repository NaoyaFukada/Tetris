package ui.panel;

import model.TetrisShape;

import javax.swing.*;
import java.awt.*;
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

    private boolean pause_showText = false;

    public void incrementScore() {
        score += 100; // Increment score by 100 for each line cleared
    }

    public BoardPanel() {
        random = new Random();
        setFocusable(true); // Ensure the panel can gain focus
        addKeyListener(this);

        // Initialize shapes
        shapes[0] = new TetrisShape(new int[][]{
                {1, 1, 1, 1}
        }, this, colors[0]);

        shapes[1] = new TetrisShape(new int[][]{
                {1, 1, 1},
                {0, 1, 0}
        }, this, colors[1]);

        shapes[2] = new TetrisShape(new int[][]{
                {1, 1, 1},
                {1, 0, 0}
        }, this, colors[2]);

        shapes[3] = new TetrisShape(new int[][]{
                {1, 1, 1},
                {0, 0, 1}
        }, this, colors[3]);

        shapes[4] = new TetrisShape(new int[][]{
                {0, 1, 1},
                {1, 1, 0}
        }, this, colors[4]);

        shapes[5] = new TetrisShape(new int[][]{
                {1, 1, 0},
                {0, 1, 1}
        }, this, colors[5]);

        shapes[6] = new TetrisShape(new int[][]{
                {1, 1},
                {1, 1}
        }, this, colors[6]);

        currentShape = shapes[0];

        looper = new Timer(delay, e -> {
            update();
            repaint();
        });
        looper.start();
    }

    private void update() {
        if (state == STATE_GAME_PLAY) {
            currentShape.update();
        }
    }

    public void setCurrentShape() {
        if (isTopRowFilled()) {
            setGameOver();
        } else {
            currentShape = shapes[random.nextInt(shapes.length)];
            currentShape.reset();
        }
    }

    private boolean isTopRowFilled() {
        for (int col = 0; col < BOARD_WIDTH; col++) {
            if (board[0][col] != null) {
                System.out.println("Column " + col + " in the top row is filled with: " + board[0][col]);
                return true;
            }
        }
        return false;
    }

    public void reset_score() {
        score = 0;
    }

    public void setGameOver() {
        state = STATE_GAME_OVER;
    }

    public void pauseGame() {
        state = STATE_GAME_PAUSE;
    }

    public void resumeGame() {
        state = STATE_GAME_PLAY;
    }

    public boolean isGameRunning() {
        return state == STATE_GAME_PLAY;
    }

    public boolean isGamePaused() {
        return state == STATE_GAME_PAUSE;
    }

    public boolean isGamePauseByP() {
        return pause_showText;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        currentShape.render(g);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        g.setColor(Color.white);
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * row);
        }

        for (int col = 0; col < BOARD_WIDTH + 1; col++) {
            g.drawLine(col * BLOCK_SIZE, 0, col * BLOCK_SIZE, BLOCK_SIZE * BOARD_HEIGHT);
        }

        g.setColor(Color.white);
        g.drawString("Score: " + score, 350, 50);

        if (state == STATE_GAME_OVER) {
            g.setColor(Color.white);
            g.drawString("GAME OVER (space key to restart)", 50, 200);
        }

        if (state == STATE_GAME_PAUSE && pause_showText) {
            g.setColor(Color.white);
            g.drawString("GAME PAUSED (Press P to continue)", 50, 200);
        }
    }

    public void resetBoard() {
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
            currentShape.speedUp();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotateShape();
        }

        if (state == STATE_GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                resetBoard();
                setCurrentShape();
                setGamePlayState();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (state == STATE_GAME_PLAY) {
                pause_showText = true;
                pauseGame();
            } else if (state == STATE_GAME_PAUSE) {
                pause_showText = false;
                resumeGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Empty method
    }

    @Override
    public boolean requestFocusInWindow() {
        boolean focused = super.requestFocusInWindow();
        System.out.println("BoardPanel focus: " + focused);
        return focused;
    }
}
