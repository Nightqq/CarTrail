package com.zxdz.car.main.bean;

/**
 * Created by Administrator on 2018\5\7 0007.
 */

public class MYBlueToothDevice {
    private String name;
    private String MAC;

    public MYBlueToothDevice(String name, String MAC) {
        this.name = name;
        this.MAC = MAC;
    }
    public String getName() {
        return name;
    }

    public String getMAC() {
        return MAC;
    }
}
