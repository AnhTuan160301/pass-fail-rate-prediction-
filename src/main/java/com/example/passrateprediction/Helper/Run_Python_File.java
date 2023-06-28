package com.example.passrateprediction.Helper;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Run_Python_File {

    private String resolvePythonScriptPath(String path){
        File file = new File(path);
        return file.getAbsolutePath();
    }
    public void runPythonScript(String filePath) throws IOException, InterruptedException {
//        String line = "python " + resolvePythonScriptPath("C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\Python\\convert_pivot.py") + filePath;
//        CommandLine cmdLine = CommandLine.parse(line);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
//
//        DefaultExecutor executor = new DefaultExecutor();
//        executor.setStreamHandler(streamHandler);
//
//        int exitCode = executor.execute(cmdLine);

        ProcessBuilder builder = new ProcessBuilder();
        //builder.command("python", "test.py");
        builder.command("python", resolvePythonScriptPath("C:\\Users\\pass-fail-rate-prediction-\\src\\main\\java\\com\\example\\passrateprediction\\Python\\convert_pivot.py"),resolvePythonScriptPath(filePath));
        Process process = builder.start();
        process.waitFor();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        bufferedReader.lines().forEach(System.out::println);
    }

    Process mProcess;

    public void runScript(String filePath){
        Process process;
        try{
            process = Runtime.getRuntime().exec("python C:\\Users\\pass-fail-rate-prediction-\\src\\main\\resources\\Python\\convert_pivot.py "+ filePath);
            mProcess = process;
        }catch(Exception e) {
            System.out.println("Exception Raised" + e.toString());
        }
        InputStream stdout = mProcess.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
        String line;
        try{
            while((line = reader.readLine()) != null){
                System.out.println("stdout: "+ line);
            }
        }catch(IOException e){
            System.out.println("Exception in reading output"+ e.toString());
        }
    }
}
