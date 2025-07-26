package com.ysl.configchecker.utility.json;

import com.ysl.configchecker.protocol.DocumentErrorInfo;
import com.ysl.configchecker.utility.LineIndexedTextFileContent;
import org.json.JSONObject;

import java.nio.file.Path;

import static com.ysl.configchecker.utility.ConsoleTextColor.ANSI_RED;

public class JsonUtility
{
    private static JsonUtility instance;

    private JsonUtility()
    {
        // Constructor not in use
    }

    public static JsonUtility getInstance()
    {
        if (instance == null)
        {
            instance = new JsonUtility();
        }
        return instance;
    }

    public JSONObject getJsonFromFileContent(LineIndexedTextFileContent content)
    {
        assert (content != null);

        String contentInString = content.toString();

        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(contentInString);
        } catch (Exception exception)
        {
            DocumentErrorInfo errorInfo = getJsonFormatErrorInfoOrNull(content.getFilePath(), exception.getMessage());
            if (errorInfo == null)
            {
                System.err.println("Failed to get error info from file: " + content.getFilePath());
            }
            else
            {
                printErrorInfoAndGuidance(content, errorInfo);
            }
        }

        return jsonObject;
    }

    private DocumentErrorInfo getJsonFormatErrorInfoOrNull(String filePath, String errorMessage)
    {
        assert (filePath != null);
        assert (errorMessage != null);

        DocumentErrorInfo docErrInfo = null;

        try
        {
            int locationInfoOpen = errorMessage.lastIndexOf('[');
            int locationInfoClose = errorMessage.lastIndexOf(']');

            String errorLocationInfo = errorMessage.substring(locationInfoOpen + 1, locationInfoClose);
            String[] tokens = errorLocationInfo.split(" ");

            docErrInfo = new DocumentErrorInfo(Path.of(filePath));

            docErrInfo.setLineIndex(Integer.parseInt(tokens[3]))
                    .setColumnIndex(Integer.parseInt(tokens[1]))
                    .setErrorMessage(errorMessage.substring(0, locationInfoOpen).trim());

        } catch (Exception exception)
        {
            System.err.println(exception.getMessage());
        }

        return docErrInfo;
    }

    private void printErrorInfoAndGuidance(LineIndexedTextFileContent content, DocumentErrorInfo docErrInfo)
    {
        int displayStartLnNo = docErrInfo.getLineIndex() - 2;
        if (displayStartLnNo < 1)
        {
            displayStartLnNo = 1;
        }

        int displayEndLnNo = docErrInfo.getLineIndex();
        if (displayEndLnNo > content.getNumberOfLines())
        {
            displayEndLnNo = content.getNumberOfLines();
        }

        System.out.println("==============================================");

        for (int lnNo = displayStartLnNo; lnNo <= displayEndLnNo; lnNo++)
        {
            System.out.printf("%3d | %s%n", lnNo, content.getLineOrNull(lnNo));
        }
        System.out.printf("%s     %s%s\n", ANSI_RED, " ".repeat(docErrInfo.getColumnIndex()), "^---- " + docErrInfo.getErrorMessage());

        System.out.println("==============================================");
    }
}
