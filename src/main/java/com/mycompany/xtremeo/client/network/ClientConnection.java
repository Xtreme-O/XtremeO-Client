/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;

/**
 *
 * @author Elsobky
 */
public class ClientConnection {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean running = false;

    public void connect() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 4242);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            running = true;
        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to server", e);
        }
    }


    public void startListening(MessageListener listener) {
        new Thread(() -> {
            try {
                while (running) {
                    String msg = dis.readUTF();
                    listener.onMessage(msg);
                }
            } catch (Exception e) {
                listener.onDisconnect();
            }
        }).start();
    }


    public void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            running = false;
            socket.close();
            System.out.println("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

interface MessageListener {
    void onMessage(String msg);
    void onDisconnect();
}

