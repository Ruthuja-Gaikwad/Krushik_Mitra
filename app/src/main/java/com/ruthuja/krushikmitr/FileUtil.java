package com.ruthuja.krushikmitr;

import android.content.Context;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {
    public static MappedByteBuffer loadMappedFile(Context context, String fileName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(context.getAssets().openFd(fileName).getFileDescriptor());
             FileChannel fileChannel = inputStream.getChannel()) {
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        }
    }
}
