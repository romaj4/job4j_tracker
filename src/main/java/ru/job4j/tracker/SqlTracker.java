package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private Connection connection;

    public SqlTracker() {
        this.init();
        this.createTable();
    }

    /**
     * Подключение к базе данных.
     *
     * @return результат подключения.
     */
    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("jdbc.driver"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Создаёт таблицу items.
     */
    public void createTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(new StringBuilder("create table if not exists items")
                    .append("(id serial primary key,")
                    .append("name varchar(100),")
                    .append("description varchar(500),")
                    .append("created bigint);").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляет таблицу items.
     */
    public void dropTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("drop table if exists items");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Закрывает соединения.
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    /**
     * Добавление заявки в таблицу.
     *
     * @param item заявка.
     * @return заявка.
     */
    @Override
    public Item add(Item item) {
        try (PreparedStatement prst = this.connection.prepareStatement(
                "insert into items(name, description, created) values (?, ?, ?)")) {
            prst.setString(1, item.getName());
            prst.setString(2, item.getDescription());
            prst.setLong(3, item.getCreate());
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * Редактирует заявку.
     *
     * @param id   уникальный ключ.
     * @param item новая заявка.
     */
    @Override
    public boolean replace(String id, Item item) {
        int rst = 0;
        try (PreparedStatement prst = this.connection.prepareStatement(
                "update items set name = ?, description = ?, created = ? where id = ?;")) {
            prst.setString(1, item.getName());
            prst.setString(2, item.getDescription());
            prst.setLong(3, item.getCreate());
            prst.setInt(4, Integer.parseInt(id));
            rst = prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst == 1;
    }

    /**
     * Удалить заявку
     *
     * @param id id.
     * @return результат удаления.
     */
    @Override
    public boolean delete(String id) {
        int rst = 0;
        try (PreparedStatement prst = this.connection.prepareStatement(
                "delete from items where id = ?")) {
            prst.setInt(1, Integer.parseInt(id));
            rst = prst.executeUpdate();
            System.out.println(rst);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst == 1;
    }

    /**
     * Получение всех заявок.
     *
     * @return заявки.
     */
    @Override
    public List<Item> findAll() {
        List<Item> itemsList = new ArrayList<>();
        try (Statement st = this.connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("select * from items");
            while (resultSet.next()) {
                Item item = new Item(resultSet.getString("name"), resultSet.getString("description"));
                item.setId(String.valueOf(resultSet.getInt("id")));
                item.setCreate(resultSet.getLong("created"));
                itemsList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsList;
    }

    /**
     * Получение заявок по имени.
     *
     * @param name имя.
     * @return заявки.
     */
    @Override
    public List<Item> findByName(String name) {
        List<Item> itemsList = new ArrayList<>();
        try (PreparedStatement prst = this.connection.prepareStatement(
                "SELECT * FROM items WHERE name = ?")) {
            prst.setString(1, name);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                Item item = new Item(resultSet.getString("name"), resultSet.getString("description"));
                item.setId(String.valueOf(resultSet.getInt("id")));
                item.setCreate(resultSet.getLong("created"));
                itemsList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsList;
    }

    /**
     * Получение заявки по id.
     *
     * @param id уникальный ключ.
     * @return заявка.
     */
    @Override
    public Item findById(String id) {
        Item item = null;
        try (PreparedStatement prst = this.connection.prepareStatement(
                "SELECT * FROM items WHERE id = ?")) {
            prst.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = prst.executeQuery();
            if (resultSet.next()) {
                item = new Item(resultSet.getString("name"), resultSet.getString("description"));
                item.setId(String.valueOf(resultSet.getInt("id")));
                item.setCreate(resultSet.getLong("created"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
