package cs5004.marblesolitaire.view;

import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs5004.marblesolitaire.model.hw05.EnglishSolitaireModel;
import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for the MarbleSolitaireTextView.
 */
public class MarbleSolitaireTextViewTest {

  private MarbleSolitaireTextView view;

  private MarbleSolitaireModelState modelState;
  private StringWriter appendable;

  @Before
  public void setUp() {
    modelState = new EnglishSolitaireModel() {
      @Override
      public int getBoardSize() {
        return 3;
      }

      @Override
      public SlotState getSlotAt(int row, int col) {
        return SlotState.Marble;
      }
    };

    appendable = new StringWriter();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullModelState() {
    new MarbleSolitaireTextView(null, appendable);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullAppendable() {
    new MarbleSolitaireTextView(modelState, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithBothNull() {
    new MarbleSolitaireTextView(null, null);
  }

  @Test
  public void testRenderBoard() {
    MarbleSolitaireView view = new MarbleSolitaireTextView(modelState, appendable);
    try {
      view.renderBoard();
      assertEquals("O O O\nO O O\nO O O", appendable.toString());
    } catch (IOException e) {
      fail("IOException was thrown");
    }
  }

  @Test
  public void testRenderMessage() {
    MarbleSolitaireView view = new MarbleSolitaireTextView(modelState, appendable);
    try {
      view.renderMessage("Hello World");
      assertEquals("Hello World", appendable.toString());
    } catch (IOException e) {
      fail("IOException was thrown");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderNullMessage() {
    MarbleSolitaireView view = new MarbleSolitaireTextView(modelState, appendable);
    try {
      view.renderMessage(null);
    } catch (IOException e) {
      fail("IOException was thrown");
    }
  }
}