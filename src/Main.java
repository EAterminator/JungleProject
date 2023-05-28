import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;

import javax.swing.*;


import view.LoginFrame;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(() -> {
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(), mainFrame);
                mainFrame.setController(gameController);
                mainFrame.setVisible(true);
            });
            loginFrame.setVisible(true);
        });
    }
}
