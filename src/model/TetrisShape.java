package model;
import ui.panel.BoardPanel;

import java.awt.*;

public class TetrisShape {
    private int x = 4, y = 0;
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

    public TetrisShape(int[][] coords, BoardPanel boardPanel, Color color) {
        this.coords = coords;
        this.boardPanel = boardPanel;
        this.color = color;

        this.blockSize = BoardPanel.BLOCK_SIZE;
        this.boardHeight = BoardPanel.BOARD_HEIGHT;

        this.beginTime = System.currentTimeMillis();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void reset() {
        this.x = 4;
        this.y = 0;
        collision = false;
    }

    public void update() {
        if (collision) {
            // Fill the color for the board
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[0].length; col++) {
                    if (coords[row][col] != 0) {
                        boardPanel.getBoard()[y + row][x + col] = color;
                    }
                }
            }
            checkLine();
            // Set current shape
            boardPanel.setCurrentShape();
            return;
        }

        // Check moving horizontally
        boolean moveX = true;
        if (!(x + deltaX + coords[0].length > 10) && !(x + deltaX < 0)) {
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[row].length; col++) {
                    if (coords[row][col] != 0) {
                        if (boardPanel.getBoard()[y + row][x + deltaX + col] != null) {
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
            if (!(y + 1 + coords.length > boardHeight)) {
                for (int row = 0; row < coords.length; row++) {
                    for (int col = 0; col < coords[row].length; col++) {
                        if (coords[row][col] != 0) {
                            if (boardPanel.getBoard()[y + 1 + row][x + deltaX + col] != null) {
                                collision = true;
                            }
                        }
                    }
                }
                if (!collision) {
                    y++;
                }
            } else {
                collision = true;
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
                    if (boardPanel.getBoard()[y + row][x + col] != null) {
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
                if (coords[row][col] != 0) {
                    g.setColor(color);
                    g.fillRect(col * blockSize + x * blockSize, row * blockSize + y * blockSize, blockSize, blockSize);
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
        return y;
    }

    public int getX() {
        return x;
    }
}
