/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Mikkel
 */
public interface IUserDataBase {
	
	public boolean hasUser(String name, String password);
	public boolean addUser(String name, String password);
	
}
