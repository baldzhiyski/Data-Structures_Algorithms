package royal_arena;

import java.util.*;
import java.util.stream.Collectors;


public class RoyalArena implements IArena {
    private Set<Battlecard> battleCards ;
    private Map<Integer,Battlecard> cardsById;

    public RoyalArena(){
        this.cardsById = new HashMap<>();
        this.battleCards = new HashSet<>();
    }
    @Override
    public void add(Battlecard card) {
        if(!battleCards.contains(card)){
            this.cardsById.put(card.getId(),card);
            this.battleCards.add(card);
        }
    }

    @Override
    public boolean contains(Battlecard card) {
        return this.cardsById.containsKey(card.getId());
    }

    @Override
    public int count() {
        return this.cardsById.size();
    }

    @Override
    public void changeCardType(int id, CardType type) {
        if(this.cardsById.containsKey(id)){
            this.cardsById.get(id).setType(type);
        }else{
            throw new IllegalStateException();
        }
    }

    @Override
    public Battlecard getById(int id) {
        if(cardsById.containsKey(id)){
            return this.cardsById.get(id);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeById(int id) {
        Battlecard battlecard = this.cardsById.get(id);
        if(battlecard!=null) {
            this.cardsById.remove(id);
            this.battleCards.remove(battlecard);
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Battlecard> getByCardType(CardType type) {
        return this.battleCards
                .stream()
                .filter(battlecard -> battlecard.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getByTypeAndDamageRangeOrderedByDamageThenById(CardType type, int lo, int hi) {
        return this.battleCards
                .stream()
                .filter(battlecard -> battlecard.getType().equals(type) && (battlecard.getDamage() >=lo && battlecard.getDamage()<=hi))
                .sorted(Comparator.comparing(Battlecard::getDamage).thenComparing(Battlecard::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getByCardTypeAndMaximumDamage(CardType type, double damage) {
        return battleCards
                .stream()
                .filter(battlecard -> battlecard.getType().equals(type) && battlecard.getDamage()<=damage)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getByNameOrderedBySwagDescending(String name) {
        return battleCards
                .stream()
                .filter(battlecard -> battlecard.getName().equals(name))
                .sorted(Comparator.comparing(Battlecard::getSwag).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getByNameAndSwagRange(String name, double lo, double hi) {
        return battleCards
                .stream()
                .filter(battlecard -> battlecard.getName().equals(name) &&
                        battlecard.getSwag()>=lo && battlecard.getSwag()<=hi)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getAllByNameAndSwag() {
        return battleCards
                .stream()
                .sorted(Comparator.comparing(Battlecard::getSwag).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> findFirstLeastSwag(int n) {
        if(n>battleCards.size()) throw new UnsupportedOperationException();
        return battleCards
                .stream()
                .sorted(Comparator.comparing(Battlecard::getSwag).thenComparing(Battlecard::getId))
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Battlecard> getAllInSwagRange(double lo, double hi) {
        return battleCards
                .stream()
                .filter(battlecard -> battlecard.getSwag()>=lo && battlecard.getSwag()<=hi)
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Battlecard> iterator() {
        return new BattleCardsIterator();
    }

    private class BattleCardsIterator implements Iterator<Battlecard> {
        private List<Battlecard> cards;
        private  int index;

        public BattleCardsIterator(){
            cards = new ArrayList<>();
            cards.addAll(battleCards);
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return index < cards.size();
        }

        @Override
        public Battlecard next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the iteration.");
            }
            return cards.get(index++);
        }
    }
}
