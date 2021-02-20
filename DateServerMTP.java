import java.net.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import java.lang.*;

//******************************************************************************
// DateServerMTP class
//
//  A multi-threaded server over sockets that uses a thread pool. Returns the 
//  date back to the client.
//******************************************************************************

public class DateServerMTP
{
  public static void main(String[] args)
  {
    try
    {
    
      int socketNumber =  Integer.parseInt(args[0]); // # user entered through shell
      ServerSocket sock = new ServerSocket(socketNumber); 
    
     // ExecutorService threadExecutor = Executors.newCachedThreadPool();// thread pool
      
      /* now listen for connections */
      while (true)
      {
        ExecutorService threadExecutor = Executors.newCachedThreadPool();// thread pool
        Socket client = sock.accept();
         
        // creates a runnable to be assigned a thread in the pool
        Runnable task = new Worker(client);
        threadExecutor.execute(task);
        
       }
    }
    catch (IOException ioe)
    {
    
     System.err.println(ioe);
     
    }
  }
}

//******************************************************************************
// Worker class
// The thread that sends the date back to the client
class Worker implements Runnable {
  
  Socket client; //the client socket passed in from main()
  
  Worker(Socket s){
  
    client = s;
    
  }

  public void run(){ 
    try
    {
    
      PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
          
      /* write the Date to the socket */
      pout.println(new java.util.Date().toString());
      
      /* close the socket and resume */
      /* listening for connections */
      client.close();
      
    }
    catch (IOException ioe)
    {
    
      System.err.println(ioe);
      
    }    
  }    
}