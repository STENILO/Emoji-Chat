/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.Naming;
import java.rmi.registry.Registry;

/**
 *
 * @author Mikkel
 */
public class JavaServer {
	private static final int PORT = IKatServer.PORT;
	private static final String HOST = IKatServer.HOST;
	private static final String SERVICE = IKatServer.SERVICE;
	private static final String FULL_ADDRESS = IKatServer.FULL_ADDRESS;
	
    public static void main(String[] arg) throws Exception
	{
		// Enten: Kør programmet 'rmiregistry' fra mappen med .class-filerne, eller:
     	System.setProperty("java.rmi.server.hostname", HOST);
		java.rmi.registry.LocateRegistry.createRegistry(PORT); // start i server-JVM
		IKatServer logic = new ServerLogic();
 		Naming.rebind(FULL_ADDRESS, logic);
		System.out.println("Katserver registreret på port:" + PORT);
		Registry reg = java.rmi.registry.LocateRegistry.getRegistry(PORT);
		System.out.println(reg.lookup(SERVICE));
	}
}
