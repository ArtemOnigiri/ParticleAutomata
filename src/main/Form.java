package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Form {

    public static final int w = 800;
    public static final int h = 800;

    public static final Color BG = new Color(30, 50, 100, 255);
    public static final int NODE_RADIUS = 5;

    public static final int nodeCount = 500;
    public static final int maxDist = 200;
    public static final int maxDist2 = maxDist * maxDist;
    public static final float SPEED = 1f;
    public static final int BORDER = 30;

    public static final int fw = w / maxDist + 1;
    public static final int fh = h / maxDist + 1;

    public static Field[][] fields = new Field[fw][fh];

    public static BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    public static final int size = 2;

    public static boolean check(int a, Set<Integer> groups, int c) {
        if(groups.contains(a)) return false;
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                int s = swap(a, i, j);
                if(groups.contains(s)) return false;
                if(c > 0) {
                    if(!check(s, groups, c - 1)) return false;
                }
            }
        }
        return true;
    }

    public static int swap(int a, int i, int j) {
        int[][] g = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if((a & (1 << (x + y * size))) == 0) g[x][y] = 0;
                else g[x][y] = 1;
            }
        }
        for (int x = 0; x < size; x++) {
            int temp = g[x][i];
            g[x][i] = g[x][j];
            g[x][j] = temp;
        }
        for (int y = 0; y < size; y++) {
            int temp = g[i][y];
            g[i][y] = g[j][y];
            g[j][y] = temp;
        }
        int b = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if(g[x][y] == 1) b |= (1 << (x + y * size));
            }
        }
        return b;
    }

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
            Particle p = new Particle((int)(Math.random() * size), (float)(Math.random() * w), (float)(Math.random() * h));
            fields[(int)(p.x / Form.maxDist)][(int)(p.y / Form.maxDist)].particles.add(p);
        }
        Set<Integer> groups = new HashSet<>();
        for (int i = 0; i < (1 << (size * size)); i++) {
            if(check(i, groups, 1)) groups.add(i);
        }
        for (Integer g: groups) {
            int[][] rules = new int[size][size];
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    if((g & (1 << (x * size + y))) == 0) rules[x][y] = -1;
                    else rules[x][y] = 1;
                }
            }
            for (int i = 0; i < 3000; i++) {
                logic(rules);
            }
            drawScene(img);
            try {
                File outputFile = new File("img2/img" + g + ".png");
                ImageIO.write(img, "png", outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void logic(int[][] rules) {
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
                        applyForce(a, b, rules);
                    }
                    if(i < fw - 1) {
                        int iNext = i + 1;
                        Field field1 = fields[iNext][j];
                        for (int j1 = 0; j1 < field1.particles.size(); j1++) {
                            Particle b = field1.particles.get(j1);
                            applyForce(a, b, rules);
                        }
                    }
                    if(j < fh - 1) {
                        int jNext = j + 1;
                        Field field1 = fields[i][jNext];
                        for (int j1 = 0; j1 < field1.particles.size(); j1++) {
                            Particle b = field1.particles.get(j1);
                            applyForce(a, b, rules);
                        }
                        if(i < fw - 1) {
                            int iNext = i + 1;
                            Field field2 = fields[iNext][jNext];
                            for (int j1 = 0; j1 < field2.particles.size(); j1++) {
                                Particle b = field2.particles.get(j1);
                                applyForce(a, b, rules);
                            }
                        }
                    }
                }
            }
        }
    }

    public void applyForce(Particle a, Particle b, int[][] rules) {
        float d2 = (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
        if(d2 < maxDist2) {
            double angle = Math.atan2(a.y - b.y, a.x - b.x);
            if(d2 < 1) d2 = 1;
            float dA = rules[a.type][b.type] / d2;
            float dB = rules[b.type][a.type] / d2;
            if(d2 < NODE_RADIUS * NODE_RADIUS * 4) {
                if(dA < 0) dA = 1 / d2;
                if(dB < 0) dB = 1 / d2;
            }
            a.sx += (float)Math.cos(angle) * dA * SPEED;
            a.sy += (float)Math.sin(angle) * dA * SPEED;
            b.sx -= (float)Math.cos(angle) * dB * SPEED;
            b.sy -= (float)Math.sin(angle) * dB * SPEED;
        }
    }

}