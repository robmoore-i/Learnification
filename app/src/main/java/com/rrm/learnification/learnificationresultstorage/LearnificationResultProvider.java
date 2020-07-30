package com.rrm.learnification.learnificationresultstorage;

import java.util.List;

public interface LearnificationResultProvider {
    List<LearnificationResult> readAll();
}
