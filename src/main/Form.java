package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Form extends JFrame implements Runnable {

    private final int w = 1600;
    private final int h = 1200;

    private final Color BG = new Color(20, 55, 75, 255);
    private final Color LINK = new Color(255, 230, 0, 100);

    private final int NODE_RADIUS = 5;
    private final int NODE_COUNT = 800;
    private final int MAX_DIST = 100;
    private final int MAX_DIST2 = MAX_DIST * MAX_DIST;
    private final float SPEED = 4f;
    private final int SKIP_FRAMES = 3;
    private final int BORDER = 30;

    private final int fw = w / MAX_DIST + 1;
    private final int fh = h / MAX_DIST + 1;

    private final ArrayList<Link> links = new ArrayList<>();
    private final float LINK_FORCE = -0.015f;
    private int frame = 0;

    private BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    // array for dividing scene into parts to reduce complexity
    private final Field[][] fields = new Field[fw][fh];

    private static final float[][] COUPLING = {
            {1, 1, -1},
            {1, 1, 1},
            {1, 1, 1}
    };

    private static int[] LINKS = {
            1,
            3,
            2
    };

    private static final float[][] LINKS_POSSIBLE = {
            {0, 1, 1},
            {1, 2, 1},
            {1, 1, 2}
    };

    public Form() {
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                fields[i][j] = new Field();
            }
        }
        // put particles randomly
        for (int i = 0; i < NODE_COUNT; i++) {
            add((int)(Math.random() * COUPLING.length), (float)(Math.random() * w), (float)(Math.random() * h));
        }

        this.setSize(w + 16, h + 38);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(50, 50);
        this.add(new JLabel(new ImageIcon(img)));
    }

    private Particle add(int type, float x, float y) {
        Particle p = new Particle(ParticleType.values()[type], x, y);
        fields[(int) (p.x / MAX_DIST)][(int) (p.y / MAX_DIST)].particles.add(p);
        return p;
    }

    @Override
    public void run() {
        while(true) {
            this.repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        drawScene(img);
        for (int i = 0; i < SKIP_FRAMES; i++) logic();
        ((Graphics2D)g).drawImage(img, null, 8, 30);
        frame++;
    }

    private void drawScene(BufferedImage image) {
        Graphics2D g2 = image.createGraphics();
        g2.setColor(BG);
        g2.fillRect(0, 0, w, h);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                Field field = fields[i][j];
                for (int i1 = 0; i1 < field.particles.size(); i1++) {
                    Particle a = field.particles.get(i1);
                    g2.setColor(a.getColor());
                    g2.fillOval((int) a.x - NODE_RADIUS, (int) a.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
                }
            }
        }
        g2.setColor(LINK);
        for (Link link: links) {
            g2.drawLine((int) link.a.x, (int) link.a.y, (int) link.b.x, (int) link.b.y);
        }
    }

    private void logic() {
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                Field field = fields[i][j];
                for (int i1 = 0; i1 < field.particles.size(); i1++) {
                    Particle a = field.particles.get(i1);
                    a.x += a.sx;
                    a.y += a.sy;
                    a.sx *= 0.98f;
                    a.sy *= 0.98f;
                    // velocity normalization
                    // idk if it is still necessary
                    float magnitude = (float)Math.sqrt(a.sx * a.sx + a.sy * a.sy);
                    if(magnitude > 1f) {
                        a.sx /= magnitude;
                        a.sy /= magnitude;
                    }
                    // border repulsion
                    if(a.x < BORDER) {
                        a.sx += SPEED * 0.05f;
                        if(a.x < 0) {
                            a.x = -a.x;
                            a.sx *= -0.5f;
                        }
                    }
                    else if(a.x > w - BORDER) {
                        a.sx -= SPEED * 0.05f;
                        if(a.x > w) {
                            a.x = w * 2 - a.x;
                            a.sx *= -0.5f;
                        }
                    }
                    if(a.y < BORDER) {
                        a.sy += SPEED * 0.05f;
                        if(a.y < 0) {
                            a.y = -a.y;
                            a.sy *= -0.5f;
                        }
                    }
                    else if(a.y > h - BORDER) {
                        a.sy -= SPEED * 0.05f;
                        if(a.y > h) {
                            a.y = h * 2 - a.y;
                            a.sy *= -0.5f;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < links.size(); i++) {
            Link link = links.get(i);
            Particle a = link.a;
            Particle b = link.b;
            float d2 = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
            if(d2 > MAX_DIST2 / 4) {
                a.links--;
                b.links--;
                a.bonds.remove(b);
                b.bonds.remove(a);
                links.remove(link);
                i--;
            }
            else {
                if(d2 > NODE_RADIUS * NODE_RADIUS * 4) {
                    double angle = Math.atan2(a.y - b.y, a.x - b.x);
                    a.sx += (float)Math.cos(angle) * LINK_FORCE * SPEED;
                    a.sy += (float)Math.sin(angle) * LINK_FORCE * SPEED;
                    b.sx -= (float)Math.cos(angle) * LINK_FORCE * SPEED;
                    b.sy -= (float)Math.sin(angle) * LINK_FORCE * SPEED;
                }
            }
        }
        // moving particle to another field
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                Field field = fields[i][j];
                for (int i1 = 0; i1 < field.particles.size(); i1++) {
                    Particle a = field.particles.get(i1);
                    if(((int)(a.x / MAX_DIST) != i) || ((int)(a.y / MAX_DIST) != j)) {
                        field.particles.remove(a);
                        fields[(int)(a.x / MAX_DIST)][(int)(a.y / MAX_DIST)].particles.add(a);
                    }
                }
            }
        }
        // dividing scene into parts to reduce complexity
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                Field field = fields[i][j];
                for (int i1 = 0; i1 < field.particles.size(); i1++) {
                    Particle a = field.particles.get(i1);
                    Particle particleToLink = null;
                    float particleToLinkMinDist2 = (w + h) * (w + h);
                    for (int j1 = i1 + 1; j1 < field.particles.size(); j1++) {
                        Particle b = field.particles.get(j1);
                        float d2 = applyForce(a, b);
                        if(d2 != -1 && d2 < particleToLinkMinDist2) {
                            particleToLinkMinDist2 = d2;
                            particleToLink = b;
                        }
                    }
                    if(i < fw - 1) {
                        int iNext = i + 1;
                        Field field1 = fields[iNext][j];
                        for (int j1 = 0; j1 < field1.particles.size(); j1++) {
                            Particle b = field1.particles.get(j1);
                            float d2 = applyForce(a, b);
                            if(d2 != -1 && d2 < particleToLinkMinDist2) {
                                particleToLinkMinDist2 = d2;
                                particleToLink = b;
                            }
                        }
                    }
                    if(j < fh - 1) {
                        int jNext = j + 1;
                        Field field1 = fields[i][jNext];
                        for (int j1 = 0; j1 < field1.particles.size(); j1++) {
                            Particle b = field1.particles.get(j1);
                            float d2 = applyForce(a, b);
                            if(d2 != -1 && d2 < particleToLinkMinDist2) {
                                particleToLinkMinDist2 = d2;
                                particleToLink = b;
                            }
                        }
                        if(i < fw - 1) {
                            int iNext = i + 1;
                            Field field2 = fields[iNext][jNext];
                            for (int j1 = 0; j1 < field2.particles.size(); j1++) {
                                Particle b = field2.particles.get(j1);
                                float d2 = applyForce(a, b);
                                if(d2 != -1 && d2 < particleToLinkMinDist2) {
                                    particleToLinkMinDist2 = d2;
                                    particleToLink = b;
                                }
                            }
                        }
                    }
                    if(particleToLink != null) {
                        a.bonds.add(particleToLink);
                        particleToLink.bonds.add(a);
                        a.links++;
                        particleToLink.links++;
                        links.add(new Link(a, particleToLink));
                    }
                }
            }
        }
    }

    private float applyForce(Particle a, Particle b) {
        float d2 = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
        boolean canLink = false;
        if(d2 < MAX_DIST2) {
            float dA = COUPLING[a.getType()][b.getType()] / d2;
            float dB = COUPLING[b.getType()][a.getType()] / d2;
            if (a.links < LINKS[a.getType()] && b.links < LINKS[b.getType()]) {
                if(d2 < MAX_DIST2 / 4) {
                    if (!a.bonds.contains(b) && !b.bonds.contains(a)) {
                        int typeCountA = 0;
                        for (Particle p : a.bonds) {
                            if (p.getType() == b.getType()) typeCountA++;
                        }
                        int typeCountB = 0;
                        for (Particle p : b.bonds) {
                            if (p.getType() == a.getType()) typeCountB++;
                        }
                        if (typeCountA < LINKS_POSSIBLE[a.getType()][b.getType()] && typeCountB < LINKS_POSSIBLE[b.getType()][a.getType()]) {
                            canLink = true;
                        }
                    }
                }
            }
            else {
                if (!a.bonds.contains(b) && !b.bonds.contains(a)) {
                    dA = 1 / d2;
                    dB = 1 / d2;
                }
            }
            double angle = Math.atan2(a.y - b.y, a.x - b.x);
            if(d2 < 1) d2 = 1;
            if(d2 < NODE_RADIUS * NODE_RADIUS * 4) {
                dA = 1 / d2;
                dB = 1 / d2;
            }
            a.sx += (float)Math.cos(angle) * dA * SPEED;
            a.sy += (float)Math.sin(angle) * dA * SPEED;
            b.sx -= (float)Math.cos(angle) * dB * SPEED;
            b.sy -= (float)Math.sin(angle) * dB * SPEED;
        }
        if(canLink) return d2;
        return -1;
    }

}