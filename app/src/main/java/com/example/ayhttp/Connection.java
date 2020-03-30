package com.example.ayhttp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

class Connection {

    private final ConnectionPool connectionPool;
    private Socket socket;
    private SocketAddress address;

    Connection(ConnectionPool connectionPool, SocketAddress address){
        this.connectionPool = connectionPool;
        this.address = address;

    }

    void connect(int connectTimeout, int readTimeout){
        try {
            connectSocket(connectTimeout, readTimeout);
        }catch (IOException e){
            close(socket);
            socket = null;
        }
    }

    HttpCodec newCodec(){
        try {
            return new HttpCodec(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void connectSocket(int connectTimeout, int readTimeout) throws IOException {
        socket = new Socket();
        socket.connect(address, connectTimeout);
        socket.setSoTimeout(readTimeout);
    }

    private void close(Socket socket){
        if (socket != null){
            try{
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
