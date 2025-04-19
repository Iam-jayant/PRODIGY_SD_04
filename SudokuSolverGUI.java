import javax.swing.*;
import java.awt.*;

public class SudokuSolverGUI extends JFrame {
    private final JTextField[][] cells = new JTextField[9][9];

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver - Prodigy Edition");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        Font cellFont = new Font("Consolas", Font.BOLD, 20);

        // Build the 9x9 grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(cellFont);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                gridPanel.add(cells[row][col]);
            }
        }

        // Buttons
        JButton solveBtn = new JButton("Solve");
        solveBtn.addActionListener(e -> solve());

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> clearGrid());

        JPanel controlPanel = new JPanel();
        controlPanel.add(solveBtn);
        controlPanel.add(clearBtn);

        // Layout
        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void solve() {
        int[][] board = new int[9][9];

        try {
            // Read input from grid
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String text = cells[i][j].getText();
                    board[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
                }
            }

            if (solveSudoku(board)) {
                // Update grid with solution
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        cells[i][j].setText(String.valueOf(board[i][j]));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No solution exists for the given puzzle.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Only numbers 1-9 are allowed.");
        }
    }

    private void clearGrid() {
        for (JTextField[] row : cells)
            for (JTextField cell : row)
                cell.setText("");
    }

    // Core backtracking Sudoku solving logic
    private boolean solveSudoku(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num ||
                board[row - row % 3 + i / 3][col - col % 3 + i % 3] == num)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolverGUI::new);
    }
}
