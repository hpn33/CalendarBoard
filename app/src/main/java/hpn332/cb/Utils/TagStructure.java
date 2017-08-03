package hpn332.cb.Utils;

public class TagStructure {

	private String title, desc;
	private int color;

	public TagStructure(String title, String desc, int color) {
		this.title = title;
		this.desc = desc;
		this.color = color;
	}

	public String getTitle() {return title;}

	public String getDesc()  {return desc;}

	public int getColor()    {return color;}
}
