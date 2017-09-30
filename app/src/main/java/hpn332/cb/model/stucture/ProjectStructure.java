package hpn332.cb.model.stucture;

public class ProjectStructure {

	private int    id;
	private String title, desc;

	public ProjectStructure(int id, String title, String desc) {
		this.id = id;
		this.title = title;
		this.desc = desc;
	}

	public int getId() {return id;}

	public String getTitle() {return title;}

	public String getDesc() {return desc;}
}
