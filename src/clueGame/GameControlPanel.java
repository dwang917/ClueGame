package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {

	// All JTextFields to create text
	private JTextField Roll;
	private JTextField turnText = new JTextField(20);
	private JTextField guessText;
	private JTextField resultText;

	// Initialize inner panels
	private JPanel rollPanel;
	private JPanel turnPanel;
	private JPanel guessPanel;
	private JPanel resultPanel;
	private KnownCardsPanel cardsPanel;

	JButton accuseButton;

	// initialize accuse panel
	JComboBox<String> roomBox, personBox, weaponBox;
	JTextField roomText, personText, weaponText;

	private HumanPlayer human;
	private int rollValue;

	private Board board;

	public GameControlPanel(Board board, KnownCardsPanel cardsPanel) {
		this.cardsPanel = cardsPanel;
		this.board = board;
		human = board.getHumanPlayer();
		// set up panel that houses all the panels
		setLayout(new GridLayout(2, 0));

		// add panels
		createUperPanel();
		lowerPanel();
	}

	public GameControlPanel() {
		board.getInstance();
		// set up panel that houses all the panels
		setLayout(new GridLayout(2, 0));

		// add panels
		createUperPanel();
		lowerPanel();
	}

	// start the first turn
	public void startTurn() {
		rollValue = dieRoll();
		// update the control panel
		update();
		human.start(board, rollValue);
	}

	// continue to the next turn
	private void nextTurn() {
		resultText.setBackground(null);
		guessText.setBackground(null);
		rollValue = dieRoll();
		// update the panel
		update();
		board.processTurn(rollValue);
	}

	// roll the die
	public int dieRoll() {
		return ((int) (Math.random() * board.PLAYER_NUM + 1));
	}

	// create top panel that holds panels for player turn, roll, and buttons
	private void createUperPanel() {

		JPanel top = new JPanel();
		top.setLayout(new GridLayout(1, 4));

		// Create panels and buttons in this panel
		createRollPanel();
		createTurnPanel();

		accuseButton = new JButton("Make Accusation");
		accuseButton.setOpaque(true);
		accuseButton.addActionListener(new AccuseListener());
		JButton nextButton = new JButton("NEXT!");
		nextButton.addActionListener(new NextListener());
		nextButton.setOpaque(true);

		// add panels to the top panel
		top.add(turnPanel);
		top.add(rollPanel);
		top.add(accuseButton);
		top.add(nextButton);

		// add the top panel to the housing panel
		add(top);
	}

	private void lowerPanel() {
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(0, 2));

		createGuessPanel();
		createResultPanel();
		bottom.add(guessPanel);
		bottom.add(resultPanel);

		add(bottom);
	}

	// create panel that houses the player's guess
	private void createGuessPanel() {
		// set up panel
		guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1, 0));
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

		// set up text field
		guessText = new JTextField(10);

		// add text to the guest panel
		guessPanel.add(guessText);
	}

	private void createResultPanel() {
		// set up panel
		resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1, 0));
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));

		// set up text field
		resultText = new JTextField(10);

		// add text to the guest panel
		resultPanel.add(resultText);
	}

	private void createTurnPanel() {
		// set up turn panel
		turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2, 1));

		// create label
		JLabel turnLabel = new JLabel("Whose turn?");

		// add label and text to panel
		turnPanel.add(turnLabel, BorderLayout.CENTER);
		turnPanel.add(turnText);

	}

	private void createRollPanel() {

		rollPanel = new JPanel(); // set up roll panel
		JLabel rollLabel = new JLabel("Roll"); // create label
		Roll = new JTextField(10);

		// add label and roll text to panel
		rollPanel.add(rollLabel);
		rollPanel.add(Roll);
	}

	private void createAccusationPanel(JFrame frame) {
		JButton submit, cancel;

		JPanel accuse = new JPanel();
		accuse.setLayout(new GridLayout(4, 2));

		roomText = new JTextField("Room");
		personText = new JTextField("Person");
		weaponText = new JTextField("Weapon");

		roomBox = new JComboBox<String>();
		personBox = new JComboBox<String>();
		weaponBox = new JComboBox<String>();

		for (Card card : board.getDeck()) {
			if (card.getType() == CardType.ROOM) {
				roomBox.addItem(card.getName());
			} else if (card.getType() == CardType.PERSON) {
				personBox.addItem(card.getName());
			} else if (card.getType() == CardType.WEAPON) {
				weaponBox.addItem(card.getName());
			}
		}

		submit = new JButton("Submit");
		submit.addActionListener(new SubmitListener());
		cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener());

		accuse.add(roomText);
		accuse.add(roomBox);
		accuse.add(personText);
		accuse.add(personBox);
		accuse.add(weaponText);
		accuse.add(weaponBox);
		accuse.add(submit);
		accuse.add(cancel);

		frame.add(accuse);
	}

	private void setGuessResult() {
		if (board.isSuggestionMade()) {
			if (board.getCurrentPlayer() instanceof HumanPlayer) {
				if (board.getDisproveCard() != null) {
					resultText.setText(board.getDisproveCard().getName() + " (from " + board.getDisprovePerson().getName() + ")");
					resultText.setBackground(board.getDisprovePerson().getColer());
				} else
					resultText.setText("Not Disproven");
			}

			else {
				if (board.getDisproveCard() != null) {
					resultText.setText("Disproven" + " (disproved by " + board.getDisprovePerson().getName() + ")");
					resultText.setBackground(board.getDisprovePerson().getColer());
				} else
					resultText.setText("Not Disproven");
			}
		} else {
			resultText.setText("");
		}
	}

	private void setGuess() {
		Solution guess = board.getGuess();
		if (guess != null) {
			guessText.setText(guess.getRoom().getName() + ", " + guess.getPerson().getName() + ", "
					+ guess.getWeapon().getName());
			guessText.setBackground(board.getCurrentPlayer().getColer());
		} else
			guessText.setText("");
	}

	// update the roll and player on the panel
	public void update() {
		turnText.setText(board.getCurrentPlayer().getName());
		turnText.setBackground(board.getCurrentPlayer().getColer());
		String roll = "";
		roll += rollValue;
		Roll.setText(roll);
		setGuess();
		setGuessResult();
		repaint();
	}

	/*
	 * public static void main(String[] args) { GameControlPanel panel = new
	 * GameControlPanel();
	 * 
	 * JFrame frame = new JFrame(); frame.setTitle("Clue Game");
	 * frame.setContentPane(panel); frame.setSize(750, 800);
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setVisible(true);
	 * 
	 * panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.orange, 0,0), 5);
	 * panel.setGuess( "I have no guess!"); panel.setGuessResult(
	 * "So you have nothing?"); }
	 */

	private class NextListener implements ActionListener {

		// if the current turn is finished, start the next turn
		@Override
		public void actionPerformed(ActionEvent e) {
			if (board.isTurnFinished()) {
				board.newTurn();
				nextTurn();
			}
			// when the user tries to click next without finishing the turn,
			// display the splash screen
			else {
				JOptionPane.showMessageDialog(null, "Please finish your turn first.");

			}
		}
	}

	private class AccuseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame frame = new JFrame();
			frame.setSize(300, 300);
			frame.setTitle("Accuse");
			createAccusationPanel(frame);
			frame.setVisible(true);
			// JOptionPane.showConfirmDialog(paintingChild, aaHint);
		}
	}

	private class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (roomBox.getSelectedItem().equals(board.getSolution().getRoom().getName())) {
				if (personBox.getSelectedItem().equals(board.getSolution().getPerson().getName())) {
					if (weaponBox.getSelectedItem().equals(board.getSolution().getWeapon().getName())) {
						JOptionPane.showMessageDialog(null, "Congradulations! You won!");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Oh no! Your accusation was wrong! You lose!");
			}
			System.exit(0);
		}
	}

	private class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComponent comp = (JComponent) e.getSource();
			Window win = SwingUtilities.getWindowAncestor(comp);
			win.dispose();
		}
	}

}
