package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import server.IKatServer;

public class ChatUI {

	private boolean isUserNameOK = false;
	private int sessionID;
	private JTextArea recievedText;
	private JButton buttonSend;
	private	JButton buttonLogout;
	private	JButton buttonLogin;
	private JTextField textToSend;
	private JPanel panelTop;
	private JTextField textUserName;
	private JTextField textPswd;
	private JLabel labelUser;
	private JLabel labelName;
	private JLabel labelPswd;




	public static IKatServer doConnect() {
		System.out.println("client.ChatUI.doConnect()");
		try {
			IKatServer srv = (IKatServer) Naming.lookup(IKatServer.FULL_ADDRESS);
			System.out.println("connected to server at" + IKatServer.FULL_ADDRESS);
			return srv;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
		
	}

	private static int doLogin(String userName, String pswd) {
		System.out.println("client.ChatUI.doLogin()");
		IKatServer srv = doConnect();
		int sID = 0;
		try {
			sID = srv.login(userName, pswd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Logged in with ID =" + sID);
		return sID;
	}

	private static void doSendMsg(JTextField input,JTextArea recievedText, int sessionID) {
		System.out.println("Sending message:" + input.getText());
		IKatServer srv = doConnect();

		try {
			String status = srv.sentMessage(input.getText(), sessionID);
			if (!status.equals("")) {
				if (status.equals("/illegal session")){
					status = "Din session er ikke længere gyldig, prøv at logge ind igen";
				}
				recievedText.setText(recievedText.getText() + status + "\n");
			}
			input.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void reset(){
		this.textToSend.setText("");
		this.textToSend.setVisible(false);
		this.buttonSend.setVisible(false);
		this.panelTop.remove(labelName);
		this.panelTop.remove(buttonLogout);
		textUserName.setText("");
		this.panelTop.add(textUserName);
		this.panelTop.add(labelPswd);
		textPswd.setText("");
		this.panelTop.add(textPswd);
		this.panelTop.add(buttonLogin);
		IKatServer server = doConnect();
		try {
			server.logOut(sessionID);
		} catch (RemoteException ex) {
		}
		sessionID = 0;
		recievedText.setText(recievedText.getText()+ "Logget ud fra serveren\n");
	}

	public ChatUI() {
		System.out.println("ChatUI");
		final JFrame frame = new JFrame("KatChat");
		final JPanel panelMain = new JPanel();
		this.panelTop = new JPanel();
		final JPanel panelCN = new JPanel();
		final JPanel panelButtom = new JPanel();
		this.textToSend = new JTextField();
		this.textUserName = new JTextField();
		this.textPswd = new JTextField();
		this.labelUser = new JLabel("Brugernavn: ");
		this.labelName = new JLabel("");
		this.labelPswd = new JLabel("Kodeord: ");
		this.buttonSend = new JButton("Send");
		this.buttonLogout = new JButton("Log ud");
		this.buttonLogin = new JButton("Log ind");
		this.recievedText = new JTextArea();
		final JScrollPane scrollPane = new JScrollPane(this.recievedText);

		//final JList ls = new JList();
		panelMain.setLayout(new BorderLayout(5, 5));
		panelTop.setLayout(new GridLayout(1, 5));
		panelCN.setLayout(new BorderLayout(5, 5));
		panelButtom.setLayout(new BorderLayout(5, 5));

		panelTop.add(labelUser);
		panelTop.add(textUserName);
		panelTop.add(labelPswd);
		panelTop.add(textPswd);
		panelTop.add(buttonLogin);

		//panelCN.add(new JScrollPane(), BorderLayout.CENTER);
		//panelCN.add(ls, BorderLayout.EAST);
		//ls.setVisible(false);
		recievedText.setEditable(false);
		panelCN.add(scrollPane);

		panelButtom.add(textToSend, BorderLayout.CENTER);
		panelButtom.add(buttonSend, BorderLayout.EAST);
		this.textToSend.setVisible(false);
		this.buttonSend.setVisible(false);

		panelMain.add(panelTop, BorderLayout.NORTH);
		panelMain.add(panelCN, BorderLayout.CENTER);
		panelMain.add(panelButtom, BorderLayout.SOUTH);
		panelMain.setBorder(new EmptyBorder(10, 10, 10, 10));

		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (textUserName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"Indtast venligst Brugernavn!",
							"Inane warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					sessionID = doLogin(textUserName.getText(), textPswd.getText());
					if (sessionID == -1) {
						JOptionPane.showMessageDialog(frame,
								"Det indtastede brugernavn og kode er ikke korrekt",
								"Inane warning",
								JOptionPane.WARNING_MESSAGE);
					} else {
						labelName.setText(textUserName.getText());
						panelTop.remove(textUserName);
						panelTop.remove(textPswd);
						panelTop.remove(labelPswd);
						panelTop.remove(buttonLogin);
						panelTop.add(labelName);
						panelTop.add(buttonLogout);
						// ls.setVisible(true);
						textToSend.setVisible(true);
						buttonSend.setVisible(true);
						buttonLogout.setVisible(true);
						recievedText.setText(recievedText.getText()+ "Logget ind på serveren\n");
						MessageFetcher fetcher = new MessageFetcher(recievedText, sessionID);
						(new Thread(fetcher)).start();
					}
				}
			}

		});
		
		buttonLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});

		textToSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSendMsg(textToSend, recievedText, sessionID);
			}
		});

		buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSendMsg(textToSend, recievedText, sessionID);
			}
		});

		frame.setContentPane(panelMain);
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}

}
