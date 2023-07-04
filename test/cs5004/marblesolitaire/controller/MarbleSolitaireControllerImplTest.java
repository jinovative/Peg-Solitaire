package cs5004.marblesolitaire.controller;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;

import cs5004.marblesolitaire.model.hw05.EnglishSolitaireModel;
import cs5004.marblesolitaire.view.MarbleSolitaireTextView;
import cs5004.marblesolitaire.view.MarbleSolitaireView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MarbleSolitaireControllerImplTest {
  private EnglishSolitaireModel model;
  private MarbleSolitaireView view;
  private StringWriter appendable;
  private MarbleSolitaireControllerImpl controller;

  @Before
  public void setUp() {
    model = new EnglishSolitaireModel();
    appendable = new StringWriter();
    view = new MarbleSolitaireTextView(model, appendable);
  }

  @Test
  public void testNormalGamePlay() {
    StringReader input = new StringReader("4 2 4 4\n 4 4 4 2\nq\n");
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
    String expected = "Game quit!\nState of game when quit:\n";
    String result = appendable.toString();
    assertTrue(result.contains(expected));
  }

  @Test
  public void testQuitGamePlay() {
    StringReader input = new StringReader("4 2 4 4\nq");
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
    String expected = "Game quit!\nState of game when quit:\n    _ _ _\n    _ _ _\n_ _ _ _ _ _ _\n_ _ _ O _ _ _\n_ _ _ _ _ _ _\n    _ _ _\n    _ _ _\nScore: ";
    String result = appendable.toString();
    assertFalse(result.contains(expected));
  }

  @Test(expected = IllegalStateException.class)
  public void testReadableFails() {
    StringReader input = new StringReader("4 2 4");
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullArguments() {
    new MarbleSolitaireControllerImpl(null, null, null);
  }

  @Test
  public void testInvalidMove() {
    StringReader input = new StringReader("1 1 1 1\nq\n");
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
    String expected = "Invalid move. Play again.";
    String result = appendable.toString();
    assertTrue(result.contains(expected));
  }

  @Test(expected = RuntimeException.class)
  public void testViewFailsToRender() {
    StringReader input = new StringReader("4 2 4 4\n4 4 4 2\n");
    StringWriter failWriter = new StringWriter() {
      @Override
      public void write(String str) {
        try {
          throw new IOException("Test forced failure");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    };
    view = new MarbleSolitaireTextView(model, failWriter);
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
  }

  @Test
  public void testInputQInsteadOfRow() {
    StringReader input = new StringReader("q\n");
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
    String expected = "Game quit!\nState of game when quit:";
    String result = appendable.toString();
    assertTrue(result.contains(expected));
  }

  @Test
  public void testInputQInsteadOfColumn() {
    StringReader input = new StringReader("4 q\n");
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
    String expected = "Game quit!\nState of game when quit:";
    String result = appendable.toString();
    assertTrue(result.contains(expected));
  }

  @Test
  public void testInputGarbageInsteadOfRow() {
    StringReader input = new StringReader("garbage\nq\n");
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
    String expected = "Unexpected value, please enter a positive number or 'q' to quit";
    String result = appendable.toString();
    assertTrue(result.contains(expected));
  }

  @Test
  public void testInputGarbageInsteadOfColumn() {
    StringReader input = new StringReader("4 garbage\nq\n");
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
    String expected = "Unexpected value, enter a positive number or 'q' to quit";
    String result = appendable.toString();
    assertTrue(result.contains(expected));
  }

  @Test(expected = IllegalStateException.class)
  public void testInputEndsAbruptly() {
    StringReader input = new StringReader("4 2");
    controller = new MarbleSolitaireControllerImpl(model, view, input);
    controller.playGame();
  }
}