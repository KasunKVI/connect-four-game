package lk.ijse.dep.service;

public class AiPlayer extends Player {

    public AiPlayer(Board board) {
        super(board);
    }

    public void movePiece(int column) {

        column = evaluate();

        //Update 2d array as Ai played
        board.updateMove(column, Piece.GREEN);

        //Update the Ui Board
        board.getBoardUI().update(column, false);

        //Find if Ai win
        if(board.findWinner().getWinningPiece()==Piece.GREEN){

            board.getBoardUI().notifyWinner(board.findWinner());
        }

        //Check if there any legal moves in board
        if(!board.existLegalMove()){

            //Notify as there no winner
            board.getBoardUI().notifyWinner(board.findWinner());

        }

    }


    private int evaluate() {

        //Assign minimum value of integer to alpha variable
        int alpha = Integer.MIN_VALUE;

        //Assign maximum value of integer to beta variable
        int beta = Integer.MAX_VALUE;

        int tiedColumn = 0;
        boolean UserWinningStats = false;

        for (int i = 0; i < 6; i++) {

            //Check if there is board have exist legal moves
            if (board.isLegalMove(i) && board.existLegalMove()) {

                //Assign next empty row index  of specified column
                int row = board.findNextAvailableSpot(i);

                //Update 2d array as Ai played
                board.updateMove(i, Piece.GREEN);

                //Call minimax method with 0 depth and assign returned value to 'eval'
                int eval = minimax(0, alpha, beta, false);

                 //Undo updated move in 2d Array
                board.updateMove(i, row, Piece.EMPTY);

                //If minimax method returned value=1 then return column index
                if (eval == 1) {
                    return i;
                }

                //If minimax method returned value=-1 then marked as winning true
                if (eval == -1) {
                    UserWinningStats = true;
                }else {
                    tiedColumn = i;//Assign specified column index to tiedColumn variable
                }

            }
        }

        //If there is winningStatus true and if tiedColumn is true
        if ((UserWinningStats) && (board.isLegalMove(tiedColumn))) {

            return tiedColumn;//returning the tied column to the movePiece method.

        }

        int column = 0;

        //Else then returned random Value between 0-6
        do {

            column = (int) (Math.random() * 6);

        }while (!board.isLegalMove(column));


        return column;

    }

    //Method for implement minimax algorithm with four parameters
    private int minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {

        //Find the winner and assign it to winner variable
        Winner winner = board.findWinner();

        //If winner is human player then return 1
        if (winner.getWinningPiece() == Piece.GREEN) {
            return 1;
        }

        //If winner is human player then return -1
        if (winner.getWinningPiece() == Piece.BLUE) {
            return -1;
        }

        //If there is no any legal moves in the board or if depth =4 then return 0
        if (!board.existLegalMove() || depth == 4) {
            return 0;
        }

        //Check if maximizing player true
        if (maximizingPlayer) {

            //Assign minimum value of integer to maxEval variable
            int maxEval=Integer.MIN_VALUE;


            for (int i = 0; i < 6; i++) {
                if (board.isLegalMove(i)) {

                    //Find the available(empty) row of the specified column
                    int row = board.findNextAvailableSpot(i);

                    //Update 2d Array as GREEN
                    board.updateMove(i, Piece.GREEN);

                    //Recursive call minimax max method
                    int eval = minimax(depth + 1, alpha, beta, false);

                    //Undo updated move in 2d Array
                    board.updateMove(i, row, Piece.EMPTY);

                    //Assign maximum value from given variable /*ath.max() is a built-in function that returns the maximum value among the given arguments
                    alpha = Math.max(alpha, eval);
                    maxEval=Math.max(alpha,eval);

                    //If alpha>=beta then stop the loop
                    if (beta <= alpha) {
                        break;
                    }

                }
            }

            return maxEval;

        //if maximizing player false then run this
        } else {

            //Assign maximum value of integer to minEval variable
            int minEval=Integer.MAX_VALUE;

            for (int i = 0; i < 6; i++) {
                if (board.isLegalMove(i)) {

                    //Find the available(empty) row of the specified column
                    int row = board.findNextAvailableSpot(i);

                    //Update 2d Array as BLUE
                    board.updateMove(i, Piece.BLUE);

                    //Recursive call minimax max method
                    int eval = minimax(depth + 1, alpha, beta, true);

                    //Undo updated move in 2d Array
                    board.updateMove(i, row, Piece.EMPTY);

                    //Assign maximum value from given variable /*ath.max() is a built-in function that returns the maximum value among the given arguments
                    minEval=Math.min(minEval,eval);
                    beta = Math.min(beta, eval);

                    //If alpha>=beta then stop the loop
                    if (beta <= alpha) {
                        break;
                    }

                }
            }

            return minEval;

        }
    }

}




/*package lk.ijse.dep.service;

import lk.ijse.dep.controller.BoardController;

public class AiPlayer extends Player{

    int depth=0;

    public AiPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int indexOf) {


        int col = minimax(depth, true);

        if (board.isLegalMove(col)) {

            board.updateMove(col, board.findNextAvailableSpot(col), Piece.GREEN);
            board.getBoardUI().update(col, false);

            if (board.findWinner().getWinningPiece() == Piece.GREEN) {
                board.getBoardUI().notifyWinner(board.findWinner());
            }
        }
        if(!board.existLegalMove()){
            board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
        }
    }

    private int minimax(int depth, boolean maximizingPlayer) {

        if (depth == 2 || !board.existLegalMove()) {
            return evaluate();
        }

        int maxEval;
        int minEval;

        if (maximizingPlayer) {

            maxEval = Integer.MIN_VALUE;

            for (int col = 0; col < 6; col++) {

                    int score = minimax(depth + 1, false);
                    maxEval = Math.max(maxEval, score);

            }

            return maxEval;

        } else {

            minEval = Integer.MAX_VALUE;

            for (int col = 0; col < 6; col++) {

                    int score = minimax(depth + 1, true);
                    minEval = Math.min(minEval, score);


            }

            return minEval;
        }


    }

    private int evaluate() {
        int score = 0;

        // Evaluate horizontal lines
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 6 - 3; col++) {
                int aiCount = 0;
                int opponentCount = 0;
                for (int i = 0; i < 4; i++) {

                    Piece piece = board.getPieceAt(row, col + i);

                    if (piece == Piece.GREEN) {
                        aiCount++;
                    } else if (piece == Piece.BLUE) {
                        opponentCount++;
                    }
                }

            }
        }

        // Evaluate vertical lines
        for (int row = 0; row < 5- 3; row++) {
            for (int col = 0; col < 6; col++) {
                int aiCount = 0;
                int opponentCount = 0;
                for (int i = 0; i < 4; i++) {
                    Piece piece = board.getPieceAt(row + i, col);
                    if (piece == Piece.GREEN) {
                        aiCount++;
                    } else if (piece == Piece.BLUE) {
                        opponentCount++;
                    }
                }

            }

        }

        return score;
    }

}*/
