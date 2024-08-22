package model;

import ui.panel.BoardPanel;

import java.awt.*;
import java.util.Arrays;

public class TetrisShape {
    private float x = 4.0f, y = -1.0f; // Use float for smooth vertical movement
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

    public TetrisShape(int[][] coords, BoardPanel boardPanel, Color color) {
        this.coords = coords;
        this.boardPanel = boardPanel;
        this.color = color;
        System.out.println("Coords: " + Arrays.deepToString(coords) + " Color: " + color);

        this.blockSize = BoardPanel.BLOCK_SIZE;
        this.boardHeight = BoardPanel.BOARD_HEIGHT;

        System.out.println("Block Size: " + blockSize + " boardHeight: " + boardHeight);

        this.beginTime = System.currentTimeMillis();
    }

    public void reset() {
        this.x = 4.0f;
        this.y = -1.0f; // Start above the visible board
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

            if (System.currentTimeMillis() - timeOfLastCollision >= 300) { // 1-second delay
                // Fill the color for the board
                for (int row = 0; row < coords.length; row++) {
                    for (int col = 0; col < coords[0].length; col++) {
                        int boardRow = (int) y + row;
                        if (coords[row][col] != 0 && boardRow >= 0) {
                            boardPanel.getBoard()[boardRow][(int) x + col] = color;
                        }
                    }
                }
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

        if (System.currentTimeMillis() - beginTime > delayTimeForMovement) {
            // Vertical movement
            boolean moveY = true;
            if (!(y + 1 + coords.length > boardHeight)) {
                for (int row = 0; row < coords.length; row++) {
                    for (int col = 0; col < coords[row].length; col++) {
                        int boardRow = (int) (y + 1) + row;
                        if (coords[row][col] != 0 && boardRow >= 0) {
                            if (boardPanel.getBoard()[boardRow][(int) x + col] != null) {
                                collision = true;
                                moveY = false;
                                y = (int) y; // Align shape to the grid to avoid glitches
                            }
                        }
                    }
                }
                if (!collision && moveY) {
                    y += 1.0f; // Smooth movement increment
                }
            } else {
                collision = true;
                y = (int) y; // Align shape to the bottom row
            }

            beginTime = System.currentTimeMillis();
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
        // Draw the shape
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                if (coords[row][col] != 0 && y + row >= 0) { // Only draw if the row is visible
                    g.setColor(color);
                    g.fillRect(Math.round(col * blockSize + x * blockSize), Math.round(row * blockSize + y * blockSize), blockSize, blockSize);
                }
            }
        }
    }


    public int[][] getCoords() {
        return coords;
    }

    public void speedUp() {
        System.out.println("Speed Up!");
        delayTimeForMovement = fast;
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
