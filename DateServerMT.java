import java.net.*;
import java.io.*;

//******************************************************************************
// DateServerMT class
//
//  A multi-threaded server over sockets, that creates its own threads. Returns 
//  the date back to the client.
//******************************************************************************

public class DateServerMT
{
  public static void main(String[] args)
  {
    try
    {
    
      int socketNumber =  Integer.parseInt(args[0]); // # user entered through shell
      ServerSocket sock = new ServerSocket(socketNumber);
      
      /* now listen for connections */
      while (true)
      {
      
        Socket client = sock.accept();
        
        //Create new threads instead of using main thread
        Worker task = new Worker(client);
        task.start();
        
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
class Worker extends Thread { 
  
  Socket client;//the client socket passed in from main()
  
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