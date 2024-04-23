import java.util.Objects;

public class NameAndTown {
    private String name;
    private String town;

    public NameAndTown(String name, String town) {
        this.name = name;
        this.town = town;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameAndTown that = (NameAndTown) o;
        return Objects.equals(name, that.name) && Objects.equals(town, that.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, town);
    }
}
