package view;

import controller.GameController;
import model.Chessboard;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.EmptyStackException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    public final int HEIGTH;
    private JLabel background;

    private final int ONE_CHESS_SIZE;
    private GameController gameController;
    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addLabel1();
        addLabel2();
        addChessboard();
        //addLabel();
        addRestartButton();
        addBackButton();
        addLoadButton();
        addSaveButton();
        addReplayButton();
        addChangeBackgroundButton();
        addMusicButton();
        addEasyAIButton();

        setResizable(true);
        setTitle("CS109 Java project");
        setLayout(new BorderLayout());
        background = new JLabel();
        add(background);
        setBackgroundImage("D:\\javaprojects\\JungleDefault\\resource\\dessert.jpg");  // 设置默认背景
    }
    public ChessGameFrame(int width, int height,GameController gameController) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        this.gameController=gameController;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addLabel1();
        addLabel2();
        addRestartButton();
        addBackButton();
        addChessboard();
        addLoadButton();
        addSaveButton();
        addMusicButton();
        addEasyAIButton();
        background = new JLabel();
        add(background);
        setBackgroundImage("D:\\javaprojects\\JungleDefault\\resource\\dessert.jpg");  // 设置默认背景
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }
    public void setController(GameController gameController) {
        this.gameController = gameController;}

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel1() {
        JLabel statusLabel = new JLabel("Round:");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    private void addLabel2() {
        JLabel statusLabel = new JLabel("Current:");
        statusLabel.setLocation(HEIGTH, HEIGTH / 14);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> {JOptionPane.showMessageDialog(this, "Restarted!");
            gameController.restart();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 +60);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);


    }
    private void addBackButton() {
        JButton button = new JButton("Back");
        button.addActionListener((e) -> {
        try{gameController.regret();}
        catch (EmptyStackException emptyStackException){
            JOptionPane.showMessageDialog(this, "already at the start of the game!", "Warning", JOptionPane.ERROR_MESSAGE);
        }});
        button.setLocation(HEIGTH, HEIGTH / 10 +330);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addReplayButton() {
        JButton button = new JButton("Replay");
        button.addActionListener((e) -> {
           try {
               gameController.replay();
           }catch (IndexOutOfBoundsException indexOutOfBoundsException){
               JOptionPane.showMessageDialog(this, "already at the end of the game!", "Warning", JOptionPane.ERROR_MESSAGE);
           }});
        button.setLocation(HEIGTH, HEIGTH / 10 + 420);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH+100, HEIGTH / 10 + 150);
        button.setSize(100, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            gameController.saveGame(path);
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 150);
        button.setSize(100, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            try {
                gameController.loadGameFromFile(path);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading game: " + ex.getMessage());
            }
        });
    }
    private void addChangeBackgroundButton() {
        JButton button = new JButton("Background");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");setBackgroundImage(path);});}
    public void setBackgroundImage(String imagePath) {
        background.setIcon(new ImageIcon(imagePath));
        background.setBounds(0, 0, WIDTH, HEIGHT);
    }
    private Clip audioClip;
    private void addMusicButton() {
        JButton button = new JButton("Music");
        button.setLocation(HEIGTH, HEIGTH / 10 + 510);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        // Add action listener to button
        button.addActionListener((e) -> {
            if (audioClip == null) {
                // Open file chooser dialog to select audio file
                JFileChooser fileChooser = new JFileChooser("resource");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV files", "wav");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File audioFile = fileChooser.getSelectedFile();
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                        AudioFormat format = audioStream.getFormat();
                        DataLine.Info info = new DataLine.Info(Clip.class, format);
                        audioClip = (Clip) AudioSystem.getLine(info);
                        audioClip.open(audioStream);
                        audioClip.start();
                        button.setText("Stop Music");
                        JOptionPane.showMessageDialog(this, "Music playing...");
                    } catch (Exception ex) {
                        // Show an error message if an exception occurs
                        JFrame errorFrame = new JFrame("Error");
                        errorFrame.setSize(300, 100);
                        errorFrame.setLocationRelativeTo(null);

                        JLabel errorLabel = new JLabel("Failed to open the audio file.");
                        errorLabel.setHorizontalAlignment(JLabel.CENTER);

                        errorFrame.add(errorLabel);
                        errorFrame.setVisible(true);
                    }
                }
            } else {
                audioClip.stop();
                audioClip.close();
                audioClip = null;
                button.setText("Music");
                JOptionPane.showMessageDialog(this, "Music stopped.");
            }
        });
    }
    public void addEasyAIButton(){
        JButton button = new JButton("EasyAI");
        button.addActionListener((e) -> {
            try {
                gameController.EasyAI();
            }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                JOptionPane.showMessageDialog(this, "already at the end of the game!", "Warning", JOptionPane.ERROR_MESSAGE);
            }});
        button.setLocation(HEIGTH, HEIGTH / 10 + 600);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

}



