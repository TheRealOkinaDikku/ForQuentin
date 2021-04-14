package CalculusAssistant;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 *
 * This class encapsulates an executor that will run any executable program.
 * It uses the Java Process class to create and execute a program as a process.
 * This allows the CommandExecutor to create streams to communicate to the
 * process. This class assumes that the process communicates using:
 * System.in
 * System.out
 * System.err
 * 
 * 
 * @author luddi
 */
public class CommandExecutor {
    
    private Process commandProcess;
    
    /**
     * This method receives a list of strings that represent the process to run
     * and any configuration commands that must be passed into the process.
     * @param commands
     * @throws IOException
     * @throws InterruptedException 
     */
    public void execCommands(List<String> commands) throws IOException, InterruptedException
    {
        String strCommand = "";
        for (String s : commands) {
            strCommand += s;
        }
       
        // Create the process be run
        commandProcess = Runtime.getRuntime().exec(strCommand);

        //FileInputStream inputStream = new FileInputStream("D:\\SchoolStuff\\CloudComputing\\numbers.txt");
        FileOutputStream outputStream = new FileOutputStream("speechResult.txt");

        // Create and start the threads that will manage the input/output of the process
        SyncErrorOutputPipe  errorRunnable   =   new SyncErrorOutputPipe(commandProcess.getErrorStream(), System.err);
        //SyncInputPipe        inputRunnable   =   new SyncInputPipe( inputStream, commandProcess.getOutputStream());
        SyncOutputPipe       outputRunnable  =   new SyncOutputPipe(commandProcess.getInputStream(), outputStream);

        Thread T1 = new Thread(errorRunnable);
        Thread T2 = new Thread(outputRunnable);
        //Thread T3 = new Thread(inputRunnable);

        T1.start();
        T2.start();
        //T3.start();
        

       
        // wait for the process to complete and then shutdown the Executor.
        int returnCode = commandProcess.waitFor();
        
        T1.join();
        T2.join();
        //T3.join();
       
        System.out.println("Shutting down");
        System.out.println("Return code = " + returnCode);

    }
    
    
    
/**
 * This class receives error output from the process and pipes it to the 
 * Executer defined error output.
 */
    private class SyncErrorOutputPipe implements Runnable
    {
        private final OutputStream outToError;
        private final InputStream inFromProcess;
        private final Scanner scan;
        private final PrintStream writer;
        private boolean done = false;
        

        
        private SyncErrorOutputPipe(InputStream errIn, OutputStream errOut) {
            inFromProcess = errIn;
            outToError = errOut;
            scan = new Scanner(inFromProcess);
            writer = new PrintStream(outToError);
        }
        
        
        
        @Override
        public void run() {
            while ( true ){     // This loop ends when the process shuts down its
                try {           // InputStream. The exception breaks out of the loop
                    String s = scan.nextLine();
                    writer.println(s);
                } catch (Exception e){
                    break;
                }
            }
            
            System.out.println("Error pipe shutting down");
        }
    }    
    
    
    
    /**
     * This class receives output from the process and "pipes" it to the 
     * Executor defined output.
     */
    private class SyncOutputPipe implements Runnable
    {
        final OutputStream outToExecutor;
        private final InputStream inFromProcess;
        private final Scanner scan;
        private final PrintStream writer;

        
        private SyncOutputPipe(InputStream stdIn, OutputStream stdOut) {
            inFromProcess = stdIn;
            outToExecutor = stdOut;
            scan = new Scanner(inFromProcess);
            writer = new PrintStream(outToExecutor);
        }
        

        @Override
        public void run() {
            while(true){    // This loop ends when the process shuts down its
                try{        // InputStream. The exception breaks out of the loop
                    String s = scan.nextLine();
                    writer.println(s);
                } catch(Exception ex){
                    break;
                }
            }
            
            System.out.println("Ouput pipe shuttring down");
        }
    }    
    
    
    
    /**
     * This class receives input from the Executor defined input and pipes it
     * to the Process input.
     */
    private class SyncInputPipe implements Runnable
    {
        private final OutputStream outToProcess;
        private final InputStream inFromExecutor;
        private final Scanner scan;
        private final PrintStream writer;
        

        
        private SyncInputPipe(FileInputStream stdIn, OutputStream stdOut) {
            inFromExecutor = stdIn;
            outToProcess = stdOut;
            scan = new Scanner(inFromExecutor);
            writer = new PrintStream(outToProcess);
        }
        
   
        @Override
        public void run() {

            String s = "";
            String quit = "Q";
            
            do{ // This loop ends when the Process defined termination occurs
                // Or if the InputStream backing the scanner closes
                try {
                    s = scan.nextLine();
                    writer.println(s);
                    writer.flush();
                } catch (Exception ex){
                    break;
                }
                
            } while ( s.compareTo(quit) != 0);
            System.out.println("Input to process shutting down");
        }
        
        
    }
    
}
