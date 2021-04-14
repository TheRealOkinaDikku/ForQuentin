/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runjavaprogram;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author luddi
 */
public class RunJavaProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        CommandExecutor cmdExec = new CommandExecutor();
        ArrayList<String> commands = new ArrayList<>();
        String runCommand = "java -jar ";
        String runParam   = "D:\\SchoolStuff\\CloudComputing\\MersennePrimeFilter\\dist\\MersennePrimeFilter.jar";
        commands.add(runCommand);
        commands.add(runParam);
        cmdExec.execCommands(commands);

        System.out.println("Programm shutting down");

    
    }

    
    
        //commands.add("cd C:\\Users\\luddi\\Documents\\NetBeansProjects\\TestComplexProject\\dist");
        //commands.add("dir");
        //cmdExec.execCommands(commands);
    

private void execCommand() throws IOException, InterruptedException
{
            
//    String[] command =
//    {
//        "cmd",
//       
//           
//    };
//    
//    Scanner scan = new Scanner(System.in);
//    String path = scan.next();
//    String name = scan.next();
//    
//    Process p = Runtime.getRuntime().exec(command);
//    
//    new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
//    new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
//    PrintWriter stdin = new PrintWriter(p.getOutputStream());
//    stdin.println("cd " + path);
//    stdin.println("dir");
//    stdin.println("java -jar " + name);
//    
//    // write any other commands you want here
//    stdin.close();
//    int returnCode = p.waitFor();
//    System.out.println("Return code = " + returnCode);
//
//}
//    
//class SyncPipe implements Runnable
//{
//private SyncPipe(InputStream istrm, OutputStream ostrm) {
//      istrm_ = istrm;
//      ostrm_ = ostrm;
//  }
//  public void run() {
//      try
//      {
//          final byte[] buffer = new byte[1024];
//          for (int length = 0; (length = istrm_.read(buffer)) != -1; )
//          {
//              ostrm_.write(buffer, 0, length);
//          }
//      }
//      catch (Exception e)
//      {
//          e.printStackTrace();
//      }
//  }
//  private final OutputStream ostrm_;
//  private final InputStream istrm_;
}    
    
    
    
}
