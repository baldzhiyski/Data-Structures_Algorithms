package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {
    private Map<String, List<Trip>> companyByNameAndTrips;
    private Set<Company> companies;
    private Set<Trip> trips;

    public  TripAdministratorImpl(){
        this.companyByNameAndTrips = new HashMap<>();
        this.companies = new LinkedHashSet<>();
        this.trips = new LinkedHashSet<>();
    }

    @Override
    public void addCompany(Company c) {
        if(companies.contains(c)){
            throw new IllegalArgumentException();
        }
        this.companies.add(c);
        this.companyByNameAndTrips.put(c.getName(),new ArrayList<>());
    }

    @Override
    public void addTrip(Company c, Trip t) {
        if(!companies.contains(c)){
            throw new IllegalArgumentException();
        }
        if(companyByNameAndTrips.get(c.getName()).size() < c.getTripOrganizationLimit() && !companyByNameAndTrips.get(c.getName()).contains(t)) {
            this.companyByNameAndTrips.get(c.getName()).add(t);
            this.trips.add(t);
        }else{
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean exist(Company c) {
        return this.companies.contains(c);
    }

    @Override
    public boolean exist(Trip t) {
        return this.trips.contains(t);
    }

    @Override
    public void removeCompany(Company c) {
        if(c==null) {
            throw new IllegalArgumentException();
        }
        this.companies.remove(c);
        this.companyByNameAndTrips.remove(c.getName());
    }

    @Override
    public Collection<Company> getCompanies() {
        return this.companies;
    }

    @Override
    public Collection<Trip> getTrips() {
        return this.trips;
    }

    @Override
    public void executeTrip(Company c, Trip t) {
        if(t==null || c==null || !this.companyByNameAndTrips.get(c.getName()).contains(t)){
            throw new IllegalArgumentException();
        }
        this.companyByNameAndTrips.get(c.getName()).remove(t);
        trips.remove(t);
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
        Set<String> companyNamesWithNTrips = this.companyByNameAndTrips.entrySet()
                .stream()
                .filter(companyEntry -> companyEntry.getValue().size() > n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        // Now, filter companies from the set based on the names collected above
        return this.companies.stream()
                .filter(company -> companyNamesWithNTrips.contains(company.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return getTrips().stream()
                .filter(trip -> trip.transportation.equals(t))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return getTrips().stream()
                .filter(trip -> trip.price>=lo && trip.price<=hi)
                .collect(Collectors.toList());
    }
}
