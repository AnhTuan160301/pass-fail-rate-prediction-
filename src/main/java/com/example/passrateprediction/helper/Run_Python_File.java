package com.example.passrateprediction.helper;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Run_Python_File {

    private String resolvePythonScriptPath(String path){
        File file = new File(path);
        return file.getAbsolutePath();
    }
    public void runPythonScript() throws Exception{
        String line = "python " + resolvePythonScriptPath("C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\Python\\convert_pivot.py");
        CommandLine cmdLine = CommandLine.parse(line);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(streamHandler);

        int exitCode = executor.execute(cmdLine);
        System.out.println("Done");
    }
}
