package com.mycompany.xtremeo.client.model.viewmodel;

import com.mycompany.xtremeo.client.model.game.GameHistoryEntry;
import com.mycompany.xtremeo.client.model.game.Move;
import com.mycompany.xtremeo.client.model.recording.TicTacToeGameReplayer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;


public class GameReplayDriver {

    private final GameViewModel viewModel;
    private final TicTacToeGameReplayer replayer;
    private Timeline autoPlayTimeline;
    private final BooleanProperty isPlaying = new SimpleBooleanProperty(false);

    public GameReplayDriver(GameViewModel viewModel, GameHistoryEntry history) {
        this.viewModel = viewModel;
        this.replayer = new TicTacToeGameReplayer(history);
    }

    public void stepForward() {
        if (replayer.hasNextMove()) {
            Move move = replayer.nextMove();
            viewModel.makeMove(move);
            
            if (!replayer.hasNextMove()) {
                stopAutoPlay();
            }
        }
    }

    public void restart() {
        stopAutoPlay();
        replayer.reset();
        viewModel.resetBoard();
    }

    public void startAutoPlay() {
        if (!replayer.hasNextMove()) {
            restart();
        }

        isPlaying.set(true);
        autoPlayTimeline = new Timeline(new KeyFrame(Duration.millis(800), e -> {
            if (replayer.hasNextMove()) {
                stepForward();
            } else {
                stopAutoPlay();
            }
        }));
        autoPlayTimeline.setCycleCount(Timeline.INDEFINITE);
        autoPlayTimeline.play();
    }

    public void stopAutoPlay() {
        isPlaying.set(false);
        if (autoPlayTimeline != null) {
            autoPlayTimeline.stop();
            autoPlayTimeline = null;
        }
    }

    public void toggleAutoPlay() {
        if (isPlaying.get()) {
            stopAutoPlay();
        } else {
            startAutoPlay();
        }
    }

    public boolean hasNext() {
        return replayer.hasNextMove();
    }

    public BooleanProperty isPlayingProperty() {
        return isPlaying;
    }
}

