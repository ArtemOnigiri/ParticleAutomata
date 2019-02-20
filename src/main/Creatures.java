package main;

import java.util.ArrayList;

public class Creatures {

    public static void butterfly(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(0, x + 0.67755127f, y + -6.4160767f));
        p.add(new Particle(2, x + 8.379272f, y + -12.621063f));
        p.add(new Particle(1, x + 19.801025f, y + -12.714996f));
        p.add(new Particle(1, x + 28.154907f, y + -0.13604736f));
        p.add(new Particle(0, x + 39.273438f, y + 1.7105408f));
        p.get(3).linkTo(p.get(4));
        p.add(new Particle(2, x + 23.13739f, y + 9.4782715f));
        p.add(new Particle(0, x + 15.212463f, y + 15.427643f));
        p.get(5).linkTo(p.get(6));
        p.get(3).linkTo(p.get(5));
        p.get(2).linkTo(p.get(3));
        p.add(new Particle(0, x + 23.085144f, y + -23.442749f));
        p.get(2).linkTo(p.get(7));
        p.get(1).linkTo(p.get(2));
        p.get(0).linkTo(p.get(1));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void glider(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(0, x + 3.4256592f, y + 9.29364f));
        p.add(new Particle(2, x + -6.922119f, y + 9.5529175f));
        p.add(new Particle(2, x + -15.239746f, y + 4.130188f));
        p.add(new Particle(0, x + -15.307861f, y + -5.7981567f));
        p.get(2).linkTo(p.get(3));
        p.get(1).linkTo(p.get(2));
        p.get(0).linkTo(p.get(1));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void wings6(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(2, x + -15.662003f, y + -9.544083f));
        p.add(new Particle(2, x + -21.28006f, y + -0.82710266f));
        p.add(new Particle(2, x + -22.45308f, y + 9.5842285f));
        p.add(new Particle(2, x + -20.164368f, y + 19.67044f));
        p.add(new Particle(0, x + -13.734482f, y + 27.56247f));
        p.get(3).linkTo(p.get(4));
        p.get(2).linkTo(p.get(3));
        p.get(1).linkTo(p.get(2));
        p.get(0).linkTo(p.get(1));
        p.add(new Particle(2, x + -8.832855f, y + -17.770508f));
        p.add(new Particle(2, x + -0.6868439f, y + -23.88652f));
        p.add(new Particle(0, x + 8.910187f, y + -24.213486f));
        p.get(6).linkTo(p.get(7));
        p.get(5).linkTo(p.get(6));
        p.get(0).linkTo(p.get(5));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void worm(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(1, x + -0.35432434f, y + -6.645355f));
        p.add(new Particle(2, x + 13.336716f, y + -10.321838f));
        p.add(new Particle(2, x + 20.187576f, y + -20.063461f));
        p.add(new Particle(2, x + 17.011383f, y + -30.70211f));
        p.add(new Particle(2, x + 5.9610596f, y + -33.807884f));
        p.add(new Particle(2, x + -4.4270782f, y + -27.3415f));
        p.add(new Particle(1, x + -8.182968f, y + -13.74379f));
        p.add(new Particle(1, x + -10.633148f, y + -2.9914398f));
        p.add(new Particle(2, x + -18.218521f, y + 7.221405f));
        p.add(new Particle(2, x + -22.823898f, y + 16.742462f));
        p.add(new Particle(0, x + -19.482346f, y + 25.736359f));
        p.get(9).linkTo(p.get(10));
        p.get(8).linkTo(p.get(9));
        p.get(7).linkTo(p.get(8));
        p.get(6).linkTo(p.get(7));
        p.get(5).linkTo(p.get(6));
        p.get(4).linkTo(p.get(5));
        p.get(3).linkTo(p.get(4));
        p.get(2).linkTo(p.get(3));
        p.get(1).linkTo(p.get(2));
        p.get(0).linkTo(p.get(1));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void spinner(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(1, x + -2.2564278f, y + -1.0381317f));
        p.add(new Particle(0, x + -4.683899f, y + 10.622086f));
        p.get(0).linkTo(p.get(1));
        p.add(new Particle(2, x + 7.8232765f, y + -1.8396759f));
        p.add(new Particle(0, x + 17.610031f, y + 0.7831421f));
        p.get(2).linkTo(p.get(3));
        p.get(0).linkTo(p.get(2));
        p.add(new Particle(1, x + -4.146736f, y + -18.279877f));
        p.add(new Particle(0, x + -0.124183655f, y + -29.014702f));
        p.get(4).linkTo(p.get(5));
        p.add(new Particle(2, x + -15.706833f, y + -22.556328f));
        p.add(new Particle(0, x + -23.601456f, y + -15.44812f));
        p.get(6).linkTo(p.get(7));
        p.get(4).linkTo(p.get(6));
        p.get(0).linkTo(p.get(4));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void frog(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(1, x + -5.2854004f, y + 3.420288f));
        p.add(new Particle(1, x + -2.9604492f, y + -6.743469f));
        p.add(new Particle(0, x + 4.4005127f, y + -14.708862f));
        p.get(1).linkTo(p.get(2));
        p.add(new Particle(1, x + -12.751404f, y + -3.5060425f));
        p.add(new Particle(2, x + -24.458557f, y + -1.5215454f));
        p.add(new Particle(0, x + -32.954285f, y + 4.1069336f));
        p.get(4).linkTo(p.get(5));
        p.get(3).linkTo(p.get(4));
        p.get(1).linkTo(p.get(3));
        p.get(0).linkTo(p.get(1));
        p.add(new Particle(2, x + -6.720764f, y + 14.770813f));
        p.add(new Particle(0, x + -8.360107f, y + 25.30133f));
        p.get(6).linkTo(p.get(7));
        p.get(0).linkTo(p.get(6));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void frog2(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(1, x + -0.7071228f, y + 4.288666f));
        p.add(new Particle(1, x + 1.7802429f, y + -5.4966736f));
        p.add(new Particle(1, x + -8.880981f, y + -2.6932373f));
        p.add(new Particle(2, x + -21.051483f, y + -7.0329285f));
        p.add(new Particle(2, x + -29.611786f, y + -12.350891f));
        p.add(new Particle(0, x + -28.412231f, y + -22.671997f));
        p.get(4).linkTo(p.get(5));
        p.get(3).linkTo(p.get(4));
        p.get(2).linkTo(p.get(3));
        p.get(1).linkTo(p.get(2));
        p.add(new Particle(2, x + 10.537811f, y + -13.831146f));
        p.add(new Particle(2, x + 15.690979f, y + -24.26471f));
        p.add(new Particle(0, x + 9.102875f, y + -31.556854f));
        p.get(7).linkTo(p.get(8));
        p.get(6).linkTo(p.get(7));
        p.get(1).linkTo(p.get(6));
        p.get(0).linkTo(p.get(1));
        p.add(new Particle(0, x + 3.1302185f, y + 14.221802f));
        p.get(0).linkTo(p.get(9));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void crab(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(1, x + 2.1273193f, y + -6.1013794f));
        p.add(new Particle(1, x + 10.064087f, y + 7.4209595f));
        p.add(new Particle(2, x + 2.869629f, y + 14.929626f));
        p.add(new Particle(0, x + -6.5061035f, y + 17.544739f));
        p.get(2).linkTo(p.get(3));
        p.get(1).linkTo(p.get(2));
        p.add(new Particle(0, x + 19.34729f, y + 12.759338f));
        p.get(1).linkTo(p.get(4));
        p.get(0).linkTo(p.get(1));
        p.add(new Particle(0, x + -10.172485f, y + -6.9542236f));
        p.get(0).linkTo(p.get(5));
        p.add(new Particle(1, x + 10.433594f, y + -18.083374f));
        p.add(new Particle(0, x + 20.794434f, y + -20.776611f));
        p.get(6).linkTo(p.get(7));
        p.add(new Particle(2, x + 5.2000732f, y + -26.631714f));
        p.add(new Particle(0, x + -3.1547852f, y + -32.21057f));
        p.get(8).linkTo(p.get(9));
        p.get(6).linkTo(p.get(8));
        p.get(0).linkTo(p.get(6));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void tadpole(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(1, x + 0.5095215f, y + 7.486145f));
        p.add(new Particle(1, x + -10.234741f, y + -5.746643f));
        p.add(new Particle(0, x + -20.663086f, y + -10.059814f));
        p.get(1).linkTo(p.get(2));
        p.add(new Particle(2, x + -1.0959473f, y + -11.569397f));
        p.add(new Particle(2, x + 9.217529f, y + -7.7807617f));
        p.add(new Particle(1, x + 13.130737f, y + 3.2855225f));
        p.add(new Particle(0, x + 22.869507f, y + 8.947388f));
        p.get(5).linkTo(p.get(6));
        p.get(4).linkTo(p.get(5));
        p.get(3).linkTo(p.get(4));
        p.get(1).linkTo(p.get(3));
        p.get(0).linkTo(p.get(1));
        p.add(new Particle(2, x + 0.21228027f, y + 18.51471f));
        p.add(new Particle(0, x + -3.9768066f, y + 29.072876f));
        p.get(7).linkTo(p.get(8));
        p.get(0).linkTo(p.get(7));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void machaon(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(1, x + 2.0079346f, y + 3.4449158f));
        p.add(new Particle(2, x + 0.3355713f, y + 15.801483f));
        p.add(new Particle(0, x + -2.765808f, y + 25.944824f));
        p.get(1).linkTo(p.get(2));
        p.get(0).linkTo(p.get(1));
        p.add(new Particle(1, x + -10.093262f, y + -3.09552f));
        p.add(new Particle(2, x + -19.070862f, y + 4.9364014f));
        p.add(new Particle(0, x + -24.941467f, y + 13.430115f));
        p.get(4).linkTo(p.get(5));
        p.get(3).linkTo(p.get(4));
        p.add(new Particle(1, x + -9.943176f, y + -20.844208f));
        p.add(new Particle(2, x + 1.9337769f, y + -23.817352f));
        p.add(new Particle(2, x + 11.914856f, y + -18.219421f));
        p.add(new Particle(1, x + 15.974731f, y + -6.5224304f));
        p.add(new Particle(0, x + 26.964539f, y + -5.1257324f));
        p.get(9).linkTo(p.get(10));
        p.get(8).linkTo(p.get(9));
        p.get(7).linkTo(p.get(8));
        p.get(6).linkTo(p.get(7));
        p.add(new Particle(0, x + -17.229248f, y + -29.263062f));
        p.get(6).linkTo(p.get(11));
        p.get(3).linkTo(p.get(6));
        p.get(0).linkTo(p.get(3));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

