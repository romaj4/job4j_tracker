package ru.job4j.tracker;

public class Item {

    private Integer id;

    public String name;

    public String description;

    public long create;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.create = System.currentTimeMillis();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public long getCreate() {
        return this.create;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreate(long create) {
        this.create = create;
    }
}
