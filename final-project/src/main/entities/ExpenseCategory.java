package entities;

public class ExpenseCategory {
    private static Integer counter = 0;
    private Integer id;
    private String name;
    private String description;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String name) {
        this.id = ++counter;
        this.name = name.toLowerCase();
    }

    public ExpenseCategory(String name, String description) {
        this(name);
        this.description = description.toLowerCase();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toLowerCase();
    }

    @Override
    public String toString() {
        return "{ " +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            " }";
    }
}