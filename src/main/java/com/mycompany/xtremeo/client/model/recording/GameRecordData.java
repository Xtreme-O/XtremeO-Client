package com.mycompany.xtremeo.client.model.recording;

import com.mycompany.xtremeo.client.ai.Difficulty;
import com.mycompany.xtremeo.client.model.game.GameResult;
import com.mycompany.xtremeo.client.model.game.InGamePlayer;

public record GameRecordData(GameResult result, InGamePlayer winner, Difficulty difficulty) {}

