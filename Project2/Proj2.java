import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * This GUI assumes that you are using a 52 card deck and that you have 13 sets
 * in the deck. The GUI is simulating a playing table
 * 
 * @author Patti Ordonez editted by Gabriel A. Santiago Plaza
 */
public class Proj2 extends JFrame implements ActionListener {
	final static int numDealtCards = 9;
	JPanel player1;
	JPanel player2;
	JPanel deckPiles;
	JLabel deck;
	JLabel stack;
	JList p1HandPile;
	JList p2HandPile;
	Computer computer = new Computer();

	Deck cardDeck;
	Deck stackDeck;

	SetPanel[] setPanels = new SetPanel[13];
	JLabel topOfStack;
	JLabel deckPile;
	JButton p1Stack;
	JButton p2Stack;

	JButton p1Deck;
	JButton p2Deck;

	JButton p1Lay;
	JButton p2Lay;

	JButton p1LayOnStack;
	JButton p2LayOnStack;

	DefaultListModel p1Hand;
	DefaultListModel p2Hand;

	private void deal(Card[] cards) {
		for (int i = 0; i < cards.length; i++)
			cards[i] = (Card) cardDeck.dealCard();
	}

	public Proj2() {
		super("The Card Game of the Century");

		setLayout(new BorderLayout());
		setSize(1200, 700);

		cardDeck = new Deck();

		for (int i = 0; i < Card.suit.length; i++) {
			for (int j = 0; j < Card.rank.length; j++) {
				Card card = new Card(Card.suit[i], Card.rank[j]);
				cardDeck.addCard(card);
			}
		}
		cardDeck.shuffle();
		stackDeck = new Deck();

		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length; i++)
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));

		top.add(setPanels[0]);
		top.add(setPanels[1]);
		top.add(setPanels[2]);
		top.add(setPanels[3]);

		player1 = new JPanel();

		player1.add(top);

		add(player1, BorderLayout.NORTH);
		JPanel bottom = new JPanel();

		bottom.add(setPanels[4]);
		bottom.add(setPanels[5]);
		bottom.add(setPanels[6]);
		bottom.add(setPanels[7]);
		bottom.add(setPanels[8]);

		player2 = new JPanel();

		player2.add(bottom);
		add(player2, BorderLayout.SOUTH);

		JPanel middle = new JPanel(new GridLayout(1, 3));

		p1Stack = new JButton("Stack");
		p1Stack.addActionListener(this);
		p1Deck = new JButton("Deck ");
		p1Deck.addActionListener(this);
		p1Lay = new JButton("Lay a Set ");
		p1Lay.addActionListener(this);
		p1LayOnStack = new JButton("LayOnStack");
		p1LayOnStack.addActionListener(this);

		Card[] cardsPlayer1 = new Card[numDealtCards];
		deal(cardsPlayer1);
		p1Hand = new DefaultListModel();
		for (int i = 0; i < cardsPlayer1.length; i++)
			p1Hand.addElement(cardsPlayer1[i]);
		p1HandPile = new JList(p1Hand);

		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack));
		p1Lay.setVisible(false);
		p1LayOnStack.setVisible(false);
		deckPiles = new JPanel();
		deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
		deckPiles.add(Box.createGlue());
		JPanel left = new JPanel();
		left.setAlignmentY(Component.CENTER_ALIGNMENT);

		stack = new JLabel("Stack");
		stack.setAlignmentY(Component.CENTER_ALIGNMENT);

		left.add(stack);
		topOfStack = new JLabel();
		topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(topOfStack);
		deckPiles.add(left);
		deckPiles.add(Box.createGlue());

		JPanel right = new JPanel();
		right.setAlignmentY(Component.CENTER_ALIGNMENT);

		deck = new JLabel("Deck");

		deck.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deck);
		deckPile = new JLabel();
		deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
		deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deckPile);
		deckPiles.add(right);
		deckPiles.add(Box.createGlue());
		middle.add(deckPiles);

		p2Stack = new JButton("Stack");
		p2Stack.addActionListener(this);
		p2Deck = new JButton("Deck ");
		p2Deck.addActionListener(this);
		p2Lay = new JButton("Lay a Set");
		p2Lay.addActionListener(this);
		p2LayOnStack = new JButton("LayOnStack");
		p2LayOnStack.addActionListener(this);

		Card[] cardsPlayer2 = new Card[numDealtCards];
		deal(cardsPlayer2);
		p2Hand = new DefaultListModel();

		for (int i = 0; i < cardsPlayer2.length; i++)
			p2Hand.addElement(cardsPlayer2[i]);

		p2HandPile = new JList(p2Hand);

		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack));
		p2HandPile.setEnabled(false);
		p2Stack.setEnabled(false);
		p2Lay.setEnabled(false);
		p2LayOnStack.setEnabled(false);
		p2Deck.setEnabled(false);

		add(middle, BorderLayout.CENTER);

		JPanel leftBorder = new JPanel(new GridLayout(2, 1));

		setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
		setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
		leftBorder.add(setPanels[9]);
		leftBorder.add(setPanels[10]);
		add(leftBorder, BorderLayout.WEST);

		JPanel rightBorder = new JPanel(new GridLayout(2, 1));

		setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
		setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
		rightBorder.add(setPanels[11]);
		rightBorder.add(setPanels[12]);
		add(rightBorder, BorderLayout.EAST);
		System.out.println("Player 1's Turn");

	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (p1Deck == src) {

			Card card = cardDeck.dealCard();

			if (card != null) {
				if (src == p1Deck) {
					p1Hand.addElement(card);
					p1Lay.setVisible(true);
					p1Stack.setVisible(false);
					p1Deck.setVisible(false);
					p1LayOnStack.setVisible(true);
				} else
					p2Hand.addElement(card);
			}
			if (cardDeck.getSizeOfDeck() == 0) {
				deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
				if (deckIsOver(p1Hand, p2Hand)) {
					JOptionPane.showMessageDialog(null, "Player 1 WON!!");
					System.out.println("Player 1 WON!!");
				} else {
					JOptionPane.showMessageDialog(null, "Player 2 WON!!");
					System.out.println("Player 2 WON!!");

				}
			}
		}
		if (p1Stack == src) {

			Card card = stackDeck.removeCard();

			if (card != null) {
				Card topCard = stackDeck.peek();
				if (topCard != null)
					topOfStack.setIcon(topCard.getCardImage());
				else
					topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));

				p1Hand.addElement(card);
				p1Lay.setVisible(true);
				p1Stack.setVisible(false);
				p1Deck.setVisible(false);
				p1LayOnStack.setVisible(true);

			}

		}

		if (p1Lay == src) {
			Object[] cards = p1HandPile.getSelectedValues();
			System.out.println(cards.toString());
			if (cards.length >= 2) {
				Card cardRankToSee = (Card) cards[0];
				int rank = cardRankToSee.getRank();
				Boolean flag = true;
				for (int i = 0; i < cards.length; i++) {

					Card card = (Card) cards[i];
					if (card.getRank() != rank) {
						flag = false;
						break;
					}

				}
				if (flag) {
					for (int i = 0; i < cards.length; i++) {
						Card card = (Card) cards[i];
						layCard(card);
						p1Hand.removeElement(card);
					}
				} else {
					JOptionPane.showMessageDialog(null, "You have to select at least 3 of the same rank to lay a set");
					System.out.println("Player 1 selected cards that are not the same rank");

				}

			} else {
				JOptionPane.showMessageDialog(null, "You got to have select at least 3 of the same rank to lay a set");
				System.out.println("Player 1 selected less than 3 cards to lay on a set");
			}
		}

		if (p1LayOnStack == src) {
			int[] num = p1HandPile.getSelectedIndices();
			if (num.length == 1) {
				Object obj = p1HandPile.getSelectedValue();
				if (obj != null) {
					p1Hand.removeElement(obj);
					Card card = (Card) obj;
					stackDeck.addCard(card);
					topOfStack.setIcon(card.getCardImage());
					p1Lay.setVisible(false);
					p1LayOnStack.setVisible(false);
					if (p1Hand.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Player 1 WON!!");
						System.out.println("Player 1 WON!!");
					}
					System.out.println("Player 2's turn");
					if (!computer.theComputerTurn(this)) {
						if (cardDeck.getSizeOfDeck() == 0) {
							if (deckIsOver(p1Hand, p2Hand)) {
								JOptionPane.showMessageDialog(null, "Player 1 WON!!");
								System.out.println("Player 1 WON!!");
							} else {
								JOptionPane.showMessageDialog(null, "Player 2 WON!!");
								System.out.println("Player 2 WON!!");
							}
						} else if (p2Hand.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Player 2 WON!!");
							System.out.println("Player 2 WON!!");
						}
					} else
						System.out.println("Player 1's turn");

					p1Deck.setVisible(true);
					p1Stack.setVisible(true);
				}
			}
		}

	}

	public static void main(String args[]) {
		Proj2 t = new Proj2();
		t.setVisible(true);
	}

	void layCard(Card card) {
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex = Card.getSuitIndex(suit);
		int rankIndex = Card.getRankIndex(rank);
		// setPanels[rankIndex].array[suitIndex].setText(card.toString());
		System.out.println("laying " + card);
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());
	}

	boolean deckIsOver(DefaultListModel p1Hand, DefaultListModel p2Hand) {
		int scoreP1 = 0;
		int scoreP2 = 0;

		for (int i = 0; i < p1Hand.getSize(); i++) {
			Card card = (Card) p1Hand.get(i);
			scoreP1 += card.getRank();

		}
		for (int i = 0; i < p2Hand.getSize(); i++) {
			Card card = (Card) p1Hand.get(i);
			scoreP2 += card.getRank();

		}
		return (scoreP1 < scoreP2);
	}
}

class HandPanel extends JPanel {

	public HandPanel(String name, JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack) {
		// model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
		// add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
		// add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);
		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}

}

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
