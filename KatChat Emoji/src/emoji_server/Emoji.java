package emoji_server;

public class Emoji {

	private String name;
	private String emoji;

	public Emoji(String cat, String name) {
		this.name = name;
		this.emoji = cat;
	}

	public String getName() {
		return name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	public String getEmoji() {
		return emoji;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

}
