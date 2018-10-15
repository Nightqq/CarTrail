package com.zxdz.car.main.model.domain;

public class QrCodeToMAC {

    private static String mac = "";

    public static String getMAC(String code) {
        switch (code) {
            case "NJZX000000": mac = "54:6C:0E:B8:B3:3A";break;
            case "NJZX000001": mac = "54:6C:0E:B8:B3:3B";break;
            case "NJZX000002": mac = "54:6C:0E:BD:80:A8";break;
            case "NJZX000003": mac = "54:6C:0E:BD:80:EE";break;
            case "NJZX000004": mac = "54:6C:0E:BD:81:84";break;
            case "NJZX000005": mac = "54:6C:0E:BD:81:91";break;
            case "NJZX000006": mac = "54:6C:0E:BD:82:96";break;
            case "NJZX000007": mac = "54:6C:0E:B8:B3:30";break;
            case "NJZX000008": mac = "54:6C:0E:BD:81:BC";break;
            case "NJZX000009": mac = "54:6C:0E:BD:81:9B";break;
            case "NJZX000010": mac = "54:6C:0E:B8:B3:1E";break;
            case "NJZX000011": mac = "A4:34:F1:86:F0:15";break;
            case "NJZX000012": mac = "A4:34:F1:86:EF:ED";break;
            case "NJZX000013": mac = "54:6C:0E:BD:80:F2";break;
            case "NJZX111111": mac = "54:6C:0E:BD:7E:EE";break;
        }
        return mac;
    }
}
