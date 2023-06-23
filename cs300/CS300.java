package cs300;

import java.util.Scanner;


public class CS300 {
    /**
     * This method is responsible for everything from displaying the opening
     * welcome message to printing out the final thank you.  It will clearly be
     * helpful to call several of the following methods from here, and from the
     * methods called from here.  See the Sample Runs below for a more complete
     * idea of everything this method is responsible for.
     *
     * @param args - any command line arguments may be ignored by this method.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("WELCOME TO CS300 PEG SOLITAIRE!\n===============================\n");

        String prompt = "Board Style Menu\n1) Cross\n2) Circle\n3) Triangle\n4) Simple T\nChoose a board style: ";

        char[][] board = createBoard(readValidInt(scanner, prompt, 1, 4));

        while (true) {
            //Display board at start
            displayBoard(board);

            //Check for condition to stop
            if (countMovesAvailable(board) == 0) {

                //If no pegs left you win
                if (countPegsRemaining(board) == 1) {
                    System.out.println("Congrats, you won!");
                } else {
                    System.out.println("It looks like there are no more legal moves.  Please try again.");
                }
                break;
            }

            //If moves, take vlaid moves and perform
            int[] validMove = readValidMove(scanner, board);
            performMove(board, validMove[1], validMove[0], validMove[2]);

        }

        System.out.println();
        System.out.print("==========================================\n" +
            "THANK YOU FOR PLAYING CS300 PEG SOLITAIRE!");

    }

    /**
     * This method is used to read in all inputs from the user.  After printing
     * the specified prompt, it will check whether the user�s input is in fact
     * an integer within the specified range.  If the user�s input does not
     * represent an integer or does not fall within the required range, print
     * an error message asking for a value within that range before giving the
     * user another chance to enter valid input.  The user should be given as
     * many chances as they need to enter a valid integer within the specified
     * range.  See the Sample Runs to see how these error messages should be
     * phrased, and to see how the prompts are repeated when multiple invalid
     * inputs are entered by the user.
     *
     * @param in - user input from standard in is ready through this.
     * @param prompt - message describing what the user is expected to enter.
     * @param min - the smallest valid integer that the user may enter.
     * @param max - the largest valid integer that the user may enter.
     * @return - the valid integer between min and max entered by the user.
     */
    public static int readValidInt(Scanner in , String prompt, int min, int max) {
        int input;

        System.out.print(prompt);

        while (true) {
            String line = in .nextLine().trim();

            if (!line.contains(" ")) {
                try {
                    input = Integer.parseInt(line);
                    if (input >= min && input <= max) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    // Invalid input, will continue the loop
                }
            }

            System.out.print("Please enter your choice as an integer between " + min + " and " + max + ": ");
        }

        return input;
    }

    /**
     * This method creates, initializes, and then returns a rectangular two
     * dimensional array of characters according to the specified boardType.
     * Initial configurations for each of the possible board types are depicted
     * below.  Note that pegs are displayed as @s, empty holes are displayed as
     * -s, and extra blank positions that are neither pegs nor holes within
     * each rectangular array are displayed as #s.
     *
     * @param boardType - 1-4 indicating one of the following initial patterns:
     *   1) Cross
     *     ###@@@###
     *     ###@@@###
     *     @@@@@@@@@
     *     @@@@-@@@@
     *     @@@@@@@@@
     *     ###@@@###
     *     ###@@@###
     *
     *   2) Circle
     *     #-@@-#
     *     -@@@@-
     *     @@@@@@
     *     @@@@@@
     *     -@@@@-
     *     #-@@-#
     *
     *   3) Triangle
     *     ###-@-###
     *     ##-@@@-##
     *     #-@@-@@-#
     *     -@@@@@@@-
     *
     *   4) Simple T
     *     -----
     *     -@@@-
     *     --@--
     *     --@--
     *     -----
     *
     * @return - the fully initialized two dimensional array.
     */
    public static char[][] createBoard(int boardType) {

        char[][] board;

        switch (boardType) {
            case 1:
                board = new char[][] {
                    "###@@@###".toCharArray(),
                        "###@@@###".toCharArray(),
                        "@@@@@@@@@".toCharArray(),
                        "@@@@-@@@@".toCharArray(),
                        "@@@@@@@@@".toCharArray(),
                        "###@@@###".toCharArray(),
                        "###@@@###".toCharArray()
                };
                break;

            case 2:
                board = new char[][] {
                    "#-@@-#".toCharArray(),
                        "-@@@@-".toCharArray(),
                        "@@@@@@".toCharArray(),
                        "@@@@@@".toCharArray(),
                        "-@@@@-".toCharArray(),
                        "#-@@-#".toCharArray()
                };
                break;

            case 3:
                board = new char[][] {
                    "###-@-###".toCharArray(),
                        "##-@@@-##".toCharArray(),
                        "#-@@-@@-#".toCharArray(),
                        "-@@@@@@@-".toCharArray()
                };
                break;

            case 4:
                board = new char[][] {
                    "-----".toCharArray(),
                        "-@@@-".toCharArray(),
                        "--@--".toCharArray(),
                        "--@--".toCharArray(),
                        "-----".toCharArray()
                };
                break;

            default:
                System.out.print("Invalid boardType");
                return null;

        }
        return board;
    }

