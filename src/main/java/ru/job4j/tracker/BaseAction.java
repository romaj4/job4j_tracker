package ru.job4j.tracker;

public abstract class BaseAction implements UserAction {

    private final int key;

    private final String name;

    public abstract void execute(Input input, ITracker tracker);

    protected BaseAction(final int key, final String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int key() {
        return this.key;
    }

    @Override
    public String info() {
        return String.format("%d. %s", this.key, this.name);
    }
}
