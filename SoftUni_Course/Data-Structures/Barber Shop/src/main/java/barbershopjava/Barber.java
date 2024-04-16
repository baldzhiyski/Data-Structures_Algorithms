package barbershopjava;

import java.util.Objects;

public class Barber {

    public String name;
    public int haircutPrice;
    public int stars;

    public Barber(String name, int haircutPrice, int stars) {
        this.name = name;
        this.haircutPrice = haircutPrice;
        this.stars = stars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barber barber = (Barber) o;
        return Objects.equals(name, barber.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHaircutPrice() {
        return haircutPrice;
    }

    public void setHaircutPrice(int haircutPrice) {
        this.haircutPrice = haircutPrice;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
