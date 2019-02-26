package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Form extends JFrame implements Runnable {

    public static final int w = 400;
    public static final int h = 400;

    public static final Color BG = new Color(20, 55, 75, 255);
    public static final int NODE_RADIUS = 5;

    public static final int nodeCount = 200;
    public static final int maxDist = 100;
    public static final int maxDist2 = maxDist * maxDist;
    public static final float SPEED = 1f;
    public static final int BORDER = 30;

    public static final int fw = w / maxDist + 1;
    public static final int fh = h / maxDist + 1;

    public static Field[][] fields = new Field[fw][fh];
    public static int frame = 0;

    public static BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    public static final float[][] COUPLING = {
//            {	1,	-1,	-1,	1,	1,	1,	-1,	1	},
//            {	-1,	1,	-1,	1,	1,	1,	1,	1	},
//            {	-1,	-1,	1,	-1,	1,	1,	-1,	1	},
//            {	1,	1,	1,	-1,	1,	1,	1,	1	},
//            {	1,	1,	1,	1,	-1,	-1,	-1,	1	},
//            {	1,	1,	-1,	1,	1,	-1,	-1,	1	},
//            {	-1,	1,	-1,	1,	1,	1,	-1,	1	},
//            {	1,	1,	1,	1,	-1,	1,	1,	-1	}
            {1, -1, -1},
            {1, -1, -1},
            {1, 1, -1}
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
            Particle p = new Particle((int)(Math.random() * COUPLING.length), (float)(Math.random() * w), (float)(Math.random() * h));
            fields[(int)(p.x / Form.maxDist)][(int)(p.y / Form.maxDist)].particles.add(p);
        }
        this.setSize(w + 16, h + 38);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(50, 50);
        this.add(new JLabel(new ImageIcon(img)));
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
        logic();
        Graphics2D g2 = img.createGraphics();
        try {
            File outputFile = new File("img/m3/T" + frame + ".png");
            ImageIO.write(img, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            double angle = Math.atan2(a.y - b.y, a.x - b.x);
            if(d2 < 1) d2 = 1;
            float dA = COUPLING[a.type][b.type] / d2;
            float dB = COUPLING[b.type][a.type] / d2;
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