package com.yrsbradford.binapp.transmission;

/**
 * Created by 10askinsw on 27/07/2015.
 */
public class Timer {

    /**
     * Multiple bins
     * Use this class
     * Time till delivery
     * List of bin objects
     * UNIX timestamps or calendar system
     * > repeats once per week, once per two weeks, monthly etc
     * hashmap bins : times
     * > save to file
     */

    public static int MINUTE = 60;
    public static int HOUR = 3600;
    public static int DAY = 86400;

    private int lastTime;

    public Timer() {

    }

    /**
     * Tells you if the time since the last reset is larger than the time specified
     * @param time
     * @return if the given time has elapsed
     */
    public boolean hasElapsed(int time){
        return getElapsedTime() >= time;
    }

    /**
     * Tells you the elapsed time since the last reset
     * @return the number of seconds since the last reset
     */
    public int getElapsedTime() {
        return getTime() - lastTime;
    }

    /**
     * Resets the time to the current time, making the elapsed time zero
     */
    public void reset(){
        lastTime = getTime();
    }

    /**
     * Returns the time since when the last reset
     * @return the last time the timer was reset
     */
    public int getLastTime(){
        return lastTime;
    }

    /**
     * Returns the current time in seconds
     * @return current time in seconds
     */
    private int getTime(){
        return (int)(System.currentTimeMillis() / 1000);
    }
}
