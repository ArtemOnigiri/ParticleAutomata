package main;

public class Particle {

    public int type;
    public float x;
    public float y;
    public float sx;
    public float sy;
    public float tx;
    public float ty;
    public float slip;

    public Particle(int type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.sx = 0;
        this.sy = 0;
        this.tx = 0;
        this.ty = 0;
        this.slip = 1;
    }

}