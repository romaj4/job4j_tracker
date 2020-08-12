package ru.job4j.tracker;

import java.util.List;

/**
 * @author Roman Korolchuk (rom.kor@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public enum TrackerSingleEnum {

    INSTANCE;

    MemTracker tracker = new MemTracker();

    public Item add(Item item) {
        return this.tracker.add(item);
    }

    public boolean replace(String id, Item item) {
        return this.tracker.replace(id, item);
    }

    public boolean delete(String id) {
        return this.tracker.delete(id);
    }

    public List<Item> findAll() {
        return this.tracker.findAll();
    }

    public List<Item> findByName(String name) {
        return this.tracker.findByName(name);
    }

    public Item findById(String id) {
        return this.tracker.findById(id);
    }

    public int findPositionById(String id) {
        return this.tracker.findPositionById(id);
    }
}
