package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Form extends JFrame implements Runnable {

    public static final int w = 800;
    public static final int h = 800;

    public static final Color BG = new Color(30, 50, 100, 255);
    public static final int NODE_RADIUS = 5;

    public static final int nodeCount = 500;
    public static final int maxDist = 50;
    public static final int maxDist2 = maxDist * maxDist;
    public static final float SPEED = 4f;

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
            {-1, 1, 1},
            {-1, -1, -1},
            {1, 1, -1}
    };

    public static final Color[] COLORS = {
            new Color(250, 0, 30),
            new Color(200, 150, 0),
            new Color(30, 180, 70),
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
        long time = System.currentTimeMillis();
        drawScene(img);
        logic();
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.RED);
        long frameDif = System.currentTimeMillis() - time;
        if(frameDif == 0) frameDif = 1;
        long fps = 1000 / frameDif;
        g2.drawString(fps + "", 100, 100);
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
                    float magnitude = (float)Math.sqrt(a.tx * a.tx + a.ty * a.ty);
                    double angle = Math.atan2(a.ty, a.tx);
                    a.sx += (float)Math.cos(angle) * magnitude * SPEED;
                    a.sy += (float)Math.sin(angle) * magnitude * SPEED;
//                    float magnitudeMax = (float)Math.sqrt(a.sx * a.sx + a.sy * a.sy);
//                    if(magnitudeMax > 1f) {
//                        a.sx /= magnitudeMax;
//                        a.sy /= magnitudeMax;
//                    }
                    a.x += a.sx;
                    a.y += a.sy;
                    float slip = a.slip;
                    if(slip > 1) slip = 1;
                    a.sx *= slip;
                    a.sy *= slip;
                    a.tx = 0;
                    a.ty = 0;
                    a.slip = 1f;
                }
            }
        }
        for (int i = 0; i < fw; i++) {
            for (int j = 0; j < fh; j++) {
                Field field = fields[i][j];
                for (int i1 = 0; i1 < field.particles.size(); i1++) {
                    Particle a = field.particles.get(i1);
                    a.x = ((a.x % w) + w) % w;
                    a.y = ((a.y % h) + h) % h;
                    if(((int)(a.x / Form.maxDist) != i) || ((int)(a.y / Form.maxDist) != j)) {
                        field.particles.remove(a);
                        int x = (int)(a.x / Form.maxDist);
                        int y = (int)(a.y / Form.maxDist);
                        Form.fields[x][y].particles.add(a);
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
                        applyForce(a, b, false, false);
                    }
                    int iNext = (i + 1) % fw;
                    Field field1 = fields[iNext][j];
                    for (int j1 = 0; j1 < field1.particles.size(); j1++) {
                        Particle b = field1.particles.get(j1);
                        applyForce(a, b, iNext == 0, false);
                    }
                    int jNext = (j + 1) % fh;
                    Field field2 = fields[i][jNext];
                    for (int j1 = 0; j1 < field2.particles.size(); j1++) {
                        Particle b = field2.particles.get(j1);
                        applyForce(a, b, false, jNext == 0);
                    }
                    Field field3 = fields[iNext][jNext];
                    for (int j1 = 0; j1 < field3.particles.size(); j1++) {
                        Particle b = field3.particles.get(j1);
                        applyForce(a, b, iNext == 0, jNext == 0);
                    }
                }
            }
        }
    }

    public void applyForce(Particle a, Particle b, boolean bxPlus, boolean byPlus) {
        float bx = b.x;
        float by = b.y;
        if(bxPlus) bx += w;
        if(byPlus) by += h;
        float d2 = (a.x - bx) * (a.x - bx) + (a.y - by) * (a.y - by);
        if(d2 < maxDist2) {
            double angle = Math.atan2(a.y - by, a.x - bx);
            if(d2 < 1) d2 = 1;
            float dA = COUPLING[a.type][b.type] / d2;
            float dB = COUPLING[b.type][a.type] / d2;
            if(d2 < NODE_RADIUS * NODE_RADIUS * 4) {
                dA = 1 / d2;
                dB = 1 / d2;
            }
            a.tx += (float)Math.cos(angle) * dA;
            a.ty += (float)Math.sin(angle) * dA;
            b.tx -= (float)Math.cos(angle) * dB;
            b.ty -= (float)Math.sin(angle) * dB;
            a.slip *= (1 - (1 / d2));
            b.slip *= (1 - (1 / d2));
        }
    }

}