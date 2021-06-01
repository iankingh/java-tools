package com.ian.tools.jms.activemq;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
 
public class SocketChannelExample {
	private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

	public static void main(String[] args) throws IOException, InterruptedException {
		int port = 11673;	//埠號
		String id = "01";
		SocketChannel channel = SocketChannel.open();
//    	SocketChannel channel1 = SocketChannel.open();
		if (args.length > 0 && args.length > 1)
			id = args[1];
		
//        we open this channel in non blocking mode
//    		channel.configureBlocking(false);
//      	channel1.configureBlocking(true);
/*     	System.out.println("Sleep 30 seconds");
       	Thread.sleep(30 * 1000);*/
		
		System.out.println("Start connect");
		
		channel.connect(new InetSocketAddress("127.0.0.1", port));	//IP設定
		while (!channel.finishConnect()) {
			System.out.println("still connecting");
		}
		System.out.println("Connect Successfully");
		
//   	System.out.println("Connect 30004 Successfully");
//     	Reader send Test to AP Server 
//	    while (!channel1.finishConnect()) {
//	    	System.out.println("still connecting");
//	    }
//	    System.out.println("Connect 31004 Successfully");
//	    boolean retry = true;
//   while (retry) {
//  }
		
		//電文內容
		CharBuffer buffer = CharBuffer.wrap(
				"000139ISCE0000200FD010000000CBC NU012017101115033100000000NUAH991061011NUAH99NUAI5000001MO3R221062838USD003767762692TW1011011602A125301         Y");
		while (buffer.hasRemaining()) {
			channel.write(Charset.defaultCharset().encode(buffer));
		}
		// see if any message has been received
		ByteBuffer bufferA = ByteBuffer.allocate(1024);
		int count = 0;
		String message = "";
		bufferA.clear();
		if ((count = channel.read(bufferA)) > 0) {
			// flip the buffer to start reading
			bufferA.flip();
			byte[] bytes = new byte[count];
			bufferA.get(bytes);
			message = new String(bytes);
			bufferA.clear();
		}

		System.out.println(" len=" + message.length() + "-->" + message);

//   Thread.sleep(1000);
//   if ( channel.finishConnect()) {
//     channel.shutdownInput();
//     channel.shutdownOutput();
//     System.out.println("finished");
//   }
//   else System.out.println("not finished");
		channel.close();
		System.out.println("finished");

	}

}