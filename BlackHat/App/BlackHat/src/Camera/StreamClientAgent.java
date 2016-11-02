package Camera;

import java.awt.Dimension;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class StreamClientAgent implements IStreamClientAgent {

//    protected final static Logger logger = LoggerFactory.getLogger(StreamClientAgent.class);
    protected final ClientBootstrap clientBootstrap;
    protected final StreamClientListener streamClientListener;
    protected final StreamFrameListener streamFrameListener;
    protected final Dimension dimension;
    protected Channel clientChannel;

    public StreamClientAgent(StreamFrameListener streamFrameListener,
            Dimension dimension) {
        super();
        this.dimension = dimension;
        this.clientBootstrap = new ClientBootstrap();
        this.clientBootstrap.setFactory(new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));
        this.streamFrameListener = streamFrameListener;
        this.streamClientListener = new StreamClientListenerIMPL();
        this.clientBootstrap.setPipelineFactory(
                new StreamClientChannelPipelineFactory(
                        streamClientListener,
                        streamFrameListener,
                        dimension));
    }

    @Override
    public synchronized void connect(SocketAddress streamServerAddress) {
//        logger.info("going to connect to stream server :{}", streamServerAddress);
        clientBootstrap.connect(streamServerAddress);
    }

    @Override
    public synchronized void stop() {
        clientChannel.close();
        clientBootstrap.releaseExternalResources();
    }

    protected class StreamClientListenerIMPL implements StreamClientListener {

        @Override
        public void onConnected(Channel channel) {
            clientChannel = channel;
        }

        @Override
        public void onDisconnected(Channel channel) {
        }

        @Override
        public void onException(Channel channel, Throwable t) {
        }

    }

}
