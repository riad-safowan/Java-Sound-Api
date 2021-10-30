package com.riadsafowan.Audio;

public class Test {
    private static final int SAMPLE_RATE = 10000;
    public static void main(String[] args) {
        byte[] array = createSinWaveBuffer(500, 100);
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j <= (array[i]+180)/3; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
    public static byte[] createSinWaveBuffer(double freq, int ms) {
        int samples = (int) ((ms * SAMPLE_RATE) / 1000);
        byte[] output = new byte[samples];

        double period = (double) SAMPLE_RATE / freq;
        for (int i = 0; i < output.length; i++) {
            double angle = 2.0 * Math.PI * i / period;
            output[i] = (byte) (Math.sin(angle) * 127f);
        }

        return output;
    }
}
