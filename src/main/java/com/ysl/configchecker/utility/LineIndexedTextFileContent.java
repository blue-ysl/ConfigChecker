package com.ysl.configchecker.utility;

import java.nio.file.Path;
import java.util.List;

public class LineIndexedTextFileContent
{
    private final Path filePath;
    private final List<String> lines;

    public LineIndexedTextFileContent(Path fileName, List<String> lines)
    {
        assert (fileName != null);
        assert (lines != null);

        this.filePath = fileName;
        this.lines = lines;
    }

    public String getLineOrNull(int lineNumber)
    {
        if (lineNumber <= 0 || lineNumber > lines.size())
        {
            return null;
        }

        return lines.get(lineNumber - 1);
    }

    public boolean isLineNumberInRange(int lineNumber)
    {
        return lineNumber > 1 && lineNumber <= lines.size();
    }

    public Integer getNumberOfLines()
    {
        return lines.size();
    }

    public String getFilePath()
    {
        return filePath.toString();
    }
}
