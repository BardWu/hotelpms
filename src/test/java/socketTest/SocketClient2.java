package socketTest;

import com.networks.pms.bean.condition.usc.Dnd;
import com.networks.pms.bean.condition.usc.GuestMessage;
import com.networks.pms.bean.condition.usc.MessageLamp;

import java.io.*;
import java.net.Socket;


/**
 * @program: hotelpms
 * @description:
 * @author: wh
 * @create: 2020-04-19 13:53
 *
 * cmd 运行java:
 * java -c xxx.java
 * java xxx
 */
public class SocketClient2 {

    private String host;
    private int port;
    private Socket socket;
    private OutputStream os = null;
    private InputStream is = null;
    private BufferedReader bufferedReader = null;

    public SocketClient2(String host , int port){
        this.host = host;
        this.port = port;
    }

    private void connect() {
        try {
            socket = new Socket(host, port);
            is = socket.getInputStream();
            os = socket.getOutputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            System.out.println("连接成功");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getMessage() {
        connect();
        int len = -1;
        String strData = null;
        while (1==1) {
            try {
                char[] buf = new char[1024 * 15];
                len = bufferedReader.read(buf);
                if (len != -1) {
                    strData = new String(buf, 0, len);
                    System.out.println("接收的信息："+strData);
                }
            } catch (IOException e) {//关闭socket
                e.printStackTrace();
            }
        }
    }

    void  send(String message){
        try {
            connect();

            os.write(message.getBytes("utf-8"));
            os.flush();
            System.out.println(message+" 已发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


     /*   String host = "";
        int port = 0;
        SocketClient2 socketClient = new SocketClient2(host,port);
        socketClient.connect();*/
        //话单
       // socketClient.getMessage();

        // checkin 、Checkout
        GuestMessage guestMessage = new GuestMessage("1908","zhangsan",GuestMessage.CheckOut_Type);
        System.out.println(guestMessage.toUcs());
    //    String message = guestMessage.toUcs();
        //MessageLamp
        MessageLamp messageLamp = new MessageLamp("2023",MessageLamp.MessageLamp_ON);
        System.out.println(messageLamp.toUsc()); ;
        //DND
        Dnd dnd = new Dnd("1908",Dnd.DND_OFF);
        System.out.println(dnd.toUsc());
      //  socketClient.send(message);
    }


}
