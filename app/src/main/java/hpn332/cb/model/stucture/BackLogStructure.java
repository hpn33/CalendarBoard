package hpn332.cb.model.stucture;

public class BackLogStructure {

	private int id, project, color;
	private String title, desc;

	public BackLogStructure(int id, String title, String desc, int project, int color) {
		this.id = id;
		this.project = project;
		this.color = color;
		this.title = title;
		this.desc = desc;
	}

	public int getId() {return id;}

	public String getTitle() {return title;}

	public String getDesc() {return desc;}

	public int getProject() {return project;}

	public int getColor() {return color;}


}
