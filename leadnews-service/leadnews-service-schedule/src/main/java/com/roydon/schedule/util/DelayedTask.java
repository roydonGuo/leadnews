package com.roydon.schedule.util;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedTask implements Delayed {

    // 任务的执行时间
    private int executeTime = 0;

    /**
     * 构造函数，参数为延迟多少秒后执行
     *
     * @param delay SECOND
     */
    public DelayedTask(int delay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, delay);
        this.executeTime = (int) (calendar.getTimeInMillis() / 1000);
    }

    /**
     * 元素在队列中的剩余时间
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        Calendar calendar = Calendar.getInstance();
        return executeTime - (calendar.getTimeInMillis() / 1000);
    }

    /**
     * 元素排序
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        long val = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return val == 0 ? 0 : (val < 0 ? -1 : 1);
    }

    public static void main(String[] args) {
        DelayQueue<DelayedTask> queue = new DelayQueue<>();

        queue.add(new DelayedTask(5));
        queue.add(new DelayedTask(10));
        queue.add(new DelayedTask(15));

        System.out.println(LocalTime.now() + " start consume");

        // 循环的从延迟队列中拉取任务
        // 缺点：数据丢失无法处理，所以需要磁盘持久化的延时队列
        while (queue.size() != 0) {
            DelayedTask delayedTask = queue.poll();
            if (delayedTask != null) {
                System.out.println(LocalTime.now() + " consume task");
            }
            //每隔一秒消费一次
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
