package com.wittarget.immunization.utils;

public interface AsyncResponse {
    void onTaskComplete(Object output);

    void onTaskStart();
}
