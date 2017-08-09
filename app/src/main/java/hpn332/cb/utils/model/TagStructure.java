package hpn332.cb.utils.model;

public class TagStructure {

	private String title, desc;
	private int id, color;

	public TagStructure(int id, String title, String desc, int color) {
		this.title = title;
		this.desc = desc;
		this.id = id;
		this.color = color;
	}

	public int getId()       {return id;}

	public String getTitle() {return title;}

	public String getDesc()  {return desc;}

	public int getColor()    {return color;}
}
