package lk.ijse.dep.service;

public class HumanPlayer extends Player {

    public HumanPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int indexOf) {

        if(board.isLegalMove(indexOf)){

           board.updateMove(indexOf,board.findNextAvailableSpot(indexOf),Piece.BLUE);
            board.getBoardUI().update(indexOf,true);

           if(board.findWinner().getWinningPiece()==Piece.BLUE){

               board.getBoardUI().notifyWinner(board.findWinner());

           }
                if(!board.existLegalMove()){
                   board.getBoardUI().notifyWinner(board.findWinner());
            }

           
       }
    }
}
