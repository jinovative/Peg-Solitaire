package cs5004.marblesolitaire.view;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState;

import static cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState.*;

public class MarbleSolitaireTextView implements MarbleSolitaireView{
  private MarbleSolitaireModelState modelState;

  /**
   * Constructor that takes a MarbleSolitaireModelState object.
   * Throws an IllegalArgumentException if the provided model is null.
   *
   * @param modelState the model state object to use
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState modelState) {
    if (modelState == null) {
      throw new IllegalArgumentException("Model state cannot be null");
    }
    this.modelState = modelState;
  }

  /**
   * Returns a string representation of the current state of the board.
   * The string has one line per row of the game board. Each slot on the
   * game board is represented by a single character: 'O' for a marble,
   * '_' for an empty slot, and a space for an invalid position. Slots in
   * a row are separated by a space. Each row has no space before the first
   * slot and after the last slot.
   *
   * @return the game state as a string
   */
  @Override
  public String toString() {
    int size = modelState.getBoardSize();
    StringBuilder stringBuilder = new StringBuilder();

    for (int row = 0; row < size; row++) {
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

        stringBuilder.append(slotChar);

        // Add space after the slot, except for the last slot in the row
        if (col < size - 1) {
          stringBuilder.append(' ');
        }
      }

      // Add newline after each row, except for the last row
      if (row < size - 1) {
        stringBuilder.append('\n');
      }
    }

    return stringBuilder.toString();
  }
}
