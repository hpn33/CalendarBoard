package hpn332.cb.Utils;

public class TaskStructure {

    private String title, desc, tag;
    private int step, rank;

    public TaskStructure(String title, String desc, String tag, int step, int rank) {
        this.title = title;
        this.desc = desc;
        this.tag = tag;
        this.step = step;
        this.rank = rank;
    }

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

    public int getRank() {
        return rank;
    }
}
