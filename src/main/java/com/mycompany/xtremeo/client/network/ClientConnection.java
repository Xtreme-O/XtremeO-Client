/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.network;

import com.mycompany.xtremeo.client.protocol.dispatcher.ResponseDispatcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Elsobky
 */
public class ClientConnection {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private volatile boolean running = false;
    private ClientConnection(){}
    private static ClientConnection connection;
    public static ClientConnection getInstance(){
        if(connection == null) {
            connection = new ClientConnection();
            ResponseDispatcher dispatcher = new ResponseDispatcher();
            connection.connect(NetworkConfig.SERVER_HOST, NetworkConfig.SERVER_PORT);
            connection.startListening(
                    new DispatcherMessageListener(dispatcher)
            );
        }
        return connection;
    }

    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            running = true;
            System.out.println("Connected to server");
        } catch (IOException e) {
            throw new RuntimeException("Unable to connect", e);
        }
    }


    public void startListening(MessageListener listener) {
        Thread listenerThread = new Thread(() -> {
            try {
                while (running) {
                    System.out.println("Waiting for message...");
                    String msg = dis.readUTF();
                    System.out.println("Received: " + msg);
                    listener.onMessage(msg);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                if (running) {
                    listener.onDisconnect(e);
                }
            } finally {
                disconnect();
            }
        }, "client-listener-thread");
        listenerThread.start();
    }


    public synchronized void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to send message", e);
        }
    }

    public static boolean isConnected() {
        return connection != null;
    }

    public void disconnect() {
        running = false;
        try {
            if (socket != null)
                socket.close();
        } catch (IOException ignored) {}
        connection = null;
    }
}

interface MessageListener {
    void onMessage(String msg);
    void onDisconnect(Exception e);
}

