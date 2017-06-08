package emoji_server;

import java.util.ArrayList;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Random;

public class EmojiLogik extends UnicastRemoteObject implements IEmoji {

	private static final ArrayList<Emoji> EMOJI_LIST = new ArrayList<>();

	public EmojiLogik() throws java.rmi.RemoteException {
		EMOJI_LIST.add(new Emoji(">'.'<", "cat1"));
		EMOJI_LIST.add(new Emoji("=^,^=", "cat2"));
		EMOJI_LIST.add(new Emoji(">O_O<", "cat3"));
	}

	@Override
	public void addCat(String emojipic, String name) throws RemoteException {
		for (int i = 0; i < EMOJI_LIST.size(); i++) {
			if (EMOJI_LIST.get(i).getName().equalsIgnoreCase(name)) {
				return;
			}
		}
		Emoji temp = new Emoji(emojipic, name);
		EMOJI_LIST.add(temp);
	}

	@Override
	public String getCat() throws java.rmi.RemoteException {
		// tilføj random generator her
		Random rand = new Random();
		int i = rand.nextInt(EMOJI_LIST.size());
		return EMOJI_LIST.get(i).getEmoji();
	}

	@Override
	public String getCat(String name) throws RemoteException {
		for (int i = 0; i < EMOJI_LIST.size(); i++) {
			if (EMOJI_LIST.get(i).getName().equals(name)) {
				return EMOJI_LIST.get(i).getEmoji();
			}

		}
		return EMOJI_LIST.get(0).getEmoji();
	}

	@Override
	public String getCat(int index) throws RemoteException {
		return EMOJI_LIST.get(index).getEmoji();
	}

}
