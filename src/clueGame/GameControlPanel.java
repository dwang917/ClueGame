package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		setTurn(human, rollValue);
		human.start(board, rollValue);
	}

	// continue to the next turn
	private void nextTurn() {
		rollValue = dieRoll();
		// update the panel
		setTurn(board.getCurrentPlayer(), rollValue);
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
		JButton accuseButton = new JButton("Make Accusation");
		accuseButton.setBackground(Color.blue);
		accuseButton.setOpaque(true);
		JButton nextButton = new JButton("NEXT!");
		nextButton.addActionListener(new NextListener());
		nextButton.setBackground(Color.blue);
		nextButton.setOpaque(true);

		// add panels to the top panel
		top.add(turnPanel);
		top.add(rollPanel);
		top.add(accuseButton);
		top.add(nextButton);

		// add the top panel to the housing panel
		add(top);
	}

	private class NextListener implements ActionListener {

		// if the current turn is finished, start the next turn
		@Override
		public void actionPerformed(ActionEvent e) {
			if (board.isTurnFinished()) {
				board.updatePlayer();
				nextTurn();
			}
			// when the user tries to click next without finishing the turn,
			// display the splash screen
			else {
				JOptionPane.showMessageDialog(null, "Please finish your turn first.");
			}
		}
	}

	
	private class AccuseListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
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

	private void setGuessResult(String string) {
		resultText.setText(string);
	}

	private void setGuess(String string) {
		guessText.setText(string);
	}

	// update the roll and player on the panel
	private void setTurn(Player Player, int dieRoll) {
		turnText.setText(Player.getName());
		turnText.setSelectionColor(Player.getColer());
		String roll = "";
		roll += dieRoll;
		Roll.setText(roll);
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
}
