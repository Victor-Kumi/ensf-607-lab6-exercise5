import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
//import java.awt.Dimension;
//import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Front end of Game App. The class makes connection with server. It builds the GUI and communicates with server
 * @author Victor Tuah Kumi
 *
 */
public class GameClientGUI extends JFrame implements ActionListener{
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private BufferedReader stdIn;
	private String response;
	private JLabel namePrompt, messagePrompt, playerSymbolPrompt;
	private JTextField nameField;
	private JTextArea messageDisplay, symbolDisplay;
	private JButton button00, button01, button02, button10, button11, button12, button20, button21, button22;
	private JPanel boxPanel, playerPanel, westPanel, eastPanel;
	private boolean turn = false;
	public GameClientGUI(String serverName, int portNumber, String title) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		makeComponents();
		arrangeBoxComponents();
		arrangePlayerComponents();
		arrangeWestComponents();
		arrangeMessageComponents();
		arrangeAllComponents();
		makeFrameComponent(title);
		try {
			aSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader (System.in));
			socketIn = new BufferedReader(new InputStreamReader (aSocket.getInputStream()));
			socketOut = new PrintWriter (aSocket.getOutputStream(), true);
		}catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void communicate () {
		provideServerWithName();
		while (!response.equals("GAME OVER")) {
			try {
				response = socketIn.readLine();  
				if (!response.equals("GAME OVER")) {
					if (response.equals("0 0")) {
						if (symbolDisplay.getText().equals("X")) {
						    button00.setText("O");
						}    
						else
							button00.setText("X");
						button00.removeActionListener(this);
						turn = true;
					}	
					else if(response.equals("0 1")) {
						if (symbolDisplay.getText().equals("X"))
						    button01.setText("O");
						else
							button01.setText("X");
						button01.removeActionListener(this);
						turn = true;
					}	
					else if(response.equals("0 2")) {
						if (symbolDisplay.getText().equals("X"))
						    button02.setText("O");
						else
							button02.setText("X");
						button02.removeActionListener(this);
						turn = true;
					}	
					else if(response.equals("1 0")) {
						if (symbolDisplay.getText().equals("X"))
						    button10.setText("O");
						else
							button10.setText("X");
						button10.removeActionListener(this);
						turn = true;
					}	
					else if(response.equals("1 1")) {
						if (symbolDisplay.getText().equals("X"))
						    button11.setText("O");
						else
							button11.setText("X");
						button11.removeActionListener(this);
						turn = true;
					}	
					else if(response.equals("1 2")) {
						if (symbolDisplay.getText().equals("X"))
						    button12.setText("O");
						else
							button12.setText("X");
						button12.removeActionListener(this);
						turn = true;
					}	
					else if(response.equals("2 0")) {
						if (symbolDisplay.getText().equals("X"))
						    button20.setText("O");
						else
							button20.setText("X");
						button20.removeActionListener(this);
						turn = true;
					}	
					else if(response.equals("2 1")) {
						if (symbolDisplay.getText().equals("X"))
						    button21.setText("O");
						else
							button21.setText("X");
						button21.removeActionListener(this);
						turn = true;
					}	
					else if(response.equals("2 2")){
						if (symbolDisplay.getText().equals("X"))
						    button22.setText("O");
						else
							button22.setText("X");
						button22.removeActionListener(this);
						turn = true;
					}
					else if (response.contains("Connected opponent")) {
						messageDisplay.setText(response);
						turn = true;
					}
					else {
						messageDisplay.setText(response);
						//messageDisplay.append(response);
					}
					    
				}

			} catch (IOException e) {
				e.printStackTrace();
			} 	
		}
		closeSocket ();	
	}
	
	private void closeSocket () {
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void makeFrameComponent(String title) {
		setTitle(title);
		pack();
		setVisible(true);
	}
	
	private void provideServerWithName() {
		response = "";
		try {
			response = socketIn.readLine();
			if (response != null) {
			    messageDisplay.setText(response);
			    if (response.contains("opponent")) {
					symbolDisplay.setText("O");
					turn = false;
				}
			    else {
			    	symbolDisplay.setText("X");
			    }
			}    
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void makeComponents() {
		namePrompt = new JLabel("Name: ");
		messagePrompt = new JLabel("");
		playerSymbolPrompt = new JLabel("Symbol: ");
		nameField = new JTextField(10);
		nameField.addActionListener(this);
		messageDisplay = new JTextArea(5,30);
		messageDisplay.setLineWrap(true);
		symbolDisplay = new JTextArea(1,1);
	    button00 = new JButton();
	    button00.addActionListener(this);
	    button01 = new JButton();
	    button01.addActionListener(this);
	    button02 = new JButton();
	    button02.addActionListener(this);
	    button10 = new JButton();
	    button10.addActionListener(this);
	    button11 = new JButton();
	    button11.addActionListener(this);
	    button12 = new JButton();
	    button12.addActionListener(this);
	    button20 = new JButton();
	    button20.addActionListener(this);
	    button21 = new JButton();
	    button21.addActionListener(this);
	    button22 = new JButton();
	    button22.addActionListener(this);
	}
	
	private void arrangeBoxComponents() {
		boxPanel = new JPanel();
		boxPanel.setLayout(new GridLayout(3, 3));
		//boxPanel.setMaximumSize(new Dimension(160, 160));
		//boxPanel.setMinimumSize(new Dimension(160, 160));
		button00.setPreferredSize(new Dimension(50, 50));
		boxPanel.add(button00);
		button01.setPreferredSize(new Dimension(50, 50));
		boxPanel.add(button01);
		button02.setPreferredSize(new Dimension(50, 50));
		boxPanel.add(button02);
		button10.setPreferredSize(new Dimension(50, 50));
		boxPanel.add(button10);
		button11.setPreferredSize(new Dimension(50, 50));
		boxPanel.add(button11);
		button12.setPreferredSize(new Dimension(50, 50));
		boxPanel.add(button12);
		button20.setPreferredSize(new Dimension(50, 50));
		boxPanel.add(button20);
		button21.setPreferredSize(new Dimension(50, 50));
		boxPanel.add(button21);
		button22.setPreferredSize(new Dimension(50, 50));
		boxPanel.add(button22);
	}
	
	private void arrangePlayerComponents(){
		playerPanel = new JPanel();
		playerPanel.setMaximumSize(new Dimension(150, 150));
		playerPanel.setLayout(new GridLayout(2, 2));
		playerPanel.add(playerSymbolPrompt);
		playerPanel.add(symbolDisplay);
		playerPanel.add(namePrompt);
		playerPanel.add(nameField);
	}
	
	private void arrangeWestComponents(){
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.add(Box.createVerticalStrut(10));
		westPanel.add(boxPanel);
		westPanel.add(Box.createVerticalStrut(10));
		westPanel.add(playerPanel);
	}
	
	private void arrangeMessageComponents(){
		eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(2, 1));
		eastPanel.add(messagePrompt);
		westPanel.add(Box.createVerticalStrut(0));
		eastPanel.add(messageDisplay);
	}
	
	private void arrangeAllComponents(){
		Container contentPane = getContentPane();
		contentPane.add("Center", westPanel);
		contentPane.add("South", eastPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nameField) {
			socketOut.println(nameField.getText());
			nameField.removeActionListener(this);
		}
		if ((e.getSource() == button00 || e.getSource() == button01 || e.getSource() == button02 || 
				e.getSource() == button10 || e.getSource() == button11 || e.getSource() == button12 ||
				e.getSource() == button20 || e.getSource() == button21 || e.getSource() == button22) && turn) {
		if (e.getSource() == button00) {
			button00.setText(symbolDisplay.getText());
			button00.removeActionListener(this);
			socketOut.println("0 0");
			if (!messageDisplay.getText().contains("GAME OVER"))
				messageDisplay.setText("Opponent's turn");
		}
		if (e.getSource() == button01) {
			button01.setText(symbolDisplay.getText());
			button01.removeActionListener(this);
			socketOut.println("0 1");
			if (!messageDisplay.getText().contains("GAME OVER"))
				messageDisplay.setText("Opponent's turn");
		}
		if (e.getSource() == button02) {
			button02.setText(symbolDisplay.getText());
			button02.removeActionListener(this);
			socketOut.println("0 2");
			if (!messageDisplay.getText().contains("GAME OVER"))
				messageDisplay.setText("Opponent's turn");
		}
		if (e.getSource() == button10) {
			button10.setText(symbolDisplay.getText());
			button10.removeActionListener(this);
			socketOut.println("1 0");
			if (!messageDisplay.getText().contains("GAME OVER"))
				messageDisplay.setText("Opponent's turn");
		}
		if (e.getSource() == button11) {
			button11.setText(symbolDisplay.getText());
			button11.removeActionListener(this);
			socketOut.println("1 1");
			if (!messageDisplay.getText().contains("GAME OVER"))
				messageDisplay.setText("Opponent's turn");
		}
		if (e.getSource() == button12) {
			button12.setText(symbolDisplay.getText());
			button12.removeActionListener(this);
			socketOut.println("1 2");
			if (!messageDisplay.getText().contains("GAME OVER"))
				messageDisplay.setText("Opponent's turn");
		}
		if (e.getSource() == button20) {
			button20.setText(symbolDisplay.getText());
			button20.removeActionListener(this);
			socketOut.println("2 0");
			if (!messageDisplay.getText().contains("GAME OVER"))
				messageDisplay.setText("Opponent's turn");
		}
		if (e.getSource() == button21) {
			button21.setText(symbolDisplay.getText());
			button21.removeActionListener(this);
			socketOut.println("2 1");
			if (!messageDisplay.getText().contains("GAME OVER"))
				messageDisplay.setText("Opponent's turn");
		}
		if (e.getSource() == button22) {
			button22.setText(symbolDisplay.getText());
			button22.removeActionListener(this);
			socketOut.println("2 2");
			if (!messageDisplay.getText().contains("GAME OVER"))
				messageDisplay.setText("Opponent's turn");
		}
		turn = false;
		}
	}
	
	public static void main(String[] args) {
		GameClientGUI aClient = new GameClientGUI ("localhost", 9898, "Tic-Tac-Toe");
		aClient.communicate();

	}

}
