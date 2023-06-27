package cs5004.marblesolitaire.view;

import org.junit.Before;
import org.junit.Test;

import cs5004.marblesolitaire.model.hw05.EnglishSolitaireModel;
import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the MarbleSolitaireTextView.
 */
public class MarbleSolitaireTextViewTest {

  private MarbleSolitaireTextView view;

  @Before
  public void setUp() {
    MarbleSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireModelState modelState = (MarbleSolitaireModelState) model;
    view = new MarbleSolitaireTextView(modelState);
  }

  @Test
  public void testToString() {
    String expected =
                              "    O O O\n"
                            + "    O O O\n"
                            + "O O O O O O O\n"
                            + "O O O _ O O O\n"
                            + "O O O O O O O\n"
                            + "    O O O\n"
                            + "    O O O";

    String actual = view.toString();

    assertEquals(expected, actual);
  }
}