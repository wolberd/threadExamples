/** class to show simple use of wait and notify to go back and
 * forth in a job
 *
 */
public class WaitNotifyExample {

    // shared data is an integer
    int result=0;

    public synchronized void incrementResult(int amount) {
        result=result+amount;
    }

    public int getResult(){
        return result;
    }

    public static void main(String args[]){

        WaitNotifyExample main= new WaitNotifyExample();

        Thread w1 = new Thread(new Worker1(main));
        Thread w2 = new Thread(new Worker2(main));
        w1.start();
        w2.start();
        System.out.println("Main done");

    }

    public static void sleep() {
        int millis=(int)Math.random()*1000;
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

    /**
     * our workers in this example implement Runnable
     * They both do some work, then notify other so it can
     * do its work
     */
    public static class Worker1 implements Runnable {
        WaitNotifyExample data;
        public Worker1(WaitNotifyExample data) {
            this.data=data;
        }
        public void run() {
            synchronized(data) {
                sleep();
                // do first part of job
                try {
                    for (int i = 0; i < 1000; i++) {
                        data.incrementResult(i);
                    }
                    System.out.println("worker 1 completed first part, result=" + data.getResult());
                    data.notify();
                    while (data.getResult()!=0)
                        data.wait();

                    // do second part of job
                    for (int i = 0; i < 500; i++) {
                        data.incrementResult(i);
                    }
                    System.out.println("worker 1 completed second part, result=" + data.getResult());
                    data.notify();

                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static class Worker2 implements Runnable {
        WaitNotifyExample data;
        public Worker2(WaitNotifyExample data) {
            this.data=data;
        }

        public void run() {
            synchronized (data) {
                try {


                    sleep();
                    // do first part of job

                    for (int i = 0; i < 1000; i++) {
                        data.incrementResult(-i);
                    }
                    System.out.println("worker 2 completed first part, result=" + data.getResult());
                    data.notify();
                    while (data.getResult()<120000)
                        data.wait();

                    // do second part of job
                    for (int i = 0; i < 500; i++) {
                        data.incrementResult(-i);
                    }
                    System.out.println("worker 2 completed second part, result=" + data.getResult());
                    data.notify();
                    data.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
