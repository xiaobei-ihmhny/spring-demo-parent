package com.xiaobei.spring.demo.event;

import java.util.Observable;
import java.util.Observer;

/**
 * java事件/监听器编程模型 {@link Observable} {@link Observer}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/23 20:56
 *
 * @see Observable
 * @see Observer
 *
 */
public class ObserverDemo {

    public static void main(String[] args) {
        EventObservable observable = new EventObservable();
        // 关联事件发送者与事件监听者
        observable.addObserver(new EventObserver());
        // 通知事件监听者
        observable.notifyObservers("Hello, World");
    }

    static class EventObservable extends Observable {

        /**
         * 将 {@link Observable} 中的方法提升为：{@code public}
         */
        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }

        @Override
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
            clearChanged();
        }
    }

    /**
     * 事件监听者
     */
    static class EventObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            System.out.printf("收到事件：%s", arg);
        }
    }



}
