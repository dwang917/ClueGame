package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class GameControlPanel extends JPanel {
	
	//All JTextFields to create text
	private JTextField Roll;
	private JTextField turnText;
	private JTextField guessText;
	private JTextField resultText;
	
	//Initialize inner panels 
	private JPanel rollPanel;
	private JPanel turnPanel;
	private JPanel guessPanel;
	private JPanel resultPanel;
	
	public GameControlPanel() {
		setName("Control Panel");
		setLayout(new GridLayout(2,0));
		createUperPanel();
		lowerPanel();
	}
	
	
	//create top panel that holds panels for player turn, roll, and buttons
	private void createUperPanel() {
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(1,4));
		createRollPanel();
		createTurnPanel();
		JButton accuseButton = new JButton("Make Accusation");
		JButton nextButton = new JButton("NEXT!");

		
		top.add(turnPanel);
		top.add(rollPanel);
		top.add(accuseButton);
		top.add(nextButton);
		
		add(top);
		
	}
	
	private void lowerPanel() {
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(0,2));
		
		createGuessPanel();
		createResultPanel();
		bottom.add(guessPanel);
		bottom.add(resultPanel);
		
		
		add(bottom);
		
	}
	
	private void createGuessPanel() {
		guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		guessText = new JTextField(10);
		guessPanel.add(guessText);
	}
	
	private void createResultPanel() {
		resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1,0));
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		resultText = new JTextField(10);
		resultPanel.add(resultText);
	}
	
	private void createTurnPanel() {
		turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		
		JLabel turnLabel = new JLabel("Whose turn?");
		turnText = new JTextField(10);
		
		turnPanel.add(turnLabel, BorderLayout.CENTER);
		turnPanel.add(turnText);

		
		
	}
	private void createRollPanel() {
		
		rollPanel = new JPanel();
		
		JLabel rollLabel = new JLabel("Roll");
		Roll = new JTextField(10);
		rollPanel.add(rollLabel);
		rollPanel.add(Roll);			
	}
	
	
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();
		
		JFrame frame = new JFrame();
		frame.setTitle("Clue Game");
		frame.setContentPane(panel);
		frame.setSize(750, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
		
		panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.orange, 0,0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
		
	}

	private void setGuessResult(String string) {
		// TODO Auto-generated method stub
		resultText.setText(string);
	}

	private void setGuess(String string) {
		// TODO Auto-generated method stub
		guessText.setText(string);
	}

	private void setTurn(ComputerPlayer computerPlayer, int i) {
		// TODO Auto-generated method stub
		turnText.setText(computerPlayer.getName());
		turnText.setSelectionColor(computerPlayer.getColer());
		String roll = "";
		roll+=i;
		Roll.setText(roll);
		
	}
}

