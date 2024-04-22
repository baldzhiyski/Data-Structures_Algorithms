package royal_arena;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoyalArenaTest {

    private RoyalArena arena;

    @BeforeEach
    void setUp() {
        arena = new RoyalArena();
    }

    @Test
    void add() {
        Battlecard card1 = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        Battlecard card2 = new Battlecard(2, CardType.RANGED, "Card2", 20.0, 30.0);

        arena.add(card1);
        assertTrue(arena.contains(card1));
        assertEquals(1, arena.count());

        // Adding same card should not increase count
        arena.add(card1);
        assertEquals(1, arena.count());

        // Adding different card should increase count
        arena.add(card2);
        assertEquals(2, arena.count());
    }

    @Test
    void contains() {
        Battlecard card = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        assertFalse(arena.contains(card));

        arena.add(card);
        assertTrue(arena.contains(card));
    }

    @Test
    void changeCardType() {
        Battlecard card = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        arena.add(card);

        arena.changeCardType(1, CardType.SPELL);
        assertEquals(CardType.SPELL, arena.getById(1).getType());

        // Test for changing type of non-existent card
        assertThrows(IllegalStateException.class, () -> arena.changeCardType(2, CardType.RANGED));
    }

    @Test
    void getById() {
        Battlecard card = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        arena.add(card);

        assertEquals(card, arena.getById(1));

        // Test for getting non-existent card
        assertThrows(UnsupportedOperationException.class, () -> arena.getById(2));
    }

    @Test
    void removeById() {
        Battlecard card = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        arena.add(card);

        arena.removeById(1);
        assertEquals(0, arena.count());
        assertFalse(arena.contains(card));

        // Test for removing non-existent card
        assertThrows(UnsupportedOperationException.class, () -> arena.removeById(2));
    }

    @Test
    void getByCardType() {
        Battlecard card1 = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        Battlecard card2 = new Battlecard(2, CardType.RANGED, "Card2", 20.0, 30.0);
        Battlecard card3 = new Battlecard(3, CardType.SPELL, "Card3", 15.0, 25.0);
        arena.add(card1);
        arena.add(card2);
        arena.add(card3);

        List<Battlecard> meleeCards = List.of(card1);
        List<Battlecard> rangedCards = List.of(card2);
        List<Battlecard> spellCards = List.of(card3);

        assertEquals(meleeCards, arena.getByCardType(CardType.MELEE));
        assertEquals(rangedCards, arena.getByCardType(CardType.RANGED));
        assertEquals(spellCards, arena.getByCardType(CardType.SPELL));
    }

    @Test
    void getByTypeAndDamageRangeOrderedByDamageThenById() {
        Battlecard card1 = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        Battlecard card2 = new Battlecard(2, CardType.MELEE, "Card2", 40.0, 30.0);
        Battlecard card3 = new Battlecard(3, CardType.MELEE, "Card3", 15.0, 25.0);
        arena.add(card1);
        arena.add(card2);
        arena.add(card3);

        List<Battlecard> expected = Arrays.asList(card1, card3);
        List<Battlecard> cards = (List<Battlecard>) arena.getByTypeAndDamageRangeOrderedByDamageThenById(CardType.MELEE, 5, 20);
        assertEquals(expected.get(0), cards.get(0));
        assertEquals(2, cards.size());
    }

    @Test
    void getByCardTypeAndMaximumDamage() {
        Battlecard card1 = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        Battlecard card2 = new Battlecard(2, CardType.MELEE, "Card2", 20.0, 30.0);
        Battlecard card3 = new Battlecard(3, CardType.MELEE, "Card3", 15.0, 25.0);
        arena.add(card1);
        arena.add(card2);
        arena.add(card3);

        List<Battlecard> expected = List.of(card1);
        assertEquals(expected, arena.getByCardTypeAndMaximumDamage(CardType.MELEE, 12.0));
    }

    @Test
    void getByNameOrderedBySwagDescending() {
        Battlecard card1 = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        Battlecard card2 = new Battlecard(2, CardType.MELEE, "Card1", 20.0, 30.0);
        arena.add(card1);
        arena.add(card2);

        List<Battlecard> expected = Arrays.asList(card2, card1);
        assertEquals(expected, arena.getByNameOrderedBySwagDescending("Card1"));
    }

    @Test
    void getByNameAndSwagRange() {
        Battlecard card1 = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        Battlecard card2 = new Battlecard(2, CardType.MELEE, "Card1", 20.0, 30.0);
        arena.add(card1);
        arena.add(card2);

        List<Battlecard> expected = List.of(card1);
        assertEquals(expected, arena.getByNameAndSwagRange("Card1", 10.0, 25.0));
    }

    @Test
    void getAllByNameAndSwag() {
        Battlecard card1 = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        Battlecard card2 = new Battlecard(2, CardType.MELEE, "Card2", 20.0, 30.0);
        arena.add(card1);
        arena.add(card2);

        List<Battlecard> expected = Arrays.asList(card2, card1);
        assertEquals(expected, arena.getAllByNameAndSwag());
    }

    @Test
    void findFirstLeastSwag() {
        Battlecard card1 = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        Battlecard card2 = new Battlecard(2, CardType.MELEE, "Card2", 20.0, 10.0);
        arena.add(card1);
        arena.add(card2);

        List<Battlecard> expected = List.of(card2);
        assertEquals(expected, arena.findFirstLeastSwag(1));
    }

    @Test
    void getAllInSwagRange() {
        Battlecard card1 = new Battlecard(1, CardType.MELEE, "Card1", 10.0, 20.0);
        Battlecard card2 = new Battlecard(2, CardType.MELEE, "Card2", 20.0, 30.0);
        arena.add(card1);
        arena.add(card2);

        List<Battlecard> expected = Arrays.asList(card1, card2);
        assertEquals(expected, arena.getAllInSwagRange(10.0, 30.0));
    }
}