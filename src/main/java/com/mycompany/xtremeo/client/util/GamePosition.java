package com.mycompany.xtremeo.client.util;


public class GamePosition {

    public static String getPositionName(int row, int col) {
        if (row == 1 && col == 1) return "Center";

        String vertical = switch (row) {
            case 0 -> "Top";
            case 1 -> "Middle";
            case 2 -> "Bottom";
            default -> "";
        };

        String horizontal = switch (col) {
            case 0 -> "Left";
            case 1 -> "Center";
            case 2 -> "Right";
            default -> "";
        };

        return vertical + " " + horizontal;
    }
}