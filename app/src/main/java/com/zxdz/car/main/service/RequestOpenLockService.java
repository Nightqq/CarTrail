package com.zxdz.car.main.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/16.
 */

public class RequestOpenLockService extends Service {
    public static int port = 8001;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("开启服务成功11！");
        new Thread(new ChatServer()).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static RequestOpenLockListenerserve listener = null;

    public interface RequestOpenLockListenerserve {
        void successful();
    }

    private class ChatServer implements Runnable {
        boolean started = false;
        ServerSocket ss = null;
        List<Client> clients = new ArrayList<Client>();

        public void start() {
            try {
                //第二个参数是最大连接数，第三个ip不指定就会使用系统自动分配的会出现不匹配的情况
                ss = new ServerSocket(port);
                started = true;
            } catch (BindException e) {
                LogUtils.e("端口被占用，请关掉相关程序并重新运行服务器！");
                //System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while (started) {
                    LogUtils.e("等待连接" + "ip:" + ss.getLocalSocketAddress() + "端口：" + ss.getLocalPort());
                    Socket s = ss.accept();//是一个阻塞线程的方法，直到有新客户加入
                    Client c = new Client(s);
                    LogUtils.e("等待接受消息");
                    new Thread(c).start();
                    clients.add(c);
                    // dis.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ss != null){
                        ss.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            start();
        }


        class Client implements Runnable {
            private Socket s;
            private DataInputStream dis = null;
            private DataOutputStream dos = null;

            public Client(Socket s) {
                this.s = s;
                try {
                    dis = new DataInputStream(s.getInputStream());
                    dos = new DataOutputStream(s.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void send(String str) {
                try {
                    dos.writeUTF(str);
                } catch (IOException e) {
                    clients.remove(this);
                    System.out.println("对方退出了！我从List里面去掉了！");
                }
            }

            public void run() {
                try {
                    int len = 0;
                    byte[] buf = new byte[1024];
                    while ((len = dis.read(buf)) != -1) {//接受数据，接受后关闭
                        LogUtils.e("收到消息来了" + new String(buf, 0, len));
                        listener.successful();
                        dis.close();
                        dos.close();
                        s.close();
                        ss.close();
                        listener = null;
                        stopSelf();
                    }
                } catch (EOFException e) {
                    System.out.println("Client closed!");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (dis != null)
                            dis.close();
                        if (dos != null)
                            dos.close();
                        if (s != null) {
                            s.close();
                            // s = null;
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
