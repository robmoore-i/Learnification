package com.rrm.learnification;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class JavaInMemoryIdGeneratorTest {
    private JavaInMemoryIdGenerator generator;

    @Before
    public void setUp() {
        generator = new JavaInMemoryIdGenerator();
    }

    @Test
    public void firstIdIs0() {
        assertThat(generator.nextId(), equalTo(0));
    }

    @Test
    public void secondIdIs1() {
        generator.nextId();
        assertThat(generator.nextId(), equalTo(1));
    }

    @Test
    public void afterReturningAnIdOnceTheLastIdIs0() {
        generator.nextId();
        assertThat(generator.lastId(), equalTo(0));
    }

    @Test
    public void afterReturningAnIdTwiceTheLastIdIs1() {
        generator.nextId();
        generator.nextId();
        assertThat(generator.lastId(), equalTo(1));
    }

    @Test
    public void afterReturningAnIdTwiceThenResettingTheLastIdIsStill1() {
        generator.nextId();
        generator.nextId();
        generator.reset();
        assertThat(generator.lastId(), equalTo(1));
    }
}