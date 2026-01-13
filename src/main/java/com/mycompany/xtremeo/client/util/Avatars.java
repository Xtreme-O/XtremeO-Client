package com.mycompany.xtremeo.client.util;


public class Avatars {

    private static final String BASE_PATH = "/com/mycompany/xtremeo/client/images/avatars/";

    public static final String[] URLS = {
            getUrl("X_O_Strategist.png"),
            getUrl("TicTacMaster.png"),
            getUrl("SarahConnor.png"),
            getUrl("PixelMaster.png"),
            getUrl("NovicePlayer.png"),
            getUrl("NeonPlayerOne.png"),
            getUrl("GridLock.png"),
            getUrl("Glitch_01.png"),
            getUrl("DragonSlayer99.png"),
            getUrl("CyberKing.png"),
            getUrl("BusyBee.png")
    };

    private static String getUrl(String filename) {
        return BASE_PATH + filename;
    }
}
