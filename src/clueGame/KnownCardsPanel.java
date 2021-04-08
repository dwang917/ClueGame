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
		JPanel peoplePanel = createPeoplePanel();
		JPanel roomsPanel = createRoomsPanel();
		JPanel weaponPanel = createWeaponsPanel();
		updatePanel(peoplePanel, CardType.PERSON);
		updatePanel(roomsPanel, CardType.ROOM);
		updatePanel(weaponPanel, CardType.WEAPON);
		add(peoplePanel);
		add(roomsPanel);
		add(weaponPanel);
	}

	private JPanel createPeoplePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		return panel;
	}

	private JPanel createRoomsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		return panel;
	}

	private JPanel createWeaponsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		return panel;
	}

	private void updatePanel(JPanel panel, CardType type) {
		panel.removeAll();
		JLabel handLabel = new JLabel("In Hand:");
		panel.add(handLabel);
		boolean handNotZero = false;
		for (Card card : board.getHumanPlayer().getHand()) {
			if (card.getType().equals(type)) {
				JTextField text = new JTextField(card.getName());
				panel.add(text);
				handNotZero = true;
			}
		}
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
			for (Card card : board.getHumanPlayer().getSeenCards()) {
				if (card.getType().equals(type)) {
					JTextField text = new JTextField(card.getName());
					panel.add(text);
				}
			}
		}
	}

	public static void main(String[] args) {
		Board b = Board.getInstance();
		// set the file names to use my config files
		b.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		b.initialize();
		//b.getHumanPlayer().addSeenCard(b.getHumanPlayer().getnotSeenCards().get(0));
		//b.getHumanPlayer().addSeenCard(b.getHumanPlayer().getnotSeenCards().get(6));
		//b.getHumanPlayer().addSeenCard(b.getHumanPlayer().getnotSeenCards().get(7));
		//b.getHumanPlayer().addSeenCard(b.getHumanPlayer().getnotSeenCards().get(12));
		
		while(!b.getHumanPlayer().getnotSeenCards().isEmpty()) {
			b.getHumanPlayer().addSeenCard(b.getHumanPlayer().getnotSeenCards().get(0));
		}

		KnownCardsPanel panel = new KnownCardsPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(180, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// test filling in the data
	}
}
