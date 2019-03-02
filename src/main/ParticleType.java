package main;

import java.awt.*;

public enum ParticleType {

    RED(1, new Color(250, 20, 20)),
    YELLOW(2, new Color(200, 140, 100)),
    BLUE(3, new Color(80, 170, 140));

    public final int type;
    public final Color color;

    private ParticleType(int type, Color color) {
        this.type = type;
        this.color = color;
    }

}