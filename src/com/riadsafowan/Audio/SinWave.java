package com.riadsafowan.Audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SinWave {
    //
    protected static final int SAMPLE_RATE = 40000;

    public static byte[] createSinWaveBuffer(double freq, int ms) {
        int samples = (int) ((ms * SAMPLE_RATE) / 1000);
        byte[] output = new byte[samples];
        //
        double period = (double) SAMPLE_RATE / freq;
        for (int i = 0; i < output.length; i++) {
            double angle = 2.0 * Math.PI * i / period;
            output[i] = (byte) (Math.sin(angle) * 127f);
        }

        return output;
    }

    public static void main(String[] args) throws LineUnavailableException {
        final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
        SourceDataLine line = AudioSystem.getSourceDataLine(af);
        line.open(af, SAMPLE_RATE);
        line.start();

        boolean forwardNotBack = true;

        for (double freq = 40; freq <= 10000; ) {
            byte[] toneBuffer = createSinWaveBuffer(freq, 100);
//            byte [] toneBuffer = {1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,1,2,3,4,50,6,7,8,9,10,11,12,13,14,15,7};
            line.write(toneBuffer, 0, toneBuffer.length);

            freq += 240;
        }

        line.drain();
        line.close();
    }

}
