package com.riadsafowan.Audio;

import javax.sound.sampled.*;
import java.io.*;

public class LiveAnnouncer {
    public static void main(String[] args) throws LineUnavailableException {

        AudioFormat format = new AudioFormat(10000.0f, 8, 1, true, true);
        TargetDataLine microphone;
        SourceDataLine speakers;

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int numBytesRead;
        int CHUNK_SIZE = 16;
        byte[] data = new byte[microphone.getBufferSize() / 5];
        microphone.start();

        int bytesRead = 0;
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        speakers.open(format);
        speakers.start();

        while (bytesRead < 1000000) {
            numBytesRead = microphone.read(data, 1, CHUNK_SIZE);
            bytesRead += numBytesRead;
            // write the mic data to a stream for use later
            out.write(data, 0, numBytesRead);
            // write mic data to stream for immediate playback
            speakers.write(data, 1, numBytesRead);
        }

        speakers.drain();
        speakers.close();
        microphone.close();
    }
}
