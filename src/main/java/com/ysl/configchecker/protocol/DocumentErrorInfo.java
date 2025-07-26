package com.ysl.configchecker.protocol;

import java.nio.file.Path;

public class DocumentErrorInfo
{
    private Path fiePath;
    private Integer lineIndex;
    private Integer columnIndex;
    private String errorMessage;

    public DocumentErrorInfo(Path filePath)
    {
        this.fiePath = filePath;
    }

    public DocumentErrorInfo setLineIndex(Integer lineIndex)
    {
        this.lineIndex = lineIndex;
        return this;
    }

    public DocumentErrorInfo setColumnIndex(Integer columnIndex)
    {
        this.columnIndex = columnIndex;
        return this;
    }

    public DocumentErrorInfo setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
        return this;
    }

    public String getFilePath()
    {
        return fiePath.toString();
    }

    public Integer getLineIndex()
    {
        return lineIndex;
    }

    public Integer getColumnIndex()
    {
        return columnIndex;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
}
