package model;

import ui.panel.BoardPanel;

import java.awt.*;
import java.util.Arrays;

public class TetrisShape {
    private float x = 4.0f, y = 0.0f; // Use float for smooth vertical movement
    private int normal = 600;
    private int fast = 50;
    private int delayTimeForMovement = normal;
    private long beginTime;

    private int deltaX = 0;
    private boolean collision = false;

    private int[][] coords;
    private BoardPanel boardPanel;
    private Color color;

    // Adding BLOCK_SIZE and BOARD_HEIGHT references
    private int blockSize;
    private int boardHeight;

    private long timeOfLastCollision = 0; // To track the time when the last shape collided
    private boolean isWaiting = false;

    private int progress = 0; // Track progress towards the next row
    private final int progressIncrement = 5; // Adjust this for smoother/faster movement
    private final int progressThreshold = 100; // When progress reaches this, move to the next row

    public TetrisShape(int[][] coords, BoardPanel boardPanel, Color color) {
        this.coords = coords;
        this.boardPanel = boardPanel;
        this.color = color;

        this.blockSize = BoardPanel.BLOCK_SIZE;
        this.boardHeight = BoardPanel.BOARD_HEIGHT;

        this.beginTime = System.currentTimeMillis();
    }

    public void reset() {
        this.x = 4.0f;
        this.y = 0.0f; // Start above the visible board
        collision = false;

        // Check for collision immediately after spawning
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[row].length; col++) {
                int boardRow = (int) y + row;
                if (coords[row][col] != 0 && boardRow >= 0 && boardPanel.getBoard()[boardRow][(int) x + col] != null) {
                    boardPanel.setGameOver();
                    return;
                }
            }
        }
    }

    public void update() {
        if (collision) {
            if (!isWaiting) {
                isWaiting = true;
                timeOfLastCollision = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - timeOfLastCollision >= 300) {
                settleShape();
                checkLine();
                boardPanel.setCurrentShape(); // Set current shape to a new one
                isWaiting = false;
                return;
            } else {
                return; // Exit early to keep waiting
            }
        }

        // Check moving horizontally
        boolean moveX = true;
        if (!(x + deltaX + coords[0].length > 10) && !(x + deltaX < 0)) {
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[row].length; col++) {
                    int boardRow = (int) y + row;
                    if (coords[row][col] != 0 && boardRow >= 0) {
                        if (boardPanel.getBoard()[boardRow][(int) x + deltaX + col] != null) {
                            moveX = false;
                        }
                    }
                }
            }
            if (moveX) {
                x += deltaX;
            }
        }

        deltaX = 0;

        // Smooth vertical movement logic
        progress += progressIncrement;
        if (progress >= progressThreshold) {
            if (canMoveDown()) {
                y += 1.0f; // Smooth movement increment
                progress = 0; // Reset progress after moving down a row
            } else {
                collision = true;
                y = (int) y; // Align shape to the grid to avoid glitches
                settleShape();
                checkLine();
                boardPanel.setCurrentShape();
            }
        } else {
            // Perform immediate collision check considering the current yOffset
            if (!canMoveDown()) {
                collision = true;
                y = (int) y; // Align shape to the grid to avoid glitches
                settleShape();
                checkLine();
                boardPanel.setCurrentShape();
            }
        }

        // Ensure the shape is rendered at the correct position without delay
        boardPanel.repaint();
    }

    private void settleShape() {
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                if (coords[row][col] != 0) {
                    int boardRow = (int) y + row;
                    if (boardRow >= 0 && boardRow < boardHeight) {
                        boardPanel.getBoard()[boardRow][(int) x + col] = color;
                    }
                }
            }
        }
    }

    private void checkLine() {
        int bottomLine = boardPanel.getBoard().length - 1;
        for (int topLine = boardPanel.getBoard().length - 1; topLine > 0; topLine--) {
            int count = 0;
            for (int col = 0; col < boardPanel.getBoard()[0].length; col++) {
                if (boardPanel.getBoard()[topLine][col] != null) {
                    count++;
                }
                boardPanel.getBoard()[bottomLine][col] = boardPanel.getBoard()[topLine][col];
            }

            // Increment score only if a full line is cleared
            if (count == boardPanel.getBoard()[0].length) {
                boardPanel.incrementScore(); // Increment score when a line is cleared
            } else {
                bottomLine--;
            }
        }
    }

    public void rotateShape() {
        int[][] rotatedShape = transposeMatrix(coords);
        reverseRows(rotatedShape);
        // Check for right side and bottom
        if ((x + rotatedShape[0].length > BoardPanel.BOARD_WIDTH) || (y + rotatedShape.length > 20)) {
            return;
        }

        // Check for collision with other shapes before rotating
        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0) {
                    if (boardPanel.getBoard()[(int) y + row][(int) x + col] != null) {
                        return;
                    }
                }
            }
        }
        coords = rotatedShape;
    }

    private int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                temp[col][row] = matrix[row][col];
            }
        }
        return temp;
    }

    private void reverseRows(int[][] matrix) {
        int middle = matrix.length / 2;
        for (int row = 0; row < middle; row++) {
            int[] temp = matrix[row];
            matrix[row] = matrix[matrix.length - row - 1];
            matrix[matrix.length - row - 1] = temp;
        }
    }

    public void render(Graphics g) {
        int yOffset = progress * blockSize / progressThreshold;

        // Draw the shape with smooth vertical movement
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                // Check that the shape is within the visible board before drawing
                if (coords[row][col] != 0 && y + row >= 0) {
                    g.setColor(color);
                    g.fillRect(Math.round(col * blockSize + x * blockSize),
                            Math.round(row * blockSize + y * blockSize + yOffset),
                            blockSize, blockSize);
                }
            }
        }
    }

    public int[][] getCoords() {
        return coords;
    }

    public void speedUp() {
        if (!collision && canMoveDown()) {
            y += 1.0f; // Move the shape down by 1 unit
            progress = 0; // Reset progress to sync with immediate downward movement
        } else {
            collision = true; // Trigger collision if moving down isn't possible
            settleShape();
            checkLine();
            boardPanel.setCurrentShape();
        }
        // Ensure the shape is rendered at the correct position without delay
        boardPanel.repaint();
    }

    private boolean canMoveDown() {
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[row].length; col++) {
                if (coords[row][col] != 0) {
                    int nextRow = (int) (y + 1 + row); // Calculate next row with yOffset in mind
                    if (nextRow >= boardHeight ||
                            (nextRow >= 0 && boardPanel.getBoard()[nextRow][(int) x + col] != null)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void speedDown() {
        delayTimeForMovement = normal;
    }

    public void moveRight() {
        deltaX = 1;
    }

    public void moveLeft() {
        deltaX = -1;
    }

    public int getY() {
        return (int) y;
    }

    public int getX() {
        return (int) x;
    }
}
