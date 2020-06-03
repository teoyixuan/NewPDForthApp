package sg.edu.rp.c346.newpdforthapp;

public class Data {
    private int id;
    private int iconId;
    private float amount;
    private String date;
    private String remark;

    public Data(int id, int iconId, float amount, String date, String remark) {
        this.id = id;
        this.iconId = iconId;
        this.amount = amount;
        this.date = date;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public int getIconId() {
        return iconId;
    }

    public float getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getRemark() {
        return remark;
    }
}
