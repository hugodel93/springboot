package org.example.demo;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.grpc.api.Broker;
import org.example.grpc.api.BrokerRPCServiceGrpc;
import org.example.grpc.api.Metric;
import org.example.grpc.api.MetricRPCServiceGrpc;

import java.util.Iterator;


/***
 * @Title GRPCClient
 * @Description:
 * @author: hang.hu
 * @date: 2024/1/31 上午10:30
 * @version: 1.0.0
 **/
public class GRPCClient {
    private static final String host = "localhost";
    private static final int serverPort = 9999;

    public static void main( String[] args ) throws Exception {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress( host, serverPort ).usePlaintext().build();
        try {
            MetricRPCServiceGrpc.MetricRPCServiceStub metricRPCServiceStub = MetricRPCServiceGrpc.newStub( managedChannel );
            Metric.MetricReq metricReq = Metric.MetricReq.newBuilder().setId( 1 ).setName("broker").build();
            metricRPCServiceStub.getTracker( metricReq, new StreamObserver<Any>() {
                @Override
                public void onNext( Any metricRsp ) {
                    try {
                        Broker.BrokerRsp brokerRsp = metricRsp.unpack( Broker.BrokerRsp.class );
                        System.out.println( brokerRsp.getBrokerVersion() );
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onError( Throwable throwable ) {
                    System.out.println( throwable );
                }

                @Override
                public void onCompleted() {
                    System.out.println( "completed" );
                }
            } );

        } finally {
            Thread.sleep( 1000 );
            managedChannel.shutdown();
        }
    }
}
