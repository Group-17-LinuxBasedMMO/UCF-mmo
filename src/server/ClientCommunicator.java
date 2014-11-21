package server;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientCommunicator{
	private Socket socket;
	private boolean connected;
	private Handler handler;

	private class Handler extends Thread{
		private ObjectInputStream in;
                Handler(){
                    try{
                        in = new ObjectInputStream(socket.getInputStream());
                    }
                    catch(Exception e){System.out.println(e);};
                }
		
                public void run(){
			try{
                                if(in.available() != 0){
                                    String stuff = in.readObject().toString();
                                    System.out.println(stuff);
                                }
			}
			catch(Exception e){
				System.out.println(e);
				return;
			}
		}
	}

	public ClientCommunicator(Socket newSocket){

		System.out.println("Creating new Client Comm");
		socket = newSocket;
		connected = true;

		handler = new Handler();
		handler.start();
	}

	public boolean isConnected(){
		return connected;
	}

	public void purge(){
		// Close everything
		try{
			connected = false;
			socket.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public String toString(){
		return new String(socket.toString());
	}
}
