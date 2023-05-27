package controller;


import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;
    private int round=1;
    private int historyIndex=0;
    private Stack<String> history = new Stack<>();
    private List<String> historyList = new ArrayList<>();

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessGameFrame chessGameFrame;
    private Stack<ChessPiece> EatenPiece= new Stack<>();
    private Stack<ChessboardPoint> EatenPoint= new Stack<>();

    public GameController(ChessboardComponent view, Chessboard model, ChessGameFrame chessGameFrame) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.chessGameFrame=chessGameFrame;
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }


    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setCurrentPlayer(PlayerColor currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        JLabel jLabel1=(JLabel) chessGameFrame.getContentPane().getComponent(0);
        jLabel1.setText("Round:"+round);
        JLabel jLabel2=(JLabel) chessGameFrame.getContentPane().getComponent(1);
        jLabel2.setText("Current:"+currentPlayer);
    }

    private boolean win() {
        boolean win_blue = true;
        boolean win_red = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessPiece chessPiece = model.getChessPieceAt(new ChessboardPoint(i, j));
                if (chessPiece != null) {
                    if (chessPiece.getOwner() == PlayerColor.BLUE) {
                        win_blue = false;
                    } else if (chessPiece.getOwner() == PlayerColor.RED) {
                        win_red = false;
                    }
                }
            }
        }

        if (model.getChessPieceAt(new ChessboardPoint(0, 3)) != null ){win_red=true;}if (model.getChessPieceAt(new ChessboardPoint(8, 3)) != null) {
           win_blue=true;
        }
        if (win_blue || win_red) {
            showWinMessage(win_blue ? PlayerColor.BLUE : PlayerColor.RED); // 显示胜利者的弹窗消息
            return true;
        } else {
            return false;
        }
    }

    private void showWinMessage(PlayerColor winner) {
        String message = "Player " + (winner == PlayerColor.BLUE ? "BLUE" : "RED") + " wins!";
        JOptionPane.showMessageDialog(chessGameFrame, message, "Victory", JOptionPane.INFORMATION_MESSAGE);
        // 在这里添加执行重新开始（restart）的逻辑
    }



    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            if (model.getChessPieceAt(selectedPoint).getOwner() == PlayerColor.RED) {
                round = round + 1;
            }
            // Add move to history
            if (model.getChessPieceAt(selectedPoint).getOwner()==PlayerColor.BLUE){history.add(round+"/" +"0/"+ model.getChessPieceAt(selectedPoint).getRank()+"/"+selectedPoint.getRow() + "/" + selectedPoint.getCol() + "/" + point.getRow() + "/" + point.getCol());
                historyList.add(round+"/" +"0/"+ model.getChessPieceAt(selectedPoint).getRank()+"/"+selectedPoint.getRow() + "/" + selectedPoint.getCol() + "/" + point.getRow() + "/" + point.getCol());}
            else {history.add(round+"/" +"1/"+model.getChessPieceAt(selectedPoint).getRank()+"/"+ selectedPoint.getRow() + "/" + selectedPoint.getCol() + "/" + point.getRow() + "/" + point.getCol());
                historyList.add(round+"/" +"1/"+ model.getChessPieceAt(selectedPoint).getRank()+"/"+selectedPoint.getRow() + "/" + selectedPoint.getCol() + "/" + point.getRow() + "/" + point.getCol());}
            System.out.println(round);
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();win();

            view.repaint();



            // TODO: if the chess enter Dens or Traps and so on
        }
    }
    public void regret(){
            String record=history.pop();
            String[] parts = record.split("/");
            round=Integer.parseInt(parts[0]);
            PlayerColor player = (parts[1].equals("0")) ? PlayerColor.BLUE : PlayerColor.RED;
            currentPlayer=player;
            int rank = Integer.parseInt(parts[2]);
            int selected_x = Integer.parseInt(parts[3]);
            int selected_y = Integer.parseInt(parts[4]);
            int to_x=Integer.parseInt(parts[5]);
            int to_y=Integer.parseInt(parts[6]);
            String name = null;
            if (rank == 1) {
                name = "Mouse";
            }
            if (rank == 2) {
                name = "Cat";
            }
            if (rank == 3) {
                name = "Dog";
            }
            if (rank == 4) {
                name = "Wolf";
            }
            if (rank == 5) {
                name = "Leopard";
            }
            if (rank == 6) {
                name = "Tiger";
            }
            if (rank == 7) {
                name = "Lion";
            }
            if (rank == 8) {
                name = "Elephant";
            }
            model.setChessPiece(new ChessboardPoint(to_x,to_y),null);swapColor();swapColor();
            if (EatenPoint.size()!=0){
        if (EatenPoint.elementAt(EatenPoint.size()-1).getRow()==to_x&&EatenPoint.elementAt(EatenPoint.size()-1).getCol()==to_y){
            model.setChessPiece(EatenPoint.pop(),EatenPiece.pop());}}
            model.setChessPiece(new ChessboardPoint(selected_x,selected_y),new ChessPiece(player,name,rank));
            view.initiateChessComponent(model);
            view.repaint();swapColor();swapColor();

        }


    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, view.ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else if (selectedPoint!=null&& model.isValidCapture(selectedPoint,point)) {
            if (model.getChessPieceAt(selectedPoint).getOwner()==PlayerColor.RED){round=round+1;}
            if (model.getChessPieceAt(selectedPoint).getOwner()==PlayerColor.BLUE){history.add(round+"/" +"0/"+ model.getChessPieceAt(selectedPoint).getRank()+"/"+selectedPoint.getRow() + "/" + selectedPoint.getCol() + "/" + point.getRow() + "/" + point.getCol());
                historyList.add(round+"/" +"0/"+ model.getChessPieceAt(selectedPoint).getRank()+"/"+selectedPoint.getRow() + "/" + selectedPoint.getCol() + "/" + point.getRow() + "/" + point.getCol());}
            else {history.add(round+"/" +"1/"+model.getChessPieceAt(selectedPoint).getRank()+"/"+ selectedPoint.getRow() + "/" + selectedPoint.getCol() + "/" + point.getRow() + "/" + point.getCol());
                historyList.add(round+"/" +"1/"+ model.getChessPieceAt(selectedPoint).getRank()+"/"+selectedPoint.getRow() + "/" + selectedPoint.getCol() + "/" + point.getRow() + "/" + point.getCol());}
            System.out.println(round);
            EatenPiece.add(model.getChessPieceAt(point));
            EatenPoint.add(point);
            view.removeChessComponentAtGrid(point);
            model.captureChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();win();
            view.repaint();
        }
        // TODO: Implement capture function
    }
    public void loadGameFromFile(String path) throws Exception {
        if (!path.endsWith(".txt")) {
            throw new Exception("File format error: 101");
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String line = br.readLine();
            model.clearBoard();
            initialize();
            model.initPieces();
            while (line != null) {
                String[] parts = line.split("/");
                line= br.readLine();
                if (parts.length != 7) {
                    throw new Exception("Invalid chess piece: 103");
                }
                round=Integer.parseInt(parts[0]);
                PlayerColor player = (parts[1].equals("0")) ? PlayerColor.BLUE : PlayerColor.RED;
                int rank = Integer.parseInt(parts[2]);
                int selected_x = Integer.parseInt(parts[3]);
                int selected_y = Integer.parseInt(parts[4]);
                int to_x=Integer.parseInt(parts[5]);
                int to_y=Integer.parseInt(parts[6]);
                String name = null;
                if (rank == 1) {
                    name = "Mouse";
                }
                if (rank == 2) {
                    name = "Cat";
                }
                if (rank == 3) {
                    name = "Dog";
                }
                if (rank == 4) {
                    name = "Wolf";
                }
                if (rank == 5) {
                    name = "Leopard";
                }
                if (rank == 6) {
                    name = "Tiger";
                }
                if (rank == 7) {
                    name = "Lion";
                }
                if (rank == 8) {
                    name = "Elephant";
                }
                try {
                    if (model.isValidMove(new ChessboardPoint(selected_x, selected_y), new ChessboardPoint(to_x, to_y))
                            || model.isValidCapture(new ChessboardPoint(selected_x, selected_y), new ChessboardPoint(to_x, to_y))) {
                        model.setChessPiece(new ChessboardPoint(selected_x, selected_y), null);
                        model.setChessPiece(new ChessboardPoint(to_x, to_y), new ChessPiece(player, name, rank));
                        // Load history
                        history.add(line);
                        view.initiateChessComponent(model);
                        view.repaint();
                    } else {
                        JOptionPane.showMessageDialog(chessGameFrame, "Error type 105: Invalid move", "Error", JOptionPane.ERROR_MESSAGE);model.clearBoard();model.initPieces();view.initiateChessComponent(model);view.repaint();break;
                    }
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(chessGameFrame, "Error type 105: Invalid move", "Error", JOptionPane.ERROR_MESSAGE);model.clearBoard();model.initPieces();view.initiateChessComponent(model);view.repaint();break;
                }

                // Perform any necessary actions with the loaded history data

        }}
            catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }}





    public void saveGame(String filePath) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filePath))) {
            for (String historyEntry : history) {
                bw.write(historyEntry + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
    public void restart(){
        model.clearBoard();
        model.initPieces();
        setRound(1);
        setCurrentPlayer(PlayerColor.RED);
        swapColor();
        view.initiateChessComponent(model);
        view.repaint();
        history=new Stack<>();
        historyList=new ArrayList<>();
        historyIndex=0;
    }
    public void replay(){
        if (historyIndex==0){model.clearBoard();
            model.initPieces();
            setRound(1);
            setCurrentPlayer(PlayerColor.RED);
            swapColor();
        }

                String history1=historyList.get(historyIndex);
                String[] parts = history1.split("/");
                round=Integer.parseInt(parts[0]);
                PlayerColor player = (parts[1].equals("0")) ? PlayerColor.BLUE : PlayerColor.RED;
                int rank = Integer.parseInt(parts[2]);
                int selected_x = Integer.parseInt(parts[3]);
                int selected_y = Integer.parseInt(parts[4]);
                int to_x=Integer.parseInt(parts[5]);
                int to_y=Integer.parseInt(parts[6]);
                String name = null;
                if (rank == 1) {
                    name = "Mouse";
                }
                if (rank == 2) {
                    name = "Cat";
                }
                if (rank == 3) {
                    name = "Dog";
                }
                if (rank == 4) {
                    name = "Wolf";
                }
                if (rank == 5) {
                    name = "Leopard";
                }
                if (rank == 6) {
                    name = "Tiger";
                }
                if (rank == 7) {
                    name = "Lion";
                }
                if (rank == 8) {
                    name = "Elephant";
                }
                model.setChessPiece(new ChessboardPoint(selected_x, selected_y), null);
                model.setChessPiece(new ChessboardPoint(to_x, to_y), new ChessPiece(player, name, rank));

        view.initiateChessComponent(model);
        view.repaint();
        historyIndex++;
    }




    public int getRound() {
        return round;
    }
}

