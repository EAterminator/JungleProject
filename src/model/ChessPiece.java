package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        if (this.rank>= target.getRank()&&this.rank!=8&&this.rank!=1)
        {return true;} else if (this.rank==8&&target.getRank()==1) {
            return false;
        } else if (this.rank==1&&(target.getRank()==8||target.getRank()==1)) {
            return true;
        } else if (this.rank==8&&target.getRank()==8||this.rank> target.getRank()) {
            return true;
        } else {return false;}
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

