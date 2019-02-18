package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Form extends JFrame implements Runnable {

    public static final int w = 1600;
    public static final int h = 1200;

    public static final Color BG = new Color(20, 55, 75, 255);
    public static final Color LINK = new Color(255, 230, 0, 200);
    public static final int NODE_RADIUS = 5;

    public static final int nodeCount = 800;
    public static final int maxDist = 100;
    public static final int maxDist2 = maxDist * maxDist;
    public static final float SPEED = 4f;
    public static final int BORDER = 30;

    public static final int fw = w / maxDist + 1;
    public static final int fh = h / maxDist + 1;

    public static Field[][] fields = new Field[fw][fh];
    public static int frame = 0;

    public static BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    public static final float[][] COUPLING = {
            {1, 1, -1},
            {1, 1, 1},
            {1, 1, 1}
    };

    public static int[] LINKS = {
            1,
            3,
            2
    };

    public static final float[][] LINKS_POSSIBLE = {
            {0, 1, 1},
            {1, 2, 1},
            {1, 1, 2}
    };

    public static final Color[] COLORS = {
            new Color(250, 20, 20),
            new Color(200, 140, 100),
            new Color(80, 170, 140),
            new Color(0, 150, 230),
            new Color(10, 20, 230),
            new Color(130, 0, 200),
            new Color(250, 150, 210),
            new Color(130, 130, 130)
    };

    public Form() {
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                fields[i][j] = new Field();
            }
        }
        for (int i = 0; i < nodeCount; i++) {
            add((int)(Math.random() * 2), (float)(Math.random() * w), (float)(Math.random() * h));
        }

        // Spinner
//        addToCenter(2, -10, 0);
//        addToCenter(2, 10, 0);
//        addToCenter(0, 20, -5);
//        addToCenter(0, -20, 5);
        // Glider
//        addToCenter(2, 5, 0);
//        addToCenter(0, -5, 5);

//        for (int k = 0; k < 30; k++) {
//            float x = (float)(Math.random() * (w - 200)) + 100;
//            float y = (float)(Math.random() * (h - 200)) + 100;
//            for (int i = 0; i < (int)(Math.random() * 8) + 2; i++) {
//                add(2, x + 20 - i * 10, y);
//            }
//            add(0, x + 30, y + 5);
//        }

