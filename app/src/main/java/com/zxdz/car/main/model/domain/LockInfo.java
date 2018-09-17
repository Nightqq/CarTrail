package com.zxdz.car.main.model.domain;

import java.util.UUID;

/**
 * Created by Lenovo on 2017/11/8.
 */

public class LockInfo {
    public static final UUID Server_UUID = UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cb7");
    public static final UUID Write_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    public static final UUID Read_UUID = UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb");
    public static final UUID Descirptor_UUID = UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cb8");
    public static final byte[] bytes_key = {(byte)0xFE,0x34,0x00,0x11,0x08,0x79,0x4F,0x54,0x6D,0x4B,0x35,0x30,0x7A};//开锁
}
