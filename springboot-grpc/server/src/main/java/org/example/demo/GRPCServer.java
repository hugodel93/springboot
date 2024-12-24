package org.example.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.demo.service.RPCDateServiceImpl;

/***
 * @Title GRPCServer
 * @Description:
 * @author: hang.hu
 * @date: 2024/1/31 上午10:38
 * @version: 1.0.0
 **/
public class GRPCServer {
    private static final int port = 9999;
    public static void main( String[] args ) throws Exception {
        Server server = ServerBuilder.
                forPort(port)
                .addService( new RPCDateServiceImpl() )
                .build().start();
        System.out.println( "grpc服务端启动成功, 端口=" + port );
        server.awaitTermination();
    }
}
