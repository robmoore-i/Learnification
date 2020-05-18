package com.rrm.learnification.idgenerator;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InternalStorageIdGeneratorTest {
    private final AndroidLogger dummyLogger = mock(AndroidLogger.class);
    private final FileStorageAdaptor mockStorage = mock(FileStorageAdaptor.class);
    private final IdGenerator generator = new InternalStorageIdGenerator(dummyLogger, mockStorage, "test");

    @Test
    public void firstIdIs0() throws IOException {
        when(mockStorage.readLines(anyString())).thenThrow(new FileNotFoundException());
        assertEquals(0, generator.nextId());
    }

    @Test
    public void theLastIdIsTheIdStoredMinusOne() throws IOException {
        when(mockStorage.readLines(anyString())).thenReturn(Collections.singletonList("4"));
        assertEquals(3, generator.lastId());
    }

    @Test
    public void theLastIdIsZeroIfTheresNoIdStored() throws IOException {
        when(mockStorage.readLines(anyString())).thenThrow(new FileNotFoundException());
        assertEquals(0, generator.lastId());
    }

    @Test
    public void ifThereIsAValueStoredOnStartupThenThatIsReadToDetermineTheNextId() throws IOException {
        when(mockStorage.readLines(anyString())).thenReturn(Collections.singletonList("5"));
        assertEquals(5, generator.nextId());
    }

    @Test
    public void ifThereIsAValueStoredOnStartupThenThatIsReadToDetermineTheLastId() throws IOException {
        when(mockStorage.readLines(anyString())).thenReturn(Collections.singletonList("5"));
        assertEquals(4, generator.lastId());
    }
}