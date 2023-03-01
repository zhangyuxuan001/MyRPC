package version3.client;

import lombok.AllArgsConstructor;
import version3.client.RPCClient;
import version3.common.RPCRequest;
import version3.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// SimpleRPCClient实现这个接口，不同的网络方式有着不同的实现
@AllArgsConstructor
public class SimpleRPCClient implements RPCClient{
    private String host;
    private int port;

    // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
    // 这里的request是封装好的，不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            // 发起一次Socket连接请求
            Socket socket = new Socket(host, port);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(request);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            RPCResponse response = (RPCResponse) objectInputStream.readObject();

            //System.out.println(response.getData());
            return response;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println();
            return null;
        }
    }
}
