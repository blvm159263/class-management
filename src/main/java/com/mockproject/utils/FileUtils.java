package com.mockproject.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FileUtils {
    public static byte[] compressFile(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[10*1024];
        while (!deflater.finished()){
            int size = deflater.deflate(tmp);
            byteArrayOutputStream.write(tmp,0,size);
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decompressFile(byte[] data) throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[10*1024];
        while (!inflater.finished()){
            int count = inflater.inflate(tmp);
            outputStream.write(tmp, 0, count);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }
}
