package ru.job4j.tracker;

import java.util.function.Consumer;

/**
 * @author Roman Korolchuk (rom.kor@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class StartUI {

    private int[] ranges;

    private final Input input;

    private final ITracker tracker;

    private final Consumer<String> output;

    public StartUI(Input input, ITracker tracker, Consumer<String> output) {
        this.input = input;
        this.tracker = tracker;
        this.output = output;
    }

    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker, output);
        menu.fillActions();
        ranges = new int[menu.getActionsLentgh()];
        for (int i = 0; i < ranges.length; i++) {
            ranges[i] = i;
        }
        do {
            menu.show();
            menu.select(input.ask("Введите пункт меню : ", ranges));
        } while (menu.isOut());
    }

    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker(), System.out::println).init();
    }
}
