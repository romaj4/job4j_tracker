package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Roman Korolchuk (rom.kor@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class TrackerSingleStaticFieldTest {

    @Test
    public void whenCreateThreeTrackerThenReturnOneInstance() {
        TrackerSingleStaticField tracker1 = TrackerSingleStaticField.getInstance();
        TrackerSingleStaticField tracker2 = TrackerSingleStaticField.getInstance();
        TrackerSingleStaticField tracker3 = TrackerSingleStaticField.getInstance();
        Item item = new Item("test1", "testDescription1");
        tracker1.add(item);
        assertThat(tracker2.findAll().get(0), is(tracker3.findByName("test1").get(0)));
    }
}