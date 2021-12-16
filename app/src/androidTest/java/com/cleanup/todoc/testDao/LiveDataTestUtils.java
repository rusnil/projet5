package com.cleanup.todoc.testDao;

import androidx.lifecycle.LiveData;

import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtils {

    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer observer = (observable, o) -> {
            data[0] = o;
            latch.countDown();
            liveData.removeObserver((androidx.lifecycle.Observer<? super T>) observable);
        };
        liveData.observeForever((androidx.lifecycle.Observer<? super T>) observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}