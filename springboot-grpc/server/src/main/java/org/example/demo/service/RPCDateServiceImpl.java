package org.example.demo.service;

import com.google.protobuf.Any;
import io.grpc.stub.StreamObserver;
import org.example.grpc.api.Broker;
import org.example.grpc.api.BrokerRPCServiceGrpc;
import org.example.grpc.api.Metric;
import org.example.grpc.api.MetricRPCServiceGrpc;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * @Title RPCDateServiceImpl
 * @Description:
 * @author: hang.hu
 * @date: 2024/1/31 上午10:39
 * @version: 1.0.0
 **/
public class RPCDateServiceImpl extends MetricRPCServiceGrpc.MetricRPCServiceImplBase{

    @Override
    public void getTracker(Metric.MetricReq request, StreamObserver<Any> responseObserver) {
        Broker.BrokerRsp brokerRsp = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("今天是"+"yyyy年MM月dd日 E kk点mm分");
        try {
            String nowTime = simpleDateFormat.format( new Date() );
            brokerRsp = Broker.BrokerRsp.newBuilder().setBrokerVersion(nowTime).build();
            responseObserver.onNext(  Any.pack( brokerRsp ) );

            String nowTime2 = simpleDateFormat.format( new Date() );
            brokerRsp = Broker.BrokerRsp.newBuilder().setBrokerVersion(nowTime2).build();
            responseObserver.onNext(  Any.pack( brokerRsp ) );
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            try {
                Thread.sleep( 10000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onCompleted();
            System.out.println( "completed");
        }
    }

}
