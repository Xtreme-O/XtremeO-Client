/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.xtremeo.client.model.recording;

import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.model.game.GameResult;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;

/**
 *
 * @author wahid
 */
public record GameRecordData(GameResult result, InGamePlayer winner, Difficulty difficulty) {}
