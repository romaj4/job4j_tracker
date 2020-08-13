package ru.job4j.tracker;

import java.util.List;

/**
 * @author Roman Korolchuk (rom.kor@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public interface Store extends AutoCloseable {
    void init();

    Item add(Item item);

    boolean replace(Integer id, Item item);

    boolean delete(Integer id);

    List<Item> findAll();

    List<Item> findByName(String key);

    Item findById(Integer id);
}
