package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Roman Korolchuk (rom.kor@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class MemTracker implements Store {
    /**
     * Массив для хранение заявок.
     */
    private final List<Item> items = new ArrayList<>();

    @Override
    public void init() {

    }

    /**
     * Метод реализаущий добавление заявки в хранилище
     *
     * @param item новая заявка.
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     *
     * @return Уникальный ключ.
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + new Random().nextInt(10000));
    }

    /**
     * Метод редактирует заявку.
     *
     * @param id   уникальный ключ.
     * @param item новая заявка.
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        int position = this.findPositionById(id);
        if (position != -1) {
            item.setId(this.items.get(position).getId());
            this.items.set(position, item);
            result = true;
        }
        return result;
    }

    /**
     * Метод удаляет заявку.
     *
     * @param id уникальный ключ.
     */
    public boolean delete(String id) {
        boolean result = false;
        int position = this.findPositionById(id);
        if (position != -1) {
            this.items.remove(position);
            result = true;
        }
        return result;
    }

    /**
     * Получение всех заявок.
     *
     * @return заявки.
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * Получение заявок по имени.
     *
     * @param name имя.
     * @return заявки.
     */
    public List<Item> findByName(String name) {
        return this.items.stream().filter(item -> item.getName().equals(name)).collect(Collectors.toList());
    }

    /**
     * Получение заявки по id.
     *
     * @param id уникальный ключ.
     * @return заявка.
     */
    public Item findById(String id) {
        return this.items.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }

    /**
     * Получение позиции заявки в items по id.
     *
     * @param id уникальный ключ.
     * @return позиция заявки.
     */
    public int findPositionById(String id) {
        int result = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) != null && items.get(i).getId().equals(id)) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}


