
import javax.swing.DefaultListModel;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class SetPanel extends JPanel {
    private Set data;
    JButton[] array = new JButton[4];

    public SetPanel(int index) {
        super();
        data = new Set(Card.rank[index]);

        for (int i = 0; i < array.length; i++) {
            array[i] = new JButton("   ");
            add(array[i]);
        }
    }

}

public class Computer implements ComputerInterface {
    int counterToStack = 0;

    @Override
    public boolean theComputerTurn(Deck cardDeck, Deck stackDeck, DefaultListModel p2Hand, JLabel topOfStack,
            JLabel deckPile, SetPanel[] setPanels) {
        // TODO Auto-generated method stub
        counterToStack += 1;

        if (!drawTurn(cardDeck, stackDeck, p2Hand, topOfStack, deckPile))
            return false;

        while (true) {
            Vector<Card> cardsSelected = playSet(p2Hand);

            if (!cardsSelected.isEmpty()) {
                for (int i = 0; i < cardsSelected.size(); i++) {
                    Card card = cardsSelected.get(i);
                    char rank = card.getRank();
                    char suit = card.getSuit();
                    int suitIndex = Card.getSuitIndex(suit);
                    int rankIndex = Card.getRankIndex(rank);
                    // setPanels[rankIndex].array[suitIndex].setText(card.toString());
                    System.out.println("laying " + card + " on a set");
                    setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
                }

            } else
                break;

        }
        if (!layOnStack(stackDeck, p2Hand, topOfStack)) {
            return false;
        }

        return true;

    }

    @Override
    public boolean drawTurn(Deck cardDeck, Deck Stack, DefaultListModel p2Hand, JLabel topOfStack, JLabel deckPile) {

        if (counterToStack % 3 == 0 && !Stack.isEmpty()) {

            Card card = Stack.removeCard();
            p2Hand.addElement(card);
            Card topCard = Stack.peek();
            System.out.println("Player 2 drew from the Stack");
            System.out.println("Hand now:");
            System.out.println(p2Hand.toString());
            if (topCard != null)
                topOfStack.setIcon(topCard.getCardImage());
            else
                topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));

            return true;
        } else {
            Card card = cardDeck.dealCard();
            p2Hand.addElement(card);
            System.out.println("Player 2 drew from the Deck");
            System.out.println("Hand now:");
            System.out.println(p2Hand.toString());
            if (cardDeck.getSizeOfDeck() == 0)
                deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));

            return !(cardDeck.getSizeOfDeck() == 0);
        }
    }

    @Override
    public Vector<Card> playSet(DefaultListModel p2Hand) {
        Vector<Card> theSet = new Vector<Card>();
        Vector<Integer> theIndexes = new Vector<Integer>();

        for (Integer i = 0; i < p2Hand.size(); i++) {
            Card card = (Card) p2Hand.get(i);

            for (int j = 0; j < p2Hand.size(); j++) {
                Card toCheck = (Card) p2Hand.get(j);
                if (toCheck.getRank() == card.getRank()) {
                    theIndexes.add(j);
                }
            }
            if (theIndexes.size() >= 3) {
                for (int j = 0; j < theIndexes.size(); j++) {
                    Card cardToSend = (Card) p2Hand.get(theIndexes.get(j));
                    theSet.add(cardToSend);
                    System.out.println("laying " + card + " on a Set");
                    p2Hand.remove(theIndexes.get(j));
                }
                return theSet;

            }
            theIndexes.clear();
        }
        return theSet;
    }

    @Override

    public boolean layOnStack(Deck Stack, DefaultListModel p2Hand, JLabel topOfStack) {
        if (p2Hand.get(0) == null) {
            return false;
        }

        Stack.addCard((Card) p2Hand.get(0));
        System.out.println("laying " + (Card) p2Hand.get(0) + " on the Stack");

        topOfStack.setIcon(((Card) p2Hand.get(0)).getCardImage());
        p2Hand.remove(0);
        System.out.println("Hand now: ");

        System.out.println(p2Hand.toString());

        if (p2Hand.get(0) == null) {
            return false;
        }

        return true;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
