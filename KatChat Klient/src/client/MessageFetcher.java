/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import server.IKatServer;

/**
 *
 * @author Mikkel
 */
public class MessageFetcher implements Runnable{

	JTextArea textArea;
	int session;
	
	public MessageFetcher(JTextArea textArea, int session){
		this.textArea = textArea;
		this.session = session;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				IKatServer server = ChatUI.doConnect();
				List<String> messages = server.getMessage(session);
				if (messages == null || messages.get(0).equals("/Logged out")){
					textArea.setText(textArea.getText()+ "Forbindelse til serveren blev afbrudt\n");
					return;
				}
				for(String msg : messages){
					textArea.setText(textArea.getText() + msg + "\n");
				}
			} catch (RemoteException ex) {
				textArea.setText(textArea.getText()+ "Forbindelse til serveren blev afbrudt\n");
				return;
			}
		}
	}
	
}
