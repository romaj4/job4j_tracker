package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Roman Korolchuk (rom.kor@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class TrackerSingleStaticFinalClassTest {

    @Test
    public void whenCreateThreeTrackerThenReturnOneInstance() {
        TrackerSingleStaticFinalClass tracker1 = TrackerSingleStaticFinalClass.getInstance();
        TrackerSingleStaticFinalClass tracker2 = TrackerSingleStaticFinalClass.getInstance();
        TrackerSingleStaticFinalClass tracker3 = TrackerSingleStaticFinalClass.getInstance();
        Item item = new Item("test1", "testDescription1");
        tracker1.add(item);
        assertThat(tracker2.findAll().get(0), is(tracker3.findByName("test1").get(0)));
    }
}