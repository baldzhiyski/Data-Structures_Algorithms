import java.util.Iterator;


public class RoyaleArena implements IArena {

    @Override
    public void add(Battlecard card) {
        
    }

    @Override
    public boolean contains(Battlecard card) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void changeCardType(int id, CardType type) {

    }

    @Override
    public Battlecard getById(int id) {
        return null;
    }

    @Override
    public void removeById(int id) {

    }

    @Override
    public Iterable<Battlecard> getByCardType(CardType type) {
        return null;
    }

    @Override
    public Iterable<Battlecard> getByTypeAndDamageRangeOrderedByDamageThenById(CardType type, int lo, int hi) {
        return null;
    }

    @Override
    public Iterable<Battlecard> getByCardTypeAndMaximumDamage(CardType type, double damage) {
        return null;
    }

    @Override
    public Iterable<Battlecard> getByNameOrderedBySwagDescending(String name) {
        return null;
    }

    @Override
    public Iterable<Battlecard> getByNameAndSwagRange(String name, double lo, double hi) {
        return null;
    }

    @Override
    public Iterable<Battlecard> getAllByNameAndSwag() {
        return null;
    }

    @Override
    public Iterable<Battlecard> findFirstLeastSwag(int n) {
        return null;
    }

    @Override
    public Iterable<Battlecard> getAllInSwagRange(double lo, double hi) {
        return null;
    }

    @Override
    public Iterator<Battlecard> iterator() {
        return null;
    }
}
