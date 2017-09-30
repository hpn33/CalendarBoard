package hpn332.cb.model.stucture;

public class CheckTagStructure {

	private String  title;
	private int     color;
	private boolean bool;

	public CheckTagStructure(String title, int color, boolean bool) {
		this.title = title;
		this.color = color;
		this.bool = bool;
	}

	public String getTitle() {
		return title;
	}

	public int getColor() {
		return color;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	public boolean isBool() {
		return bool;
	}
}
