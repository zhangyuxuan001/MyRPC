package version3.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyRPCServer implements RPCServer {
    private ServiceProvider serviceProvider;
    @Override
    public void start(int port) {
        // netty 服务线程组boss负责建立连接， work负责具体的请求
        //创建两个线程组 boosGroup、workerGroup
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        System.out.printf("Netty服务端启动了...");
        try {
            // 启动netty服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 初始化
            // 配置两个线程组boosGroup和workerGroup
            serverBootstrap.group(bossGroup,workGroup)
                    //设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    //使用匿名内部类的形式初始化通道对象
                    .childHandler(new NettyServerInitializer(serviceProvider));
            // 绑定端口号，启动服务端，同步阻塞
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 对关闭通道进行监听，死循环监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
    }
}
