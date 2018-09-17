package com.zxdz.car.main.model.domain;

public class QrCodeToMAC {

    private static String mac = "";

    public static String getMAC(String code) {
        switch (code) {
            case "NJZX000000":
                mac = "";
                break;
            case "NJZX000001":
                mac = "";
                break;
        }
        return mac;
    }
}
