package emoji_server;

public interface IEmoji extends java.rmi.Remote {

	public static final int PORT = 4093;
	public static final String HOST = "ubuntu4.javabog.dk";
//	public static final String HOST = "localhost";
	public static final String SERVICE = "emoji";
	public static final String FULL_ADDRESS = "rmi://" + HOST + ":" + PORT + "/" + SERVICE;

	public void addCat(String cat, String name) throws java.rmi.RemoteException;
	public String getCat() throws java.rmi.RemoteException;
	public String getCat(String name) throws java.rmi.RemoteException;
	public String getCat(int index) throws java.rmi.RemoteException;
}
