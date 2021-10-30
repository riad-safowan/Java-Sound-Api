package com.riadsafowan.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BinauralAudioPlayer {

    static Clip clipR;
    static Clip clipL;
    static AudioInputStream audioInputStreamR;
    static AudioInputStream audioInputStreamL;
    static String filePath;

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        int vd= 20;
        long td= (long) 2.0;

        filePath = "src/com/riadsafowan/Audio/warriyo.wav";
        audioInputStreamR =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clipR = AudioSystem.getClip();
        clipR.open(audioInputStreamR);
        float volumeR = 100f;
        FloatControl gainControlVolumeR = (FloatControl) clipR.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControlVolumeR.getMaximum() - gainControlVolumeR.getMinimum();
        float gainR = (range * volumeR/100) + gainControlVolumeR.getMinimum();
        gainControlVolumeR.setValue(gainR);
        FloatControl gainControlBalanceR = (FloatControl) clipR.getControl(FloatControl.Type.BALANCE); //PAN
        gainControlBalanceR.setValue(1f);

        audioInputStreamL =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clipL = AudioSystem.getClip();
        clipL.open(audioInputStreamL);
        float volumeL = 100f - vd;
        FloatControl gainControlVolumeL = (FloatControl) clipL.getControl(FloatControl.Type.MASTER_GAIN);
        float gainL = (range * volumeL/100) + gainControlVolumeR.getMinimum();
        gainControlVolumeL.setValue(gainL);
        FloatControl gainControlBalanceL = (FloatControl) clipL.getControl(FloatControl.Type.BALANCE);
        gainControlBalanceL.setValue(-1f);

        Thread threadR = new Thread(new Runnable() {
            @Override
            public void run() {
                clipR.start();
                System.out.println("clip 1 played");
                try {
                    Thread.sleep(td);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clipL.start();
                System.out.println("clip 2 played");
                try {
                    Thread.sleep(clipR.getMicrosecondLength());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadR.start();

    }

}
