package hpn332.cb.utils.model;

public class TaskStructure {

	private String title, desc, tag;
	private int id, step, rank;

	public TaskStructure(int id, String title, String desc, String tag, int step, int rank) {
		this.id = id;
		this.title = title;
		this.desc = desc;
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

	public String getTag() {
		return tag;
	}

	public int getStep() {
		return step;
	}

	public int getRank() {return rank;}
}
