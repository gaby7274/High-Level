import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public interface ComputerInterface extends Comparable {

    int counterToStack = 0;

    public Computer();

    public boolean theComputerTurn(Proj2 table);

    public boolean drawTurn(Deck cardDeck, Deck Stack, DefaultListModel p2Hand, JLabel topOfStack, JLabel deckPile);

    public Vector<Card> playSet(DefaultListModel p2Hand);

    public boolean layOnStack(Deck Stack, DefaultListModel p2Hand, JLabel topOfStack);

    public int compareTo(Object o);
}