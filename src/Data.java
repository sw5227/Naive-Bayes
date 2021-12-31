
public class Data {
    private String comment;
    private Category category;

    public Data(String comment, Category category) {
        this.comment = comment;
        this.category = category;
    }

    public String getComment() {
        return this.comment;
    }

    public Category getCategory() {
        return this.category;
    }
}
