## TCP程序设计

在Java中，TCP程序设计是指利用ServerSocket类和Socket类编写的网络通信程序。利用TCP协议进行通信的两个应用程序有主次之分的，一个称为服务端程序，另一个是客户端程序。

1. 服务器程序创建一个ServerSocket（服务器端套接字），调用accept()方法等待客户机来连接
2. 客户端程序创建一个socket，请求与服务器建立连接。
3. 服务器接收客户机的连接请求，同时创建一个新的Socket与客户建立连接。服务器继续等待新的请求。

### ServerSocket服务端

主要功能是等待来自网络上的“请求”，它通过指定端口来等待连接的套接字。服务器套接字依次可以与一个套接字连接。如果多态客户机同时提出连接请求，服务器套接字会将请求连接的客户机存入队列中，然后从中取出一个套接字，与服务器新建的套接字连接起来。若请求连接数大于最大容纳书，则多出的连接请求被拒绝。

| 方法                                                         | 说明                                                        |
| ------------------------------------------------------------ | ----------------------------------------------------------- |
| ServerSocket()                                               | 创建非绑定服务器套接字                                      |
| ServerSocket(int port)                                       | 创建绑定到特定端口的服务器套接字                            |
| ServerSocket(int port, int backlog)                          | 利用指定的backlog创建服务器套接字并将其绑定到指定的本地端口 |
| ServerSocket(int port, int backlog, InetAddress bindAddress) |                                                             |
| accept()                                                     | 等待客户机的连接。若连接，则创建一个套接字                  |
| isBound()                                                    | 判断ServerSocket的绑定状态                                  |
| getInetAddress()                                             | 返回次服务器套接字的本地地址                                |
| isClosed()                                                   | 返回服务器套接字的关闭状态                                  |
| close()                                                      | 关闭服务器套接字                                            |
| bind(SocketAddress endpoint)                                 | 将ServerSocket绑定到特定地址                                |
| getLocalPort()                                               | 返回服务器套接字等待的端口号                                |

### Socket客户端

| 方法                                                         | 说明                                             |
| ------------------------------------------------------------ | ------------------------------------------------ |
| Socket()                                                     | 创建未绑定套接字                                 |
| Socket(InetAddress address, int port)                        | 创建套接字并将其连接到指定IP地址的指定端口号     |
| Socket(String host, int port)                                | 创建套接字并将其连接到指定主机的指定端口号       |
| Socket(String host, int port, InnetAddress localAddr, int localPort) | 创建套接字并将其连接到到指定远程主机的指定端口号 |
| bind(SocketAddress bindpoint)                                | 将套接字绑定到本地地址                           |
| close()                                                      | 关闭此套接字                                     |
| connenct(SocketAddress endpoint)                             | 将此套接字连接到服务器                           |
| connect(SocketAddress endpoint, int timeout)                 | 将此套接字连接到服务器，并指定一个超时值         |
| getInentAddress()                                            | 返回套接字连接地址                               |
| getInputStream()                                             | 返回此套接字的输入流                             |
| getLocalAddresss()                                           | 获取绑定的本地地址                               |
| getLocalPort()                                               | 获取端口号                                       |
| getOutputStream()                                            | 获取输出流                                       |
| getPort()                                                    | 获取远程端口号                                   |
| isBound()                                                    |                                                  |
| isClosed()                                                   |                                                  |
| inConnect()                                                  |                                                  |

### 代码

服务端

```java
import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1100);
		System.out.println("服务器启动成功，等待用户接入...")
		//等待用户接入，知道有用户接入为止
		Socket client = server.accept();
		//得到接入客户端的IP地址
		System.out.println("有客户端接入， 客户IP： " + client.getInetAddress());

		InputStream in = client.getInputStream(); //从客户端生成网络输入流，用于接收来自网络的数据

		OutputStream out = client.getOutputStream(); //从客户端生成网络输出流，用于把数据发送到网络

		byte[] bt = new byte[1024]; //定义一个字节数组，用来存储数据网络
		int len = in.read(bt); //将网络数据写入字节数组
		String data = new String(bt, 0, len); //将网络数据转换为字符串数据
		System.out.println("来自客户端的消息： " + data);
		out.write("我是服务器，欢迎光临".getBytes())
		client.close(); //关闭套接字
	}
}
```

客户端

```c++
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
```

