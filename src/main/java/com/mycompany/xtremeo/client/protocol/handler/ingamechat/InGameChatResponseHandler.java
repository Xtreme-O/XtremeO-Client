/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.protocol.handler.ingamechat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.xtremeo.client.model.common.MessageBody;
import com.mycompany.xtremeo.client.model.lobby.ChatMessageData;
import com.mycompany.xtremeo.client.protocol.envelope.RequestEnvelope;
import com.mycompany.xtremeo.client.protocol.handler.ResponseHandler;
import java.util.function.Consumer;

/**
 *
 * @author LOQ
 */
public class InGameChatResponseHandler implements ResponseHandler<ChatMessageData>{
    
    static Consumer<ChatMessageData> onMessageReceived;
    
    @Override
    public void handle(String json, Gson gson) {
        RequestEnvelope<ChatMessageData> request = gson.fromJson(json, new TypeToken<RequestEnvelope<ChatMessageData>>(){}.getType());
        ChatMessageData body = request.getBody();
        onMessageReceived.accept(body);
    } 
}
 