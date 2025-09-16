package com.example.crud.designpattern;

public class SingletonLog {

    private static volatile SingletonLog instance =null;

    private SingletonLog(){}

    public static SingletonLog getInstance() {

        if(instance ==null) {
            synchronized (SingletonLog.class) {
                if(instance==null) {
                    instance=new SingletonLog();
                }
            }
        }
        return instance;
    }

    public void log(String message) {

        System.out.println("[LOG] " + message);
    }




}

