/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.network;

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
        if(connection == null)
            connection = new ClientConnection();
        return connection;
    }
    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to server");
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            running = true;
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

    public void disconnect() {
        running = false;
        try {
            if (socket != null) socket.close();
        } catch (IOException ignored) {}
    }
}

interface MessageListener {
    void onMessage(String msg);
    void onDisconnect(Exception e);
}

