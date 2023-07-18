package lk.ijse.dep.service;

public abstract class Player{

    public Player(Board board) {
        this.board = board;
    }

    protected Board board;

    public Player() {

    }

    abstract public void movePiece(int indexOf);
}
