package server;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ChannelOrder extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        ch.pipeline().addLast(
                new RequestDecoder(),
                new ResponseDataEncoder(),
                new ProcessingHandler());
    }
}
