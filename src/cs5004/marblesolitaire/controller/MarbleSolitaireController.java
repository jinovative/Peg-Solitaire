package cs5004.marblesolitaire.controller;

/**
 * This interface represents a Marble Solitaire Controller.
 * It includes a method to initiate the game play for Marble Solitaire.
 */
public interface MarbleSolitaireController {

  /**
   * Initiates and manages the play of the Marble Solitaire game.
   * It communicates with the Marble Solitaire Model and View to manage the gameplay.
   *
   * @throws IllegalStateException if the controller is unable to successfully receive input
   *                               or transmit output
   */
  void playGame();
}
