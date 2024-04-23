import java.util.*;
import java.util.stream.Collectors;

public class PersonCollectionImpl implements PersonCollection {
    private final Map<String, Person> peopleByEmail;

    // indices
    private final Map<String, TreeMap<String, Person>> peopleByEmailDomain;

    private final Map<NameAndTown, Set<Person>> peopleNameAndTownSortedByEmail;
    private final TreeMap<Integer, Set<Person>> peopleByAgeAndTown;



    public PersonCollectionImpl() {
        this.peopleByEmail = new HashMap<>();
        this.peopleByEmailDomain = new HashMap<>();
        this.peopleNameAndTownSortedByEmail = new HashMap<>();
        this.peopleByAgeAndTown = new TreeMap<>();
    }


    @Override
    public boolean add(String email, String name, int age, String town) {
        String domain = getDomain(email);
        Person personToAdd = new Person(email, name, age, town);
        NameAndTown nameAndTown = new NameAndTown(name, town);

        peopleNameAndTownSortedByEmail.putIfAbsent(nameAndTown,new TreeSet<>());
        peopleByEmailDomain.putIfAbsent(domain, new TreeMap<>());
        peopleByAgeAndTown.putIfAbsent(age,new TreeSet<>());

        Person person = peopleByEmail.get(email);
        if (person == null) {
            peopleByEmail.put(email, personToAdd);
            peopleByEmailDomain.get(domain).put(email, personToAdd);
            peopleNameAndTownSortedByEmail.get(nameAndTown).add(personToAdd);
            peopleByAgeAndTown.get(age).add(personToAdd);
            return true;
        }
        return false;
    }


    @Override
    public int getCount() {
        return peopleByEmail.size();
    }

    @Override
    public boolean delete(String email) {
        Person person = this.peopleByEmail.get(email);
        TreeMap<String, Person> people = peopleByEmailDomain.get(getDomain(email));

        if (person != null) {
            String name = person.getName();
            String town = person.getTown();
            NameAndTown nameAndTown = new NameAndTown(name, town);

            this.peopleNameAndTownSortedByEmail.get(nameAndTown).remove(person);
            this.peopleByEmail.remove(email);

            int age = person.getAge();
            this.peopleByAgeAndTown.get(age).remove(person);

            people.remove(email);
            return true;
        }
        return false;
    }

    @Override
    public Person find(String email) {
        return this.peopleByEmail.get(email);
    }

    @Override
    public Iterable<Person> findAll(String emailDomain) {
        if (this.peopleByEmailDomain.get(emailDomain) == null) return new ArrayList<>();
        return this.peopleByEmailDomain.get(emailDomain).values();
    }

    @Override
    public Iterable<Person> findAll(String name, String town) {
        NameAndTown key = new NameAndTown(name, town);
        if(this.peopleNameAndTownSortedByEmail.get(key) == null) return  new ArrayList<>();

        return  peopleNameAndTownSortedByEmail.get(key);
    }

    @Override
    public Iterable<Person> findAll(int startAge, int endAge) {
        return peopleByEmail.values()
                .stream().filter(person -> person.getAge() >= startAge && person.getAge() <=endAge)
                .sorted(Comparator.comparing(Person::getAge).thenComparing(Person::compareTo))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Person> findAll(int startAge, int endAge, String town) {
        NavigableMap<Integer, Set<Person>> subMap = peopleByAgeAndTown.subMap(startAge, true, endAge, true);
        if (subMap == null) {
            return new ArrayList<>();
        }

        List<Person> result = new ArrayList<>();
        for (Set<Person> people : subMap.values()) {
            for (Person person : people) {
                if (person.getTown().equals(town)) {
                    result.add(person);
                }
            }
        }
        return result;
    }
    private static String getDomain(String email) {
        int startIndexDom = email.indexOf("@") + 1;

        return email.substring(startIndexDom);
    }
}
