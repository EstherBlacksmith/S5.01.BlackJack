package cat.itacademyS5_01.game.service;

import java.util.HashSet;
import java.util.Set;

public class GameIdGenerator {
    private static final Set<Long> usedIds = new HashSet<>();
    private static long nextId = 1L;

    public static synchronized long generateId() {
        while (usedIds.contains(nextId)) {
            nextId++;
        }
        usedIds.add(nextId);
        return nextId;
    }
}
