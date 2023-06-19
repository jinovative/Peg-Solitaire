package cs5004.marblesolitaire.model.hw05;

public class EnglishSolitaireModel implements MarbleSolitaireModel{

  //      O O O
  //      O O O
  //  O O O O O O O
  //  O O O _ O O O
  //  O O O O O O O
  //      O O O
  //      O O O
  private int armThickness;
  private int size;
  private SlotState[][] board;

  public EnglishSolitaireModel() {
    this(3, 3, 3);
  }

  public EnglishSolitaireModel(int sRow, int sCol) {
    this(3, sRow, sCol);
  }

  public EnglishSolitaireModel(int armThickness) {
    this(armThickness, armThickness, armThickness);
  }

  public EnglishSolitaireModel(int armThickness, int sRow, int sCol) {
    if (armThickness <= 0 || armThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be a positive odd number");
    }

    if (!validCell(sRow, sCol, armThickness)) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
    }

    this.armThickness = armThickness;
    this.size = 2 * armThickness - 1;
    this.board = new SlotState[size][size];

    // Initialize the board with marbles and the empty slot
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (validPosition(row, col)) {
          board[row][col] = SlotState.Marble;
        } else if (row == sRow && col == sCol) {
          board[row][col] = SlotState.Empty;
        } else {
          board[row][col] = SlotState.Invalid;
        }
      }
    }
  }

  private boolean validPosition(int row, int col) {
    return row >= armThickness - 1 && row < size - (armThickness - 1) && col >= armThickness - 1 && col < size - (armThickness - 1);
  }

  private boolean validCell(int row, int col, int armThickness) {
    int emptyCellRange = 2 * (armThickness - 1) + 1;
    return row >= 0 && row < emptyCellRange && col >= 0 && col < emptyCellRange;
  }

  /**
   * Return the size of this board. The size is roughly the longest dimension of a board
   *
   * @return the size as an integer
   */
  @Override
  public int getBoardSize() {
    return size;
  }

  /**
   * Get the state of the slot at a given position on the board.
   *
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @return the state of the slot at the given row and column
   * @throws IllegalArgumentException if the row or the column are beyond
   *                                  the dimensions of the board
   */
  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= size || col < 0 || col >= size) {
      throw new IllegalArgumentException("Invalid position (" + row + "," + col + ")");
    }
    return board[row][col];
  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board
   */
  @Override
  public int getScore() {
    int marblesCount = 0;
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (board[row][col] == SlotState.Marble) {
          marblesCount++;
        }
      }
    }
    return marblesCount;
  }

  /**
   * Move a single marble from a given position to another given position.
   * A move is valid only if the from and to positions are valid. Specific
   * implementations may place additional constraints on the validity of a move.
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @throws IllegalArgumentException if the move is not possible
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    // Check if the from and to positions are valid
    if (!validPosition(fromRow, fromCol) || !validPosition(toRow, toCol)) {
      throw new IllegalArgumentException("Invalid move: position out of bounds");
    }

    // Check if the move is diagonal (one row and one column away)
    if (Math.abs(fromRow - toRow) != 1 || Math.abs(fromCol - toCol) != 1) {
      throw new IllegalArgumentException("Invalid move: not a diagonal move");
    }

    // Check if the from position contains a marble
    if (board[fromRow][fromCol] != SlotState.Marble) {
      throw new IllegalArgumentException("Invalid move: source position does not contain a marble");
    }

    // Check if the to position is empty
    if (board[toRow][toCol] != SlotState.Empty) {
      throw new IllegalArgumentException("Invalid move: destination position is not empty");
    }

    // Calculate the position of the marble that will be removed
    int removedRow = (fromRow + toRow) / 2;
    int removedCol = (fromCol + toCol) / 2;

    // Check if the position of the removed marble is valid
    if (!validPosition(removedRow, removedCol) || board[removedRow][removedCol] != SlotState.Marble) {
      throw new IllegalArgumentException("Invalid move: no marble to remove");
    }

    // Perform the move by updating the board
    board[fromRow][fromCol] = SlotState.Empty;
    board[toRow][toCol] = SlotState.Marble;
    board[removedRow][removedCol] = SlotState.Empty;
  }

  /**
   * Determine and return if the game is over or not. A game is over if no
   * more moves can be made.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    // Check if there are any valid moves available
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        // Check if the current position contains a marble
        if (board[row][col] == SlotState.Marble) {
          // Check if there are valid moves from this position
          if (validPosition(row, col)) {
            // Found a valid move, so the game is not over
            return false;
          }
        }
      }
    }
    // No valid moves found, so the game is over
    return true;
  }
}
