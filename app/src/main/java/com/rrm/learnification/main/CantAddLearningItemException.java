package com.rrm.learnification.main;

class CantAddLearningItemException extends RuntimeException {
    CantAddLearningItemException(Exception cause) {
        super(cause);
    }
}
