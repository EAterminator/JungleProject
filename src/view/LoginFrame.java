package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.GameController;
import model.Chessboard;
import java.io.*;


public class LoginFrame extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JButton loginButton;
    private JButton registerButton;
    private Runnable mainMethod;

    public LoginFrame(Runnable mainMethod) {
        setTitle("Jungle");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 5, 5));

        JLabel usernameLabel = new JLabel("Your password：");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        loginButton = new JButton("log in");
        loginButton.addActionListener(this);
        add(loginButton);

        registerButton = new JButton("register");
        registerButton.addActionListener(this);
        add(registerButton);

        this.mainMethod = mainMethod;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();

            // 验证用户名
            if (isValidUser(username)) {
                SwingUtilities.invokeLater(mainMethod);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Wrong password!");
            }
        } else if (e.getSource() == registerButton) {
            // 处理注册按钮点击事件
            String username = usernameField.getText();

            // 向文件中添加用户名
            try {
                FileWriter fileWriter = new FileWriter("users.txt", true);
                fileWriter.write(username + "\n");
                fileWriter.close();
                JOptionPane.showMessageDialog(this, "Registered successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed!Please try again!");
            }
        }
    }

    // 验证用户名是否存在
    private boolean isValidUser(String username) {
        try {
            FileReader fileReader = new FileReader("users.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals(username)) {
                    bufferedReader.close();
                    return true;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}