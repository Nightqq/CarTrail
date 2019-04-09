package com.zxdz.car.main.service;

public class SocketOpenLock {

    private static SocketOpenLock socketOpenLock;

    private SocketOpenLock(){
        if (socketOpenLock!=null){
            socketOpenLock = new SocketOpenLock();
        }
    }
    public static SocketOpenLock getsocket(){

        return socketOpenLock;
    }

    public void openSocket(){

    }

}
