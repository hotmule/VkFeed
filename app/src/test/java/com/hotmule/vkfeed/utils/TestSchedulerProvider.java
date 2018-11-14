package com.hotmule.vkfeed.utils;

import com.hotmule.vkfeed.utils.rx.SchedulerProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class TestSchedulerProvider implements SchedulerProvider {

    private final TestScheduler mTestScheduler;

    public TestSchedulerProvider(TestScheduler testScheduler) {
        mTestScheduler = testScheduler;
    }

    @Override
    public Scheduler ui() {
        return mTestScheduler;
    }

    @Override
    public Scheduler io() {
        return mTestScheduler;
    }
}
