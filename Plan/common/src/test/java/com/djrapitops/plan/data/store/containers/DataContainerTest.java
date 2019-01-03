/*
 *  This file is part of Player Analytics (Plan).
 *
 *  Plan is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License v3 as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Plan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Plan. If not, see <https://www.gnu.org/licenses/>.
 */
package com.djrapitops.plan.data.store.containers;

import com.djrapitops.plan.data.store.Key;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for {@link DataContainer} programming errors.
 *
 * @author Rsl1122
 */
public class DataContainerTest {

    private static final Key<String> TEST_KEY = new Key<>(String.class, "TEST_KEY");
    private static final Key<String> TEST_KEY_COPY = new Key<>(String.class, "TEST_KEY");

    @Test
    public void safeUnsafeKeySupplierSameObject() {
        DataContainer container = new DataContainer();
        container.putSupplier(TEST_KEY, () -> "Success");

        assertEquals("Success", container.getUnsafe(TEST_KEY));
    }

    @Test
    public void safeUnsafeKeySupplierDifferentObject() {
        DataContainer container = new DataContainer();
        container.putSupplier(TEST_KEY, () -> "Success");

        assertEquals("Success", container.getUnsafe(TEST_KEY_COPY));
    }

    @Test
    public void safeUnsafeKeyRawSameObject() {
        DataContainer container = new DataContainer();
        container.putRawData(TEST_KEY, "Success");

        assertEquals("Success", container.getUnsafe(TEST_KEY));
    }

    @Test
    public void safeUnsafeKeyRawDifferentObject() {
        DataContainer container = new DataContainer();
        container.putRawData(TEST_KEY, "Success");

        assertEquals("Success", container.getUnsafe(TEST_KEY_COPY));
    }

    @Test
    public void safeUnsafeKeyRawNull() {
        DataContainer container = new DataContainer();
        container.putRawData(TEST_KEY, null);

        assertTrue(container.supports(TEST_KEY));
        assertNull(container.getUnsafe(TEST_KEY));
    }

    @Test
    public void safeUnsafeKeyNullSupplier() {
        DataContainer container = new DataContainer();
        container.putSupplier(TEST_KEY, null);

        assertFalse(container.supports(TEST_KEY));
    }

    @Test
    public void safeUnsafeKeySupplierNull() {
        DataContainer container = new DataContainer();
        container.putSupplier(TEST_KEY, () -> null);

        assertTrue(container.supports(TEST_KEY));
        assertNull(container.getUnsafe(TEST_KEY));
    }

    @Test
    public void cachingSupplier() {
        DataContainer container = new DataContainer();
        String firstObj = "First";
        String secondObj = "Second";

        assertNotSame(firstObj, secondObj);

        container.putCachingSupplier(TEST_KEY, () -> firstObj);

        String found = container.getUnsafe(TEST_KEY);
        assertEquals(firstObj, found);
        assertSame(firstObj, found);
        assertNotSame(secondObj, found);

        String secondCall = container.getUnsafe(TEST_KEY);
        assertSame(found, secondCall);
    }

}