    public static void sunfish(float x, float y) {
        ArrayList<Particle> p = new ArrayList<>();
        p.add(new Particle(1, x + 0.21984863f, y + -0.47740173f));
        p.add(new Particle(2, x + 7.194092f, y + -12.903809f));
        p.add(new Particle(2, x + 4.708496f, y + -23.94046f));
        p.add(new Particle(2, x + -5.7509766f, y + -26.516708f));
        p.add(new Particle(2, x + -13.94873f, y + -18.198792f));
        p.add(new Particle(1, x + -14.964355f, y + -4.238632f));
        p.add(new Particle(1, x + -27.770996f, y + 7.6631165f));
        p.add(new Particle(2, x + -38.939575f, y + 5.2273865f));
        p.add(new Particle(0, x + -48.839355f, y + 6.607727f));
        p.get(7).linkTo(p.get(8));
        p.get(6).linkTo(p.get(7));
        p.add(new Particle(0, x + -29.924683f, y + 18.703674f));
        p.get(6).linkTo(p.get(9));
        p.get(5).linkTo(p.get(6));
        p.get(4).linkTo(p.get(5));
        p.get(3).linkTo(p.get(4));
        p.get(2).linkTo(p.get(3));
        p.get(1).linkTo(p.get(2));
        p.get(0).linkTo(p.get(1));
        p.add(new Particle(1, x + 5.572754f, y + 15.479858f));
        p.add(new Particle(2, x + 15.387451f, y + 18.72931f));
        p.add(new Particle(0, x + 23.111694f, y + 25.235748f));
        p.get(11).linkTo(p.get(12));
        p.get(10).linkTo(p.get(11));
        p.add(new Particle(0, x + 0.83496094f, y + 25.49353f));
        p.get(10).linkTo(p.get(13));
        p.get(0).linkTo(p.get(10));
        for (Particle a : p) {
            Form.fields[(int) (a.x / Form.maxDist)][(int) (a.y / Form.maxDist)].particles.add(a);
        }
    }

}