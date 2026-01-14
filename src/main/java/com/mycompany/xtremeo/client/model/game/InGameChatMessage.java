/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.model.game;

/**
 *
 * @author wahid
 */
public record InGameChatMessage(
        InGamePlayer sender,
        String message,
        String time,
        boolean isLocalPlayer
) {}
