package cs5004.marblesolitaire.view;

import java.io.IOException;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState;

import static cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState.SlotState;

/**
 * This class implements the MarbleSolitaireView interface and represents the text-based view.
 */
public class MarbleSolitaireTextView implements MarbleSolitaireView {
  private MarbleSolitaireModelState modelState;
  private Appendable ap;

  /**
   * Constructs a new MarbleSolitaireTextView that outputs to the standard output.
   *
   * @param modelState the MarbleSolitaireModelState object representing the state of the game board
   * @throws IllegalArgumentException if the modelState is null
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState modelState) {
    this(modelState, System.out);
  }

  /**
   * Constructs a new MarbleSolitaireTextView with a specified Appendable object for output.
   *
   * @param modelState the MarbleSolitaireModelState object representing the state of the game board
   * @param ap the Appendable object that outputs will be sent to
   * @throws IllegalArgumentException if either the modelState or the Appendable object is null
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState modelState, Appendable ap) {
    if (modelState == null || ap == null) {
      throw new IllegalArgumentException("Neither Model state nor Appendable can be null");
    }
    this.modelState = modelState;
    this.ap = ap;
  }

  @Override
  public String toString() {
    int size = modelState.getBoardSize();
    StringBuilder stringBuilder = new StringBuilder();

    for (int row = 0; row < size; row++) {
      StringBuilder lineBuilder = new StringBuilder();

      for (int col = 0; col < size; col++) {
        SlotState slotState = modelState.getSlotAt(row, col);
        char slotChar;

        if (SlotState.Marble == slotState) {
          slotChar = 'O';
        } else if (slotState == SlotState.Empty) {
          slotChar = '_';
        } else {
          slotChar = ' ';
        }

        lineBuilder.append(slotChar);

        // Add space after the slot, except for the last slot in the row
        if (col < size - 1) {
          lineBuilder.append(' ');
        }
      }

      String line = lineBuilder.toString().stripTrailing();
      stringBuilder.append(line);

      // Add newline after each line, except for the last line
      if (row < size - 1) {
        stringBuilder.append('\n');
      }
    }

    return stringBuilder.toString();
  }

  /**
   * Render the board to the provided data destination. The board should be rendered exactly
   * in the format produced by the toString method above
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  public void renderBoard() throws IOException {
    ap.append(toString());
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }
    ap.append(message);
  }
}
