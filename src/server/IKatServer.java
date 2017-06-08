package server;
import java.util.ArrayList;


/**
 *
 * @author Mikkel Westh
 */
public interface IKatServer extends java.rmi.Remote{
	public static final int PORT = 4092;
	public static final String HOST = "ubuntu4.javabog.dk";
//	public static final String HOST = "localhost";
	public static final String SERVICE = "kat_chat";
	public static final String FULL_ADDRESS = "rmi://" + HOST + ":" + PORT + "/" + SERVICE;
	
    public int login(String name, String password) throws java.rmi.RemoteException;
	public void logOut(int session) throws java.rmi.RemoteException;
    public ArrayList<String> getMessage( int session) throws java.rmi.RemoteException;
    public String sentMessage(String message, int session) throws java.rmi.RemoteException;
}
