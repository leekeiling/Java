import java.io.*;
import java.net.*;

public class Clinet {
	public static void main(String[] args) throws UnknowException, IOException {
		Socket client = new Socket("127.0.0.1", 1100);
		System.out.println("连接服务器成功");
		InputStream in = client.getInputStream(); //从客户端生成网络输入流，用于接收来自网络的数据

		OutputStream out = client.getOutputStream(); //从客户端生成网络输出流，用于把数据发送到网络

		out.write("我是客户端，欢迎光临".getBytes())；

		byte[] bt = new byte[1024];
		int len = in.read(bt);
		String data = new String(bt, 0, len);
		System.out.println("来自服务器的消息： " + data);
		client.close(); //关闭套接字
	}
}