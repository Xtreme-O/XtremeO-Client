/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.adapter.LocalDateTimeAdapter;
import com.mycompany.xtremeo.client.enums.ActionType;
import com.mycompany.xtremeo.client.model.lobby.ChatMessageData;
import com.mycompany.xtremeo.client.network.ClientConnection;
import com.mycompany.xtremeo.client.protocol.envelope.Header;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author LOQ
 */
public class InGameChatService {
    
    private ClientConnection connection;
    //private ChatMessageData chatMessage;
    
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create(); 

    
    
    public void chatSend(ChatMessageData message){
       
        Header header = new Header("JSON", ActionType.IN_GAME_MESSAGE.name());
        RequestEnvelope<ChatMessageData> request = new RequestEnvelope<>(header, message);
        connection.send(gson.toJson(request));
        
    } 
}


