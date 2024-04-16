package tripadministratorjava;

import java.util.Objects;

public class Company {

    public String name;
    public int tripOrganizationLimit;

    public Company(String name, int tripOrganizationLimit) {
        this.name = name;
        this.tripOrganizationLimit = tripOrganizationLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTripOrganizationLimit() {
        return tripOrganizationLimit;
    }

    public void setTripOrganizationLimit(int tripOrganizationLimit) {
        this.tripOrganizationLimit = tripOrganizationLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
