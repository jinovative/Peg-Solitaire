package cs5004.marblesolitaire.model.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for the EnglishSolitaireModel.
 */
public class EnglishSolitaireModelTest {
  private EnglishSolitaireModel model;

  @Test
  public void testNewGame() {
    model.move(1, 3, 3, 3); // Make a move to change the state of the game
    model.newGame(); // Reset the game
    assertEquals(0, model.getScore()); // Score should be reset
    assertFalse(model.isGameOver()); // Game should not be over
  }

  @Test
  public void testGetBoard() {
    MarbleSolitaireModelState.SlotState[][] board = model.getBoard();
    assertEquals(model.getBoardSize(), board.length);
    assertEquals(model.getBoardSize(), board[0].length);
  }

  @Test
  public void testGetBoardSize() {
    assertEquals(7, model.getBoardSize());
  }

  @Test
  public void testGetSlotAt() {
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Invalid, model.getSlotAt(0, 0));
  }

  @Test
  public void testGetScore() {
    assertEquals(32, model.getScore());
  }

  @Test
  public void testMove() {
    model.move(1, 3, 3, 3); // Valid move
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));

    // Invalid move
    try {
      model.move(2, 2, 2, 4);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid move", e.getMessage());
    }
  }

  @Test
  public void testIsGameOver() {
    assertFalse(model.isGameOver()); // Game should not be over initially

    // Move marbles to create a game over condition
    model.move(1, 3, 3, 3);
    model.move(2, 1, 2, 3);
    assertTrue(model.isGameOver()); // Game should be over
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArmThickness() {
    model = new EnglishSolitaireModel(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEmptyCellPosition() {
    model = new EnglishSolitaireModel(5, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEmptyCellPosition2() {
    model = new EnglishSolitaireModel(3, 0, 1);
  }

  @Test
  public void testInvalidGetSlotAt() {
    try {
      model.getSlotAt(3, 1); // Expected marble, but empty slot returned
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position is returned as empty or invalid", e.getMessage());
    }

    try {
      model.getSlotAt(0, 0); // Expected marble, but invalid slot returned
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Given position is returned as empty or invalid", e.getMessage());
    }
  }
}