package util;

public class TestInterrupt {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("OK");
            }
        });
        t.start();
        System.out.println("Interrupt");
        t.interrupt();
    }
}
