package cs5004.marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.view.MarbleSolitaireView;

/**
 * This class represents a controller for the Marble Solitaire game.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private final MarbleSolitaireModel model;
  private final MarbleSolitaireView view;
  private final Scanner scanner;

  /**
   * Constructs a MarbleSolitaireControllerImpl.
   *
   * @param model the MarbleSolitaireModel object
   * @param view the MarbleSolitaireView object
   * @param readable the Readable object
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model, MarbleSolitaireView view,
                                       Readable readable) throws IllegalArgumentException {
    if (model == null || view == null || readable == null) {
      throw new IllegalArgumentException("None of the arguments can be null");
    }
    this.model = model;
    this.view = view;
    this.scanner = new Scanner(readable);
  }

  @Override
  public void playGame() throws IllegalStateException {
    while (!model.isGameOver()) {
      // Render the current state of the game
      try {
        view.renderBoard();
        view.renderMessage("\nScore: " + model.getScore() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("Error rendering the game state or score", e);
      }

      int fromRow = 0;
      int fromCol = 0;
      int toRow = 0;
      int toCol = 0;

      // Obtain user input
      try {
        if (!scanner.hasNext()) {
          throw new IllegalStateException("Readable ran out of inputs");
        }

        while (!scanner.hasNextInt()) {
          if (scanner.hasNext("q|Q")) {
            view.renderMessage("Game quit!\nState of game when quit:\n");
            view.renderBoard();
            view.renderMessage("\nScore: " + model.getScore() + "\n");
            return;
          } else {
            view.renderMessage("Unexpected value, please enter a positive number or 'q' to quit\n");
            scanner.next();
          }
        }

        fromRow = scanner.nextInt();

        if (!scanner.hasNext()) {
          throw new IllegalStateException("Readable ran out of inputs");
        }

        while (!scanner.hasNextInt()) {
          if (scanner.hasNext("q|Q")) {
            view.renderMessage("Game quit!\nState of game when quit:\n");
            view.renderBoard();
            view.renderMessage("\nScore: " + model.getScore() + "\n");
            return;
          } else {
            view.renderMessage("Unexpected value, enter a positive number or 'q' to quit\n");
            scanner.next();
          }
        }

        fromCol = scanner.nextInt();

        if (!scanner.hasNext()) {
          throw new IllegalStateException("Readable ran out of inputs");
        }

        while (!scanner.hasNextInt()) {
          if (scanner.hasNext("q|Q")) {
            view.renderMessage("Game quit!\nState of game when quit:\n");
            view.renderBoard();
            view.renderMessage("\nScore: " + model.getScore() + "\n");
            return;
          } else {
            view.renderMessage("Unexpected value, enter a positive number or 'q' to quit\n");
            scanner.next();
          }
        }

        toRow = scanner.nextInt();

        if (!scanner.hasNext()) {
          throw new IllegalStateException("Readable ran out of inputs");
        }

        while (!scanner.hasNextInt()) {
          if (scanner.hasNext("q|Q")) {
            view.renderMessage("Game quit!\nState of game when quit:\n");
            view.renderBoard();
            view.renderMessage("\nScore: " + model.getScore() + "\n");
            return;
          } else {
            view.renderMessage("Unexpected value, enter a positive number or 'q' to quit\n");
            scanner.next();
          }
        }

        toCol = scanner.nextInt();

      } catch (IOException e) {
        throw new IllegalStateException("Error reading the user input", e);
      }

      // Adjust indices to be 0-based
      fromRow--;
      fromCol--;
      toRow--;
      toCol--;

      try {
        model.move(fromRow, fromCol, toRow, toCol);
      } catch (IllegalArgumentException e) {
        try {
          view.renderMessage("Invalid move. Play again. " + e.getMessage() + "\n");
        } catch (IOException ioException) {
          throw new IllegalStateException("Error rendering the invalid move message", ioException);
        }
      }
    }

    // Game over
    try {
      view.renderMessage("Game over!\n");
      view.renderBoard();
      view.renderMessage("\nScore: " + model.getScore() + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Error rendering the game over state or final score", e);
    }
  }


}
