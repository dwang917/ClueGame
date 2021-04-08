package clueGame;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Constructor for the panel, it does 90% of the work
 */
public class KnownCardsPanel extends JPanel {
	private JPanel peoplePanel, roomPanel, weaponPanel;
	private Board board;

	public KnownCardsPanel() {
		board = Board.getInstance();
		setLayout(new GridLayout(0, 1));
		peoplePanel = createPanel("People");
		roomPanel = createPanel("Rooms");
		weaponPanel = createPanel("Weapons");
		//update the cards displayed in the panels
		updatePanel(peoplePanel, CardType.PERSON);
		updatePanel(roomPanel, CardType.ROOM);
		updatePanel(weaponPanel, CardType.WEAPON);
		add(peoplePanel);
		add(roomPanel);
		add(weaponPanel);
	}

	//Create panels for people, rooms and weapons
	private JPanel createPanel(String title) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		return panel;
	}

	//update the seen cards text fields and in hand text fields 
	private void updatePanel(JPanel panel, CardType type) {
		//clear the panel
		panel.removeAll();
		JLabel handLabel = new JLabel("In Hand:");
		panel.add(handLabel);
		boolean handNotZero = false;
		//if any card matches desired card type, create a text field
		for (Card card : board.getHumanPlayer().getHand()) {
			if (card.getType().equals(type)) {
				JTextField text = new JTextField(card.getName());
				panel.add(text);
				handNotZero = true;
			}
		}
		//if no cards in hand, create a non text field
		if(!handNotZero) {
			JTextField text = new JTextField("None");
			panel.add(text);
		}

		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		if (board.getHumanPlayer().getSeenCards().size() == 0) {
			JTextField text = new JTextField("None");
			panel.add(text);
		} else {
			//create text fields for seen cards
			for (Card card : board.getHumanPlayer().getSeenCards()) {
				if (card.getType().equals(type)) {
					JTextField text = new JTextField(card.getName());
					//color code the text fields according to players
					text.setBackground(card.getColor());
					panel.add(text);
				}
			}
		}
	}

	public static void main(String[] args) {
		Board b = Board.getInstance();
		b.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		b.initialize();
		Player humanPlayer = b.getHumanPlayer();
		//testing the panels with different seen cards
		//humanPlayer.addSeenCard(b.getHumanPlayer().getnotSeenCards().get(0));
		//humanPlayer.addSeenCard(b.getHumanPlayer().getnotSeenCards().get(6));
		//humanPlayer.addSeenCard(b.getHumanPlayer().getnotSeenCards().get(7));
		//humanPlayer).addSeenCard(b.getHumanPlayer().getnotSeenCards().get(12));
		
		//test the panel when all players' cards are seen
		while(!humanPlayer.getnotSeenCards().isEmpty()) {
			humanPlayer.addSeenCard(humanPlayer.getnotSeenCards().get(0));
		}

		KnownCardsPanel panel = new KnownCardsPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(180, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
