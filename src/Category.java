import java.util.HashMap;
import java.util.Map;

public enum Category {
    CATEGORY_NEGATIVE("0"),
    CATEGORY_POSITIVE("1");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    private static final Map<String, Category> MAP = new HashMap<>();

    static {
        for (Category category : Category.values()) {
            MAP.put(category.getValue(), category);
        }
    }

    public static Category fromValue(final String value) {
        return MAP.get(value);
    }

}
