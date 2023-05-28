package model;

import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();//river cell list
    private final Set<ChessboardPoint> trapCellBlue = new HashSet<>();//blue trap cell list
    private final Set<ChessboardPoint> trapCellRed = new HashSet<>();//red trap cell list

    public Chessboard() {
        this.grid =
                new Cell[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));
        trapCellBlue.add(new ChessboardPoint(0,2));
        trapCellBlue.add(new ChessboardPoint(0,4));
        trapCellBlue.add(new ChessboardPoint(1,3));
        trapCellRed.add(new ChessboardPoint(8,2));
        trapCellRed.add(new ChessboardPoint(8,4));
        trapCellRed.add(new ChessboardPoint(7,3));
    }

    public void initPieces() {
        grid[0][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));grid[0][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));grid[1][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Mouse",1));grid[2][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));grid[2][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));grid[8][0].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));grid[7][1].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.RED, "Mouse",1));grid[6][4].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));grid[6][0].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        removeChessPiece(dest);
        setChessPiece(dest, removeChessPiece(src));
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if ((getChessPieceAt(src).getOwner()==PlayerColor.BLUE&&!(dest.getCol()==3&&dest.getRow()==0))||(getChessPieceAt(src).getOwner()==PlayerColor.RED&&!(dest.getCol()==3&&dest.getRow()==8))){
        if (getChessPieceAt(src).getRank()!=7&&getChessPieceAt(src).getRank()!=6&&getChessPieceAt(src).getRank()!=1){//when not lion,tiger,mouse
        if (getChessPieceAt(src) == null || riverCell.contains(dest)||calculateDistance(src,dest)!=1) {
            return false;
        }else return true;}
        else if (getChessPieceAt(src).getRank()==1){if (calculateDistance(src,dest)!=1) {
            return false;
        }else return true;}//when is a mouse
        else {if (getChessPieceAt(src) == null || riverCell.contains(dest)) {//when is tiger or lion
            return false;
        }else if (((src.getCol()==dest.getCol()||src.getRow()==dest.getRow())&&(Math.abs(src.getCol()-dest.getCol())==3||Math.abs(src.getRow()-dest.getRow())==4))&&(riverCellNearby(src)&&riverCellNearby(dest)&&checkMouseInRiver(src,dest)))
        {
            return true;
        }else if (calculateDistance(src, dest)!=1)return false;else return true;}}else return false;
    }
    public boolean riverCellNearby(ChessboardPoint point){
        return riverCell.contains(new ChessboardPoint(point.getRow() -1, point.getCol()))||riverCell.contains(new ChessboardPoint(point.getRow() +1, point.getCol()))||riverCell.contains(new ChessboardPoint(point.getRow(), point.getCol()+1))||riverCell.contains(new ChessboardPoint(point.getRow() , point.getCol()-1));
    }
    public boolean checkMouseInRiver(ChessboardPoint src,ChessboardPoint dest){
        if (src.getRow()==dest.getRow()&&src.getCol()>dest.getCol()){
            return getChessPieceAt(new ChessboardPoint(src.getRow(), src.getCol()-1))==null&&getChessPieceAt(new ChessboardPoint(src.getRow(), src.getCol()-2))==null;
        } else if (src.getRow()==dest.getRow()&&src.getCol()<dest.getCol()) {
            return getChessPieceAt(new ChessboardPoint(src.getRow(), src.getCol()+1))==null&&getChessPieceAt(new ChessboardPoint(src.getRow(), src.getCol()+2))==null;
        }
        if (src.getCol()==dest.getCol()&&src.getRow()> dest.getRow()){
           return getChessPieceAt(new ChessboardPoint(src.getRow()-1, src.getCol() ))==null&&getChessPieceAt(new ChessboardPoint(src.getRow()-2, src.getCol() ))==null&&getChessPieceAt(new ChessboardPoint(src.getRow()-3, src.getCol() ))==null;
        } else if (src.getCol()==dest.getCol()&&src.getRow()< dest.getRow()) {
            return getChessPieceAt(new ChessboardPoint(src.getRow()+1, src.getCol() ))==null&&getChessPieceAt(new ChessboardPoint(src.getRow()+2, src.getCol() ))==null&&getChessPieceAt(new ChessboardPoint(src.getRow()+3, src.getCol() ))==null;
        }
        return true;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
        ChessPiece destPiece = getChessPieceAt(dest);
        if (srcPiece.getOwner()!=destPiece.getOwner()){
        if (srcPiece == null || destPiece == null) {
            // Throw an exception or return false depending on your application's requirements
            throw new IllegalArgumentException("Source or destination point is invalid");
        }
        if (!srcPiece.canCapture(destPiece)&& !trapCellRed.contains(dest) && !trapCellBlue.contains(dest)) {
            return false;
        }
        if (riverCell.contains(src)&&riverCell.contains(dest)){return true;}
        if (riverCell.contains(src) && !trapCellRed.contains(dest) && !trapCellBlue.contains(dest)) {
            return false;
        }
        if (srcPiece.getOwner() == PlayerColor.BLUE && trapCellBlue.contains(dest)) {
            return true;
        }
        if (srcPiece.getOwner() == PlayerColor.RED && trapCellRed.contains(dest)) {
            return true;
        }
        return true;
    }return false;}
    public void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                setChessPiece(new ChessboardPoint(i,j), null);
            }
        }
    }


}
