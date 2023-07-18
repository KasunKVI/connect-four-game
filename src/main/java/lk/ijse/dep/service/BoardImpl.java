package lk.ijse.dep.service;

import lk.ijse.dep.controller.BoardController;

public class BoardImpl implements Board{

    private BoardUI boardUI;
    private Piece[][] pieces;


    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;

        int[][] array = new int[NUM_OF_COLS][NUM_OF_ROWS];

        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                array[i][j] = 0;
            }
        }

        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];

        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
               pieces[i][j] = Piece.EMPTY;
            }
        }

    }

    @Override
    public BoardUI getBoardUI(){
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col){

        int empty=-1;

        for (int i = 0; i < NUM_OF_ROWS; i++){
            if(pieces[col][i]==Piece.EMPTY){
                empty = i;
                break;
            }else{
                empty = -1;
            }
        }
        return empty;
    }

    @Override
    public boolean isLegalMove(int col){

        boolean legal=false;

        if(col>=0&&col<6) {
            if (findNextAvailableSpot(col) > -1) {
                legal = true;
            }
        }

        return legal;


    }

    @Override
    public boolean existLegalMove() {

        boolean legal = false;

        for (int i = 0; i < NUM_OF_COLS; i++){
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    legal = true;
                }
            }
        }

        return legal;
    }

    @Override
    public void updateMove(int col,Piece move){

        for (int i = 0; i < NUM_OF_ROWS; i++){
            if(pieces[col][i]==Piece.EMPTY) {
                pieces[col][i] = move;
                break;
            }
        }
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
            pieces[col][row]=move;
    }

    @Override
    public Winner findWinner(){

        Winner winner;
        Piece winnerPieces=Piece.EMPTY;
        int row1=-1;
        int col1=-1;
        int row2=-1;
        int col2=-1;

        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < 2; j++) {
                Piece currentPiece = pieces[i][j];
                if (currentPiece == Piece.BLUE || currentPiece == Piece.GREEN) {
                    if (currentPiece == pieces[i][j+1] && currentPiece == pieces[i][j+2] && currentPiece == pieces[i][j+3]) {
                        winnerPieces = currentPiece;
                        row1 = j;
                        col1 = i;
                        row2 = j + 3;
                        col2 = i;
                    }
                }
            }
        }

        for (int i = 0; i< NUM_OF_ROWS; i++){
            for (int j = 0; j < 3; j++){
                Piece currentPiece = pieces[j][i];
                if (currentPiece == Piece.BLUE || currentPiece == Piece.GREEN) {
                if(currentPiece ==pieces[j+1][i]&&currentPiece ==pieces[j+2][i]&&currentPiece==pieces[j+3][i]){
                        winnerPieces=currentPiece;
                        row1=i;
                        col1=j;
                        row2=i;
                        col2=j+3;
                    }
                }
            }

        }
        if(winnerPieces==Piece.EMPTY){
            winner=new Winner(winnerPieces);
        }else {
            winner=new Winner(winnerPieces,col1,row1,col2,row2);
        }
        return winner;
    }

    @Override
    public Piece getPieceAt(int i, int col) {
        return pieces[col][i];
    }


}
