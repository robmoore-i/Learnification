package com.rrm.learnification.idgenerator;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
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
        assertThat(generator.nextId(), equalTo(0));
    }

    @Test
    public void theLastIdIsTheIdStoredMinusOne() throws IOException {
        when(mockStorage.readLines(anyString())).thenReturn(Collections.singletonList("4"));
        assertThat(generator.lastId(), equalTo(3));
    }

    @Test
    public void theLastIdIsZeroIfTheresNoIdStored() throws IOException {
        when(mockStorage.readLines(anyString())).thenThrow(new FileNotFoundException());
        assertThat(generator.lastId(), equalTo(0));
    }

    @Test
    public void ifThereIsAValueStoredOnStartupThenThatIsReadToDetermineTheNextId() throws IOException {
        when(mockStorage.readLines(anyString())).thenReturn(Collections.singletonList("5"));
        assertThat(generator.nextId(), equalTo(5));
    }

    @Test
    public void ifThereIsAValueStoredOnStartupThenThatIsReadToDetermineTheLastId() throws IOException {
        when(mockStorage.readLines(anyString())).thenReturn(Collections.singletonList("5"));
        assertThat(generator.lastId(), equalTo(4));
    }
}