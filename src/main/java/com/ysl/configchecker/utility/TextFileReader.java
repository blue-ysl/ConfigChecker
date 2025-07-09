package com.ysl.configchecker.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader
{
    private static TextFileReader instance;

    private TextFileReader()
    {
        // Constructor not in use
    }

    public static TextFileReader getInstance()
    {
        if (instance == null)
        {
            instance = new TextFileReader();
        }
        return instance;
    }

    public LineIndexedTextFileContent getLineIndexedTextFileContentOrNull(Path filePath)
    {
        assert (filePath != null);

        List<String> content = getFileContentOrNull(filePath);
        if (content == null)
        {
            return null;
        }

        return new LineIndexedTextFileContent(filePath, content);
    }

    public List<String> getFileContentOrNull(Path filePath)
    {
        assert (filePath != null);

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath))
        {
            System.err.println("Error: File does not exist at path: " + filePath);
            return null;
        }

        List<String> lines = new ArrayList<>();
        try
        {
            lines = Files.readAllLines(filePath);
        } catch (IOException e)
        {
            System.err.println("Error: IO exception occurred while reading file: " + filePath);
        }

        return lines;
    }
}