    /**
     * This method prints out the contents of the specified board using @s to
     * represent pegs, -s to represent empty hole, and #s to represent empty
     * positions that are neither pegs nor holes.  In addition to this, the
     * columns and rows of this board should be labelled with numbers starting
     * at one and increasing from left to right (for column labels) and from
     * top to bottom (for row labels).  See the Sample Runs for examples of how
     * these labels appear next to boards with different dimensions.
     *
     * @param board - the current state of the board being drawn.
     */
    public static void displayBoard(char[][] board) {
        System.out.println();

        // Just iterate over length of column to print numbers to label it
        // Add one because zero indexed
        
        System.out.print("  ");
        for (int col = 0; col < board[0].length; col++) {
            System.out.print(col + 1);
        }
        System.out.println();

        // Iterate over and print row number then each value 
        for (int row = 0; row < board.length; row++) {
            System.out.print(row + 1 + " ");
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }


    /**
     * This method is used to read in and validate each part of a user�s move
     * choice: the row and column that they wish to move a peg from, and the
     * direction that they would like to move/jump that peg in.  When the
     * player�s row, column, and direction selection does not represent a valid
     * move, your program should report that their choice does not constitute a
     * legal move before giving them another chance to enter a different move.
     * They should be given as many chances as necessary to enter a legal move.
     * The array of three integers that this method returns will contain: the
     * user�s choice of column as the first integer, their choice of row as the
     * second integer, and their choice of direction as the third.
     *
     * @param in - user input from standard in is ready through this.
     * @param board - the state of the board that moves must be legal on.
     * @return - the user's choice of column, row, and direction representing
     *   a valid move and store in that order with an array.
     */
    public static int[] readValidMove(Scanner in , char[][] board) {
        int row, column, direction;
        
        // Call our readValidInt, and then isValidMove to keep prmpting until 
        // we get a valid move. 

        while (true) {
            String prompt = "Choose the COLUMN of a peg you'd like to move: ";
            column = readValidInt( in , prompt, 1, board[0].length);

            prompt = "Choose the ROW of a peg you'd like to move: ";
            row = readValidInt( in , prompt, 1, board.length);

            prompt = "Choose a DIRECTION to move that peg 1) UP, 2) DOWN, 3) LEFT, or 4) RIGHT: ";
            direction = readValidInt( in , prompt, 1, 4);


            if (isValidMove(board, row, column, direction)) {
                int[] move = {column, row, direction};
                
                return move;
            }

            // If not valid print saying move is not valid.
            String curr_direc;

            switch (direction) {
                case 1:
                    curr_direc = "UP";
                    break;
                case 2:
                    curr_direc = "DOWN";
                    break;
                case 3:
                    curr_direc = "LEFT";
                    break;
                case 4:
                    curr_direc = "RIGHT";
                    break;
                default:
                    throw new IllegalStateException("Unexpected direction value: " + direction);

            }
            System.out.println("Moving a peg from row " + row + " and column " + column + " " + curr_direc + " is not currently a legal move.\n");
        }

    }

    /**
     * This method checks whether a specific move (column + row + direction) is
     * valid within a specific board configuration.  In order for a move to be
     * a valid: 1)there must be a peg at position row, column within the board,
     * 2)there must be another peg neighboring that first one in the specified
     * direction, and 3)there must be an empty hole on the other side of that
     * neighboring peg (further in the specified direction).  This method
     * only returns true when all three of these conditions are met.  If any of
     * the three positions being checked happen to fall beyond the bounds of
     * your board array, then this method should return false.  Note that the
     * row and column parameters here begin with one, and may need to be
     * adjusted if your programming language utilizes arrays with zero-based
     * indexing.
     *
     * @param board - the state of the board that moves must be legal on.
     * @param row - the vertical position of the peg proposed to be moved.
     * @param column - the horizontal position of the peg proposed to be moved.
     * @param direction - the direction proposed to move/jump that peg in.
     * @return - true when the proposed move is legal, otherwise false.
     */
    public static boolean isValidMove(char[][] board, int row, int column, int direction) {
        // Convert to 0-based index as hinted
        row--;
        column--;

        // Bounds of board
        int numRows = board.length;
        int numCols = board[0].length;

        // Declare variables to track direction
        // Will make easier to keep track of and move one way
        int dRow = 0, dCol = 0;

        // Update row and column based on direction, will add to origianl row
        switch (direction) {
            case 1: // Up
                dRow = -1;
                break;
            case 2: // Down
                dRow = 1;
                break;
            case 3: // Left
                dCol = -1;
                break;
            case 4: // Right
                dCol = 1;
                break;
            default:
                return false; // Invalid direction
        }

        // Check if initial position is within bounds and contains a peg
        if (row < 0 || row >= numRows || column < 0 || column >= numCols || board[row][column] != '@') {
            return false;
        }

        // Check if neighbor position is within bounds and contains a peg by adding dRow and dCol
        int neighborRow = row + dRow;
        int neighborCol = column + dCol;
        if (neighborRow < 0 || neighborRow >= numRows || neighborCol < 0 || neighborCol >= numCols || board[neighborRow][neighborCol] != '@') {
            return false;
        }

        // Check if there's an empty hole on the other side of that neighboring peg
        // We do this by continuing one more to the direction specified
        int holeRow = neighborRow + dRow;
        int holeCol = neighborCol + dCol;
        if (holeRow < 0 || holeRow >= numRows || holeCol < 0 || holeCol >= numCols || board[holeRow][holeCol] != '-') {
            return false;
        }

        return true; // All conditions met
    }


    /**
     * The parameters of this method are the same as those of the isValidMove()
     * method.  However this method changes the board state according to this
     * move parameter (column + row + direction), instead of validating whether
     * the move is valid.  If the move specification that is passed into this
     * method does not represent a legal move, then do not modify the board.
     *
     * @param board - the state of the board will be changed by this move.
     * @param row - the vertical position that a peg will be moved from.
     * @param column - the horizontal position that a peg will be moved from.
     * @param direction - the direction of the neighbor to jump this peg over.
     * @return - the updated board state after the specified move is taken.
     */
    public static char[][] performMove(char[][] board, int row, int column, int direction) {
        if (isValidMove(board, row, column, direction)) {
            // Convert to 0-based index again
            row--;
            column--;

            // Utilize same strategy to track movement
            int dRow = 0, dCol = 0;

            switch (direction) {
                case 1: // Up
                    dRow = -1;
                    break;
                case 2: // Down
                    dRow = 1;
                    break;
                case 3: // Left
                    dCol = -1;
                    break;
                case 4: // Right
                    dCol = 1;
                    break;
            }

            // Change the initial position to empty
            board[row][column] = '-';

            // Change the neighbor position to empty
            board[row + dRow][column + dCol] = '-';

            // Move the peg to the hole position
            board[row + 2 * dRow][column + 2 * dCol] = '@';
        }
        return board;
    }

    /**
     * This method counts up the number of pegs left within a particular board
     * configuration, and returns that number.
     *
     * @param board - the board that pegs are counted from.
     * @return - the number of pegs found in that board.
     */
    public static int countPegsRemaining(char[][] board) {
    	// Iterate over 2d array and count @s
    	
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '@') {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * This method counts up the number of legal moves that are available to be
     * performed in a given board configuration.
     *
     * HINT: Would it be possible to call the isValidMove() method for every
     * direction and from every position within your board?  Counting up the
     * number of these calls that return true should yield the total number of
     * moves available within a specific board.
     *
     * @param board - the board that possible moves are counted from.
     * @return - the number of legal moves found in that board.
     */
    public static int countMovesAvailable(char[][] board) {
        int total = 0;

        // Call is validMove on every possible move left on board
        for (int row = 1; row <= board.length; row++) {
            for (int col = 1; col <= board[0].length; col++) {
                for (int dir = 1; dir < 5; dir++) {
                    if (isValidMove(board, row, col, dir)) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

}