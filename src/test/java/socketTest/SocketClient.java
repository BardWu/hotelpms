package socketTest;



import java.io.*;
import java.net.Socket;



public class SocketClient {

    private String host;
    private int port;
    private Socket socket;
    private OutputStream os = null;
    private InputStream is = null;
    private BufferedReader bufferedReader = null;

    public SocketClient(String host ,int port){
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


        String host = "";
        int port = 0;
        SocketClient socketClient = new SocketClient(host,port);
        socketClient.connect();

    }


}
