package clientTest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelFuture;
import server.RequestData;
import server.ResponseData;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {

        RequestData msg = new RequestData();
        msg.setIntValue(123);
        msg.setStringValue(
                "all work and no play makes jack a dull boy");
        msg.setFloatValue(79.5);
        ChannelFuture future = ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof ResponseData) {
            System.out.println(((ResponseData) msg).getFloatValue());
        } else {
            System.out.println("Unknown data type");
        }
        //System.out.println((ResponseData)msg);
        ctx.close();
    }
}