//        addToCenter(2, -20, 0);
//        addToCenter(2, -10, 10);
//        addToCenter(2, 0, 20);
//        addToCenter(2, 10, 10);
//        addToCenter(2, 20, 0);
//        addToCenter(2, -10, -30);
//        addToCenter(2, 0, -30);
//        addToCenter(2, 10, -30);
//        addToCenter(1, -10, -20);
//        addToCenter(1, 0, -20);
//        addToCenter(1, 10, -20);
//        addToCenter(0, 0, -40);

        this.setSize(w + 16, h + 38);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(50, 50);
        this.add(new JLabel(new ImageIcon(img)));
    }

    public static Particle addToCenter(int type, float x, float y) {
        Particle p = add(type, x + w / 2, y + h / 2);
        return p;
    }

    public static Particle add(int type, float x, float y) {
        Particle p = new Particle(type, x, y);
        fields[(int) (p.x / Form.maxDist)][(int) (p.y / Form.maxDist)].particles.add(p);
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
//        long time = System.currentTimeMillis();
        drawScene(img);
        for (int i = 0; i < 3; i++) logic();
//        try {
//            File outputFile = new File("links1/img" + frame + ".png");
//            ImageIO.write(img, "png", outputFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Graphics2D g2 = img.createGraphics();
//        g2.setColor(Color.RED);
//        long frameDif = System.currentTimeMillis() - time;
//        if(frameDif == 0) frameDif = 1;
//        long fps = 1000 / frameDif;
//        g2.drawString(fps + "", 100, 100);
        ((Graphics2D)g).drawImage(img, null, 8, 30);
        frame++;
    }

    public void drawScene(BufferedImage image) {
        Graphics2D g2 = image.createGraphics();
        g2.setColor(BG);
        g2.fillRect(0, 0, w, h);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                Field field = fields[i][j];
                for (int i1 = 0; i1 < field.particles.size(); i1++) {
                    Particle a = field.particles.get(i1);
                    g2.setColor(COLORS[a.type]);
                    g2.fillOval((int) a.x - NODE_RADIUS, (int) a.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
//                    g2.setColor(LINK);
//                    for (Particle b: a.bonds) {
//                        g2.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
//                    }
                }
            }
        }
    }

    public void logic() {
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                Field field = fields[i][j];
                for (int i1 = 0; i1 < field.particles.size(); i1++) {
                    Particle a = field.particles.get(i1);
                    a.x += a.sx;
                    a.y += a.sy;
                    a.sx *= 0.98f;
                    a.sy *= 0.98f;
                    float magnitude = (float)Math.sqrt(a.sx * a.sx + a.sy * a.sy);
                    if(magnitude > 1f) {
                        a.sx /= magnitude;
                        a.sy /= magnitude;
                    }
                    if(a.x < BORDER) {
                        a.sx += SPEED * 0.005f;
                        if(a.x < 0) {
                            a.x = -a.x;
                            a.sx *= -0.5f;
                        }
                    }
                    else if(a.x > w - BORDER) {
                        a.sx -= SPEED * 0.005f;
                        if(a.x > w) {
                            a.x = w * 2 - a.x;
                            a.sx *= -0.5f;
                        }
                    }
                    if(a.y < BORDER) {
                        a.sy += SPEED * 0.005f;
                        if(a.y < 0) {
                            a.y = -a.y;
                            a.sy *= -0.5f;
                        }
                    }
                    else if(a.y > h - BORDER) {
                        a.sy -= SPEED * 0.005f;
                        if(a.y > h) {
                            a.y = h * 2 - a.y;
                            a.sy *= -0.5f;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                Field field = fields[i][j];
                for (int i1 = 0; i1 < field.particles.size(); i1++) {
                    Particle a = field.particles.get(i1);
                    if(((int)(a.x / Form.maxDist) != i) || ((int)(a.y / Form.maxDist) != j)) {
                        field.particles.remove(a);
                        Form.fields[(int)(a.x / Form.maxDist)][(int)(a.y / Form.maxDist)].particles.add(a);
                    }
                    for (Iterator<Particle> iterator = a.bonds.iterator(); iterator.hasNext();) {
                        Particle b = iterator.next();
                        float d2 = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
                        if(d2 > maxDist2) {
                            a.links--;
                            b.links--;
                            iterator.remove();
                        }
                        else {
//                            a.sx = a.sx * 0.999f + b.sx * 0.001f;
//                            a.sy = a.sy * 0.999f + b.sy * 0.001f;
                            if(d2 > NODE_RADIUS * NODE_RADIUS * 4) {
                                double angle = Math.atan2(a.y - b.y, a.x - b.x);
                                float dA = -0.02f;
                                a.sx += (float)Math.cos(angle) * dA * SPEED;
                                a.sy += (float)Math.sin(angle) * dA * SPEED;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                Field field = fields[i][j];
                for (int i1 = 0; i1 < field.particles.size(); i1++) {
                    Particle a = field.particles.get(i1);
                    for (int j1 = i1 + 1; j1 < field.particles.size(); j1++) {
                        Particle b = field.particles.get(j1);
                        applyForce(a, b);
                    }
                    if(i < fw - 1) {
                        int iNext = i + 1;
                        Field field1 = fields[iNext][j];
                        for (int j1 = 0; j1 < field1.particles.size(); j1++) {
                            Particle b = field1.particles.get(j1);
                            applyForce(a, b);
                        }
                    }
                    if(j < fh - 1) {
                        int jNext = j + 1;
                        Field field1 = fields[i][jNext];
                        for (int j1 = 0; j1 < field1.particles.size(); j1++) {
                            Particle b = field1.particles.get(j1);
                            applyForce(a, b);
                        }
                        if(i < fw - 1) {
                            int iNext = i + 1;
                            Field field2 = fields[iNext][jNext];
                            for (int j1 = 0; j1 < field2.particles.size(); j1++) {
                                Particle b = field2.particles.get(j1);
                                applyForce(a, b);
                            }
                        }
                    }
                }
            }
        }
    }

    public void applyForce(Particle a, Particle b) {
        float d2 = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
        if(d2 < maxDist2) {
            float dA = COUPLING[a.type][b.type] / d2;
            float dB = COUPLING[b.type][a.type] / d2;
            if (a.links < LINKS[a.type] && b.links < LINKS[b.type]) {
                if(d2 < maxDist2) {
                    if (!a.bonds.contains(b) && !b.bonds.contains(a)) {
                        int typeCountA = 0;
                        for (Particle p : a.bonds) {
                            if(p.type == b.type) typeCountA++;
                        }
                        int typeCountB = 0;
                        for (Particle p : b.bonds) {
                            if(p.type == a.type) typeCountB++;
                        }
                        if(typeCountA < LINKS_POSSIBLE[a.type][b.type] && typeCountB < LINKS_POSSIBLE[b.type][a.type]) {
                            a.bonds.add(b);
                            b.bonds.add(a);
                            a.links++;
                            b.links++;
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
    }

}