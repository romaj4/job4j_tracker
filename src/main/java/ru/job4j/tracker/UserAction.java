package ru.job4j.tracker;

/**
 * @author Roman Korolchuk (rom.kor@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public interface UserAction {

    int key();

    void execute(Input input, ITracker tracker);

    String info();
}
