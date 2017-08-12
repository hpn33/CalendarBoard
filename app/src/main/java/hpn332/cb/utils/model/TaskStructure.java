package hpn332.cb.utils.model;

public class TaskStructure {

	private String title, desc, tag;
	private int id, step, rank, project;

	public TaskStructure(
			int id, String title, String desc, int project, String tag, int step, int
			rank) {
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.project = project;
		this.tag = tag;
		this.step = step;
		this.rank = rank;
	}

	public int getId() {return id;}

	public String getTitle() {
		return title;
	}

	public String getDesc() {
		return desc;
	}

	public int getProject() {return project;}

	public String getTag() {
		return tag;
	}

	public int getStep() {
		return step;
	}

	public int getRank() {return rank;}
}
