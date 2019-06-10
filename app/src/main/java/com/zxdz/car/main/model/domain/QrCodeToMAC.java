package com.zxdz.car.main.model.domain;

public class QrCodeToMAC {

    private static String MAC = "";

    public static String getMAC(String code) {
        switch (code) {
            case "NJZX000000": MAC = "54:6C:0E:B8:B3:3A";break;
            case "NJZX000001": MAC = "54:6C:0E:B8:B3:3B";break;
            case "NJZX000002": MAC = "A4:34:F1:86:EF:EA";break;
            case "NJZX000003": MAC = "A4:34:F1:86:F0:51";break;
            case "NJZX000004": MAC = "54:6C:0E:BD:81:84";break;
            case "NJZX000005": MAC = "54:6C:0E:BD:81:C9";break;
            case "NJZX000006": MAC = "54:6C:0E:BD:82:96";break;
            case "NJZX000007": MAC = "54:6C:0E:BD:83:D6";break;
            case "NJZX000008": MAC = "54:6C:0E:BD:81:BC";break;
            case "NJZX000009": MAC = "54:6C:0E:BD:81:9B";break;
            case "NJZX000010": MAC = "54:6C:0E:B8:B3:1E";break;
            case "NJZX000011": MAC = "A4:34:F1:86:F0:15";break;
            case "NJZX000012": MAC = "A4:34:F1:86:EF:ED";break;
            case "NJZX000013": MAC = "54:6C:0E:BD:80:F2";break;
            case "NJZX111111": MAC = "54:6C:0E:BD:7E:EE";break;
            case "NJZX000014": MAC = "A4:34:F1:86:EF:E4";break;
            case "NJZX000015": MAC = "54:6C:0E:BD:84:85";break;
            case "NJZX000016": MAC = "54:6C:0E:BD:80:FD";break;
            case "NJZX000017": MAC = "54:6C:0E:BD:81:8E";break;
            case "NJZX000018": MAC = "54:6C:0E:BD:81:C2";break;
            case "NJZX000019": MAC = "54:6C:0E:BD:84:EB";break;
            case "NJZX000020": MAC = "54:6C:0E:BD:83:A7";break;
            case "NJZX000021": MAC = "54:6C:0E:BD:83:CE";break;
            case "NJZX000022": MAC = "A4:34:F1:86:F0:46";break;
            case "NJZX000023": MAC = "54:6C:0E:BD:80:F9";break;
            case "NJZX000024": MAC = "54:6C:0E:BD:81:DD";break;
            case "NJZX000025": MAC = "54:6C:0E:BD:81:99";break;
            case "NJZX000026": MAC = "54:6C:0E:BD:81:D2";break;
            case "NJZX000027": MAC = "54:6C:0E:BD:7F:F9";break;
            case "NJZX000028": MAC = "54:6C:0E:BD:7F:D9";break;
            case "NJZX000029": MAC = "A4:34:F1:86:F0:01";break;
            case "NJZX000030": MAC = "54:6C:0E:BD:81:AC";break;
            case "NJZX000031": MAC = "54:6C:0E:BD:82:FB";break;
            case "NJZX000032": MAC = "54:6C:0E:BD:81:D5";break;
            case "NJZX000033": MAC = "54:6C:0E:BD:7F:CD";break;
            case "NJZX000034": MAC = "54:6C:0E:BD:7F:C8";break;
            case "NJZX000035": MAC = "A4:34:F1:86:F0:0E";break;
            case "NJZX000036": MAC = "A4:34:F1:86:F0:5A";break;
            case "NJZX000037": MAC = "54:6C:0E:BD:81:EA";break;

            case "NJZX000051": MAC = "54:6C:0E:BD:80:A8";break;
            case "NJZX000052": MAC = "54:6C:0E:BD:80:EE";break;
            case "NJZX000053": MAC = "54:6C:0E:BD:81:91";break;
            case "NJZX000054": MAC = "54:6C:0E:B8:B3:30";break;
            case "NJZX000055": MAC = "54:6C:0E:BD:7F:EC";break;
            case "NJZX000056": MAC = "A4:34:F1:86:F0:26";break;
            case "NJZX000057": MAC = "A4:34:F1:86:F0:65";break;
            case "NJZX000058": MAC = "A4:34:F1:86:F0:0D";break;
            case "NJZX000059": MAC = "54:6C:0E:BD:80:98";break;
            case "NJZX000060": MAC = "54:6C:0E:BD:81:AE";break;
            case "NJZX000061": MAC = "54:6C:0E:BD:7F:DF";break;
            case "NJZX000062": MAC = "54:6C:0E:BD:81:B2";break;
            case "NJZX000063": MAC = "54:6C:0E:BD:81:97";break;
            case "NJZX000064": MAC = "54:6C:0E:BD:81:93";break;
            case "NJZX000065": MAC = "54:6C:0E:BD:80:AB";break;
            case "NJZX000066": MAC = "A4:34:F1:86:F0:3C";break;
            case "NJZX000067": MAC = "54:6C:0E:BD:83:EA";break;
            case "NJZX000068": MAC = "A4:34:F1:86:F0:34";break;
            case "NJZX000069": MAC = "54:6C:0E:BD:81:A2";break;
            case "NJZX000070": MAC = "54:6C:0E:BD:7F:A9";break;
            case "NJZX000071": MAC = "A4:34:F1:86:F1:05";break;
            case "NJZX000072": MAC = "A4:34:F1:86:EF:D6";break;
            case "NJZX000073": MAC = "54:6C:0E:BD:82:CC";break;
            case "NJZX000074": MAC = "A4:34:F1:86:F0:37";break;
            case "NJZX000075": MAC = "54:6C:0E:BD:83:C6";break;//2019年六月5日新增


        }
        return MAC;
    }
}
