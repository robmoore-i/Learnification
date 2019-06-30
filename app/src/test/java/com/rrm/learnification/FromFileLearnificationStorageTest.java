package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FromFileLearnificationStorageTest {
    private AndroidLogger logger = mock(AndroidLogger.class);

    @Test
    public void onReadIfFileDoesntExistThenItCreatesIt() {
        AndroidStorage mockAndroidStorage = mock(AndroidStorage.class);
        when(mockAndroidStorage.doesFileExist(FromFileLearnificationStorage.FILE_NAME)).thenReturn(false);
        FromFileLearnificationStorage fromFileLearnificationStorage = new FromFileLearnificationStorage(logger, mockAndroidStorage);

        fromFileLearnificationStorage.read();

        verify(mockAndroidStorage, times(1)).createNewEmptyFile(FromFileLearnificationStorage.FILE_NAME);
    }
}