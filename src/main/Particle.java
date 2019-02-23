package main;

import java.util.HashSet;
import java.util.Set;

public class Particle {

    public int type;
    public float x;
    public float y;
    public float sx;
    public float sy;
    public int links;
    public Set<Particle> bonds;

    public Particle(int type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.sx = 0;
        this.sy = 0;
        this.links = 0;
        this.bonds = new HashSet<>();
    }

    public Particle linkTo(Particle b) {
        this.bonds.add(b);
        b.bonds.add(this);
        this.links++;
        b.links++;
        Form.links.add(new Link(this, b));
        return this;
    }

}