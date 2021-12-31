public class WordProperty {

    private int positive;
    private int negative;

    public WordProperty() {
        positive = 0;
        negative = 0;
    }

    public void addPositive() {
        positive++;
    }

    public void addNegative() {
        negative++;
    }

    public int getNegativeAmount() {
        return negative;
    }

    public int getPositiveAmount() {
        return positive;
    }
}
