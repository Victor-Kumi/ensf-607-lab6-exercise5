import java.io.*;
/**
 * Player class is responsible for making the play and checking for possible win or tie.
 * It also is responsible for relaying every move made to the front end; the other opponent;
 * @author Victor Tuah Kumi
 *
 */
public class Player {
    private String name;
    private Board board;
    private Player opponent;
    private char mark;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    public Player(String name, char mark){
        this.name = name;
        this.mark = mark;
    }
    
    /**
     * While there is no winner, make move, check for possible winner and exit game, if not make opponent play.
     */
    public void play() {
        while (!isWinner()){
            this.makeMove();
            if(!isWinner())
            	opponent.play();
        }
    }
    
    /**
     * Make move and update opponent on move made
     */
    public void makeMove(){
    	if ((board.getMarkCount() > 7) && !isUsefulMove()) {
    		isWinner();
    		return;
    	}
        int row;
        int col;
        socketOut.println(this.name + ", click a box to play");
        try {
        	String rowColStr = socketIn.readLine();
			String[] rowCol = rowColStr.split(" ");
			row = Integer.parseInt(rowCol[0]);
			col = Integer.parseInt(rowCol[1]);
	        if (board.getMark(row, col) == board.SPACE_CHAR) {
	        	opponent.socketOut.println(rowColStr);
	            board.addMark(row, col, mark);
	        } 
	        else
	            makeMove();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}   
    }
    
    public void setOpponent(Player oPlayer) {
        this.opponent = oPlayer;
    }

    public void setBoard(Board theBoard) {
        this.board = theBoard;
    }
    
    /**
     * check for win, tie or none
     * @return false if no winner, otherwise game ends
     */
    public boolean isWinner(){
        boolean winner = false;
        boolean tie = board.isFull();
        boolean xWins = board.xWins();
        boolean oWins = board.oWins();
        if (tie || xWins || oWins){
            winner = true;
            if (tie) {
            	socketOut.println("GAME OVER!!!.. A TIE GAME");
            	opponent.socketOut.println("GAME OVER!!!.. A TIE GAME");
            }   
            else {
            	socketOut.println("GAME OVER!!! " + this.name + " wins");
            	opponent.socketOut.println("GAME OVER!!! " + this.name + " wins");
            }
            socketOut.println("GAME OVER");
            opponent.socketOut.println("GAME OVER");
        }
        return winner;
    }
    
    public void setSocket(BufferedReader socketIn, PrintWriter socketOut) {
    	this.socketIn = socketIn;
    	this.socketOut = socketOut;
    }
    public PrintWriter getSocketOut() {
    	return this.socketOut;
    }
    
    public String getName() {
    	return this.name;
    }
    //Method unused
    public void printBoardToBothSocketOuts() {
    	board.setSocket(socketOut);
    	board.display();

    	board.setSocket(opponent.getSocketOut());
    	board.display();
    }
    
    public boolean isUsefulMove() {
    	//String emptyBox = "";
    	int emptyBoxRow = -1;
    	int emptyBoxCol = -1;

    	outer:for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				if (board.getMark(row, col) == board.SPACE_CHAR) {
					//emptyBox = Integer.toString(row) + Integer.toString(col);
					emptyBoxRow = row;
					emptyBoxCol = col;
					break outer;
				}
			}
    	board.addMark(emptyBoxRow, emptyBoxRow, opponent.getMark());
    	if (board.xWins()) {
    		board.removeMark(emptyBoxRow, emptyBoxCol);
    		System.out.println(opponent.getName() + " wins");
    		return true;
    	}
    	return false;
    }
    
    public char getMark() {
    	return mark;
    }
}
