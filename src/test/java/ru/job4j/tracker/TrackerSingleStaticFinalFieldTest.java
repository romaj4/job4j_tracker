package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Roman Korolchuk (rom.kor@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class TrackerSingleStaticFinalFieldTest {

    @Test
    public void whenCreateThreeTrackerThenReturnOneInstance() {
        TrackerSingleStaticFinalField tracker1 = TrackerSingleStaticFinalField.getInstance();
        TrackerSingleStaticFinalField tracker2 = TrackerSingleStaticFinalField.getInstance();
        TrackerSingleStaticFinalField tracker3 = TrackerSingleStaticFinalField.getInstance();
        Item item = new Item("test1", "testDescription1");
        tracker1.add(item);
        assertThat(tracker2.findAll().get(0), is(tracker3.findByName("test1").get(0)));
    }
}