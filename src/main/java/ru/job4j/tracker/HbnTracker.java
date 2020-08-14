package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbnTracker implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void init() {
    }

    @Override
    public Item add(Item item) {
        Session session = this.sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        boolean rst = this.findById(id) != null;
        if (rst) {
            item.setId(id);
            Session session = this.sf.openSession();
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            session.close();
        }
        return rst;
    }

    @Override
    public boolean delete(Integer id) {
        boolean rst = false;
        Item item = this.findById(id);
        if (item != null) {
            Session session = this.sf.openSession();
            session.beginTransaction();
            session.delete(item);
            session.getTransaction().commit();
            session.close();
            rst = true;
        }
        return rst;
    }

    @Override
    public List<Item> findAll() {
        Session session = this.sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.tracker.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = this.sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.tracker.Item where name =: name").setParameter("name", key).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item findById(Integer id) {
        Session session = this.sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(this.registry);
    }
}
