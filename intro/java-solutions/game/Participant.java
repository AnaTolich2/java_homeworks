package game;

public class Participant {
    private final Player type;
    private int prevOpponent;
    public Participant(Player type){
        this.type = type;
    }
    public int getPrevOpponent() {
        return prevOpponent;
    }
    public void assignPrevOpp(int prevOpponent){
        this.prevOpponent = prevOpponent;
    }
    public Player getType() {
        return type;
    }
}
