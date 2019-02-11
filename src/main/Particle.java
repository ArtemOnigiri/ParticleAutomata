package main;

public class Particle {

    public int type;
    public float x;
    public float y;
    public float sx;
    public float sy;
    public float neighbors;

    public Particle(int type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.sx = 0;
        this.sy = 0;
        this.neighbors = 0;
    }

}