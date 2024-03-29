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

	JButton accuseButton;

	// initialize accuse panel
	JComboBox<String> roomBox, personBox, weaponBox;
	JTextField roomText, personText, weaponText;

	private HumanPlayer human;
	private int rollValue;

	private Board board;

	public GameControlPanel(Board board) {
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
		// check if a suggestion was made in the turn
		if (board.isSuggestionMade()) {
			// if this is a human player, display the disproving card
			if (board.getCurrentPlayer() instanceof HumanPlayer) {
				if (board.getDisproveCard() != null) {
					resultText.setText(
							board.getDisproveCard().getName() + " (from " + board.getDisprovePerson().getName() + ")");
					resultText.setBackground(board.getDisprovePerson().getColer());
				} else
					resultText.setText("no new clue");
			}

			else {
				if (board.getDisproveCard() != null) {
					// if this is a computer player, display the disproving player
					resultText.setText(
							"suggestion disproved" + " (disproved by " + board.getDisprovePerson().getName() + ")");
					resultText.setBackground(board.getDisprovePerson().getColer());
				} else
					resultText.setText("Not Disproved");
			}
		} else {
			resultText.setText("");
		}
	}

	private void setGuess() {
		Solution guess = board.getGuess();
		if (guess != null) {
			// display the guess made by players
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
			// if this is not human player's turn, display an error message
			if (!(board.getCurrentPlayer() instanceof HumanPlayer) || board.isHumanMoved()) {
				JOptionPane.showMessageDialog(null, "Please wait till your next turn to make an accusation.");
			} else {
				JFrame frame = new JFrame();
				frame.setSize(300, 300);
				frame.setTitle("Accuse");
				createAccusationPanel(frame);
				frame.setVisible(true);
			}
		}
	}

	private class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// we check the accusation directly by their names, it seems more efficient this
			// way
			String roomName = board.getSolution().getRoom().getName();
			String personName = board.getSolution().getPerson().getName();
			String weaponName = board.getSolution().getWeapon().getName();
			String answerMsg = "The answers are: " + roomName + ", " + personName + ", " + weaponName + ". ";

			//if the accusation matches the solution, it wins
			if (roomBox.getSelectedItem().equals(roomName)) {
				if (personBox.getSelectedItem().equals(personName)) {
					if (weaponBox.getSelectedItem().equals(weaponName)) {
						JOptionPane.showMessageDialog(null, answerMsg + "Congradulations! You won!");
					}
				}
			} else {
				//otherwise display loss message
				JOptionPane.showMessageDialog(null, answerMsg + "Oh no! Your accusation was wrong! You lose!");
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