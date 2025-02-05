package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String player1, player2;
        String command;

        while(true) {

            System.out.print("Input command: > ");
            command = sc.nextLine();
            String[] words = command.split("\\s+");
            int len = words.length;
            if(len == 1 && words[0].equalsIgnoreCase("exit"))
                break;
            if(len != 3) {
                System.out.println("Bad parameters!");
                continue;
            }
            else {
                if(words[0].equalsIgnoreCase("start")) {
                    if((words[1].equalsIgnoreCase("user") || words[1].equalsIgnoreCase("easy") || words[1].equalsIgnoreCase("medium") || words[1].equalsIgnoreCase("hard")) &&
                            (words[2].equalsIgnoreCase("user") || words[2].equalsIgnoreCase("easy") || words[2].equalsIgnoreCase("medium") || words[2].equalsIgnoreCase("hard"))) {
                        player1 = words[1];
                        player2 = words[2];
                    }
                    else {
                        System.out.println("Bad parameters!");
                        continue;
                    }
                }
                else {
                    System.out.println("Bad parameters!");
                    continue;
                }
            }

            String initialState = "_________";
            char[][] board = new char[3][3];
            int k = 0;
            for(int i=0;i<3;i++) {
                for(int j=0;j<3;j++) {
                    board[i][j] = initialState.charAt(k++);
                }
            }

            display(board);

            game(player1,player2,board,sc);

        }
    }

    private static void game(String player1, String player2, char[][] board, Scanner sc) {
        int[] player1Value = new int[8];
        int[] player2Value = new int[8];
        int[][][] winningCombinations = {
                {{0,0}, {0,1}, {0,2}},
                {{1,0}, {1,1}, {1,2}},
                {{2,0}, {2,1}, {2,2}},
                {{0,0}, {1,0}, {2,0}},
                {{0,1}, {1,1}, {2,1}},
                {{0,2}, {1,2}, {2,2}},
                {{0,0}, {1,1}, {2,2}},
                {{0,2}, {1,1}, {2,0}}
        };
        while(true) {

            if(player1.equalsIgnoreCase("user")) {
                int[] coordinate = humanTurn(board,'X');
                updateWinningCombinations(winningCombinations,coordinate,player1Value);
            }
            else if(player1.equalsIgnoreCase("easy")) {
                int[] coordinate = computerTurnEasy(board,'X');
                updateWinningCombinations(winningCombinations,coordinate,player1Value);
            }
            else if(player1.equalsIgnoreCase("medium")) {
                int[] coordinate = computerTurnMedium(winningCombinations,player1Value,player2Value,board,'X');
                updateWinningCombinations(winningCombinations,coordinate,player1Value);
            }
            else {
                int[] coordinate = computerTurnHard(board,'X');
                updateWinningCombinations(winningCombinations,coordinate,player2Value);
            }

            display(board);
            if(checkIfResult(board)) break;
            System.out.println("Game not finished");

            if(player2.equalsIgnoreCase("user")) {
                int[] coordinate = humanTurn(board,'O');
                updateWinningCombinations(winningCombinations,coordinate,player2Value);
            }
            else if(player2.equalsIgnoreCase("easy")) {
                int[] coordinate = computerTurnEasy(board,'O');
                updateWinningCombinations(winningCombinations,coordinate,player2Value);
            }
            else if(player2.equalsIgnoreCase("medium")) {
                int[] coordinate = computerTurnMedium(winningCombinations,player2Value,player1Value,board,'O');
                updateWinningCombinations(winningCombinations,coordinate,player2Value);
            }
            else {
                int[] coordinate = computerTurnHard(board,'O');
                updateWinningCombinations(winningCombinations,coordinate,player2Value);
            }


            display(board);
            if(checkIfResult(board)) break;
            System.out.println("Game not finished");
        }
    }

    private static int[] computerTurnMedium(int[][][] winningCombinations, int[] computer, int[] user, char[][] board, char symbol) {
        System.out.println("Making move level \"medium\"");
        for(int i=0;i<8;i++) {
            if(computer[i] == 2) {
                for(int j=0;j<3;j++) {
                    int x = winningCombinations[i][j][0];
                    int y = winningCombinations[i][j][1];
                    if(board[x][y] == '_') {
                        board[x][y] = symbol;
                        return new int[]{x, y};
                    }
                }
            }
        }
        for(int i=0;i<8;i++) {
            if(user[i] == 2) {
                for(int j=0;j<3;j++) {
                    int x = winningCombinations[i][j][0];
                    int y = winningCombinations[i][j][1];
                    if(board[x][y] == '_') {
                        board[x][y] = symbol;
                        return new int[]{x, y};
                    }
                }
            }
        }
        int x, y;
        Random R = new Random();
        while(true) {
            x = R.nextInt(3);
            y = R.nextInt(3);
            if(board[x][y] == '_') {
                board[x][y] = symbol;
                return new int[]{x, y};
            }
        }
    }

    private static int[] humanTurn(char[][] board, char symbol) {
        Scanner sc = new Scanner(System.in);
        int x, y;
        while(true) {
            System.out.print("Enter the coordinates: > ");
            try {
                x = sc.nextInt();
                y = sc.nextInt();
                if(x < 1 || x > 3 || y < 1 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }

                x--;
                y--;

                if(board[x][y] != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                sc.nextLine();
                continue;
            }
            board[x][y] = symbol;
            return new int[]{x, y};
        }
    }

    private static int[] computerTurnEasy(char[][] board, char symbol) {
        System.out.println("Making move level \"easy\"");
        int x, y;
        Random R = new Random();
        while(true) {
            x = R.nextInt(3);
            y = R.nextInt(3);
            if(board[x][y] == '_') {
                board[x][y] = symbol;
                return new int[]{x, y};
            }
        }
    }

    private static int[] computerTurnHard(char[][] board, char symbol) {
        System.out.println("Making move level \"Hard\"");
        int[] place = new int[2];
        int bestVal = symbol == 'X' ? -1000 : 1000;
        boolean flag = symbol != 'X';
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == '_')
                {
                    board[i][j] = symbol;
                    int moveVal = minimax(board, 10, flag);
                    board[i][j] = '_';
                    if(symbol == 'X') {
                        if (moveVal > bestVal)
                        {
                            place[0] = i;
                            place[1] = j;
                            bestVal = moveVal;
                        }
                    }
                    else {
                        if (moveVal < bestVal)
                        {
                            place[0] = i;
                            place[1] = j;
                            bestVal = moveVal;
                        }
                    }
                }
            }
        }
        board[place[0]][place[1]] = symbol;
        return place;
    }

    private static int minimax(char[][] board, int depth, boolean maximizing) {
        if(isWinner(board,'X'))
            return 10;
        if(isWinner(board,'O'))
            return -10;
        if(isFull(board) || depth == 0)
            return 0;

        int best;
        if(maximizing) {
            best = Integer.MIN_VALUE;
            for(int i=0;i<3;i++) {
                for(int j=0;j<3;j++) {
                    if(board[i][j] == '_') {
                        board[i][j] = 'X';
                        best = Math.max(best, minimax(board,depth-1, false));
                        board[i][j] = '_';
                    }
                }
            }
        }
        else {
            best = Integer.MAX_VALUE;
            for(int i=0;i<3;i++) {
                for(int j=0;j<3;j++) {
                    if(board[i][j] == '_') {
                        board[i][j] = 'O';
                        best = Math.min(best, minimax(board,depth-1, true));
                        board[i][j] = '_';
                    }
                }
            }
        }
        return best;
    }

    private static boolean checkIfResult(char[][] board) {
        if(isWinner(board,'X')) {
            System.out.println("X wins");
            return true;
        }
        if(isWinner(board,'O')) {
            System.out.println("O wins");
            return true;
        }
        if(isFull(board)) {
            System.out.println("Draw");
            return true;
        }
        return false;
    }

    private static boolean isWinner(char[][] board, char symbol) {
        if(board[0][0] == symbol && board[0][1] == symbol && board[0][2] == symbol) return true;
        else if(board[1][0] == symbol && board[1][1] == symbol && board[1][2] == symbol) return true;
        else if(board[2][0] == symbol && board[2][1] == symbol && board[2][2] == symbol) return true;
        else if(board[0][0] == symbol && board[1][0] == symbol && board[2][0] == symbol) return true;
        else if(board[0][1] == symbol && board[1][1] == symbol && board[2][1] == symbol) return true;
        else if(board[0][2] == symbol && board[1][2] == symbol && board[2][2] == symbol) return true;
        else if(board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) return true;
        else if(board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) return true;
        else return false;
    }

    public static void updateWinningCombinations(int[][][] winningCombinations, int[] coordinate, int[] value) {
        for (int i = 0; i < 8; i++) {
            if ((winningCombinations[i][0][0] == coordinate[0] && winningCombinations[i][0][1] == coordinate[1]) ||
                    (winningCombinations[i][1][0] == coordinate[0] && winningCombinations[i][1][1] == coordinate[1]) ||
                    (winningCombinations[i][2][0] == coordinate[0] && winningCombinations[i][2][1] == coordinate[1])) {
                value[i]++;
            }
        }
    }

    private static boolean isFull(char[][] board) {
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(board[i][j] == '_') return false;
            }
        }
        return true;
    }

    private static void display(char[][] board) {
        System.out.println("_________");
        for(int i=0;i<3;i++) {
            System.out.print("| ");
            for(int j=0;j<3;j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println("|");
        }
        System.out.println("_________");
    }
}
