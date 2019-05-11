package netty.sample.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.sample.proto.UnixTime;

/**
 * Handles a server-side channel.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        UnixTime time = UnixTime.newBuilder().setValue((int) (System.currentTimeMillis() / 1000L + 2208988800L)).build();
        System.out.println("Sending: " + time);
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(ChannelFutureListener.CLOSE);//Close channel after sent
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
