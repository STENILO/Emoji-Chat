/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import brugerautorisation.transport.rmi.Brugeradmin;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import emoji_server.IEmoji;
import java.rmi.RemoteException;

/**
 *
 * @author JoachimØstergaard
 */
public class Analyse {

	public String analyse(String message, String name) throws NotBoundException, MalformedURLException, RemoteException {

		if (!message.equals("")) {
			if (message.charAt(0) == '!') {
				if (message.length() > 1 && (message.charAt(1) == 'E' || message.charAt(1) == 'e')) {
					IEmoji emoji;
					emoji = (IEmoji) Naming.lookup(IEmoji.FULL_ADDRESS);
					if (emoji == null) {
						return "/Ingen forbindelse til emoji server";
					}
					String[] messageSplit = message.split(" ", 4);
					if (messageSplit.length < 2) {
						return "/Der skal være mindst et parameter til !e";
					}
					if (messageSplit[1].equalsIgnoreCase("random")) {
						return emoji.getCat();
					}
					if (messageSplit[1].equalsIgnoreCase("save")) {
						if (messageSplit.length < 4) {
							return "/Der skal være både navn og emote til !e save";
						}
						emoji.addCat(messageSplit[3], messageSplit[2]);
						return "added a new cat named " + messageSplit[2] + " looking like this " + messageSplit[3];
					} else {
						String emote = emoji.getCat(messageSplit[1]);
						return emote != null ? emote : "/Der var ingen emote med det navn";
					}
				} else {
					return "/Komando ikke genkendt";
				}
			}
			if (message.charAt(0) == '/') {
				String[] messageSplit = message.split(" ", 3);
				if (messageSplit[0].equals("/password")) {
					Brugeradmin ba;
					try {
						ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
						if (ba == null) {
							return "/Ingen forbindelse til bruger serveren";
						}
						if (messageSplit.length >= 3) {
							ba.ændrAdgangskode(name, messageSplit[1], messageSplit[2]);
						} else {
							return "/For få parametre til at ændre kodeord";
						}
						return "/Kodeord ændret";
					} catch (NotBoundException ex) {
						Logger.getLogger(ServerLogic.class.getName()).log(Level.SEVERE, null, ex);
					} catch (MalformedURLException ex) {
						Logger.getLogger(ServerLogic.class.getName()).log(Level.SEVERE, null, ex);
					} catch (IllegalArgumentException ex){
						return "/Den gamle adgangskode er ikke korrekt";
					}
				}
				return "/Komando ikke genkendt";
			}
		}

		return message;
	}
}
