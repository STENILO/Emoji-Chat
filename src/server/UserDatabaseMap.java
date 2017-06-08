/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mikkel
 */
public class UserDatabaseMap implements IUserDataBase {

	private static final Map<String, String> USERS = new HashMap<>();

	@Override
	public boolean hasUser(String name, String password) {
		try {
			//Brugeradmin ba = (Brugeradmin2) Naming.lookup("rmi://localhost:4089/brugeradmin");
			Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
			Bruger b = ba.hentBruger(name, password);

			System.out.println("User found in remote database");
			return true;
		} catch (NotBoundException ex) {
			Logger.getLogger(ServerLogic.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MalformedURLException ex) {
			Logger.getLogger(ServerLogic.class.getName()).log(Level.SEVERE, null, ex);
		} catch (RemoteException ex) {
			Logger.getLogger(UserDatabaseMap.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException e) {
			System.out.println("User nor found in remote - checking local");
			return (USERS.containsKey(name) && USERS.get(name).equals(password));
		}

		System.out.println("User nor found in remote - checking local");
		return (USERS.containsKey(name) && USERS.get(name).equals(password));
	}

	@Override
	public boolean addUser(String name, String password) {
		return USERS.put(name, password) == null;
	}
}
