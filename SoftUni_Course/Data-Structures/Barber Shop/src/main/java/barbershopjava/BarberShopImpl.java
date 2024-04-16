package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;

public class BarberShopImpl implements BarberShop {
    private Map<String,Barber> barbersByNames;

    private Map<String , List<Client>> barbersByNameAndClients;
    private Map<String,Client> clientsByNames;


    public BarberShopImpl(){
        this.barbersByNames = new HashMap<>();
        this.clientsByNames = new HashMap<>();
        this.barbersByNames = new HashMap<>();
    }

    @Override
    public void addBarber(Barber b) {
        if(barbersByNames.containsKey(b.getName())){
            throw new IllegalArgumentException();
        }
        barbersByNames.put(b.getName(),b);
        barbersByNameAndClients.put(b.getName(),new ArrayList<>());
    }

    @Override
    public void addClient(Client c) {
        if(clientsByNames.containsKey(c.getName())){
            throw new IllegalArgumentException();
        }
        clientsByNames.put(c.getName(),c);
    }

    @Override
    public boolean exist(Barber b) {
        return this.barbersByNames.containsKey(b.getName());
    }

    @Override
    public boolean exist(Client c) {
        return clientsByNames.containsKey(c.getName());
    }

    @Override
    public Collection<Barber> getBarbers() {
        return this.barbersByNames.values();
    }

    @Override
    public Collection<Client> getClients() {return this.clientsByNames.values();
    }

    @Override
    public void assignClient(Barber b, Client c) {
        List<Client> barber = this.barbersByNameAndClients.get(b.getName());
        Client client = this.clientsByNames.get(c.getName());
        if(client !=null){
           barber.add(client);
           client.setBarber(b);
        }

    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        Barber barber = barbersByNames.get(b.getName());
       if(barber==null){
           throw new IllegalArgumentException();
       }
        List<Client> clients = this.barbersByNameAndClients.get(b.getName());
        for (Client client : clients) {
            client.setBarber(null);
        }
        clients.clear();

    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return getClients().stream().filter(client -> client.getBarber()==null)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
        return this.barbersByNameAndClients.entrySet()
                .stream()
                .sorted((second,first) -> Integer.compare(first.getValue().size(),second.getValue().size()))
                .map(barber -> this.barbersByNames.get(barber.getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        return getBarbers()
                .stream()
                .sorted(Comparator.comparing(Barber::getStars).reversed().thenComparing(Barber::getHaircutPrice))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {
        return getClients()
                .stream()
                .filter(client -> client.getBarber() != null)
                .sorted(Comparator.comparing(Client::getAge).reversed()
                        .thenComparing((second, first) -> Integer.compare(first.getBarber().getStars(), second.getBarber().getStars())))
                .collect(Collectors.toList());

    }
}
