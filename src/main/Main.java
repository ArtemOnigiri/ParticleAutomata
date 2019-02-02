package main;

public class Main {

    public static void main(String[] args) {
        Form f = new Form();
        new Thread(f).start();
    }
}