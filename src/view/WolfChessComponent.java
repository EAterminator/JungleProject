package view;


import model.PlayerColor;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class WolfChessComponent extends ChessComponent {

    public WolfChessComponent(PlayerColor owner, int size) {
        super(owner, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 设置背景色
        g2.setBackground(owner.getColor());
        g2.clearRect(0, 0, getWidth(), getHeight());

        // 添加图片
        ImageIcon imageIcon = new ImageIcon("D:\\javaprojects\\JungleDefault\\resource\\left\\3WolfLeft.png");
        Image image = imageIcon.getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        // 绘制选中的圆形边框
        if (isSelected()) {
            g2.setColor(Color.yellow);
            g2.drawOval(0, 0, getWidth(), getHeight());

            // play background music
            try {
                // Open an audio input stream.
                File soundFile = new File("resource/zhiyin.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

                // Get a sound clip resource.
                Clip clip = AudioSystem.getClip();

                // Open audio clip and load samples from the audio input stream.
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}


