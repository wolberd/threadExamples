/** Run the following program multiple times. The program asks five threads to add
 *   one to the score, which should result in a score of 5. Without synchronized data
 *   the result will sometimes be less than 5. Run the program a number of times to see this.
 *   then see if you can figure out where to add the "synchronized" keyword.
 */

public class ScoreKeeper {
    private static int score=0;  // this is the shared data

    int numWorkers=5;
    ArrayWorker workers[] = new ArrayWorker[numWorkers];

    /** playGames creates the workers, starts them off, then joins back
     * together to get final result
     *
     * @throws InterruptedException
     */
    public void playGames() throws InterruptedException {
        // create and start the worker threads
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new ArrayWorker(i);
            workers[i].start();
        }

        for (ArrayWorker worker : workers) {
            worker.join();

        }
        System.out.println("score at end:"+score);
    }

    /** incrementScore just adds one to score and prints out
     *  which worker is doing it. It also sleeps for a random amount
     *  of time to add some randomness to our execution
     * @param id
     */
    public static void incrementScore(int id){
        System.out.println("worker"+id+ " entering synchronized and score is:"+score);
        sleep();
        score++;
        sleep();
        System.out.println("worker"+id+ " exiting synchronized and score is:"+score);
    }

    /**
     * sleep for a random amount of time
     */
    public static void sleep() {
        int millis=(int)Math.random()*1000;
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

    /**
     * simple thread class that just increments the shared variable
     * We wend in an id
     */
    private static class ArrayWorker extends Thread {
        int id;
        public ArrayWorker(int id) {
            this.id=id;
        }
        @Override
        public void run() {
            incrementScore(id);
        }
    }

    public static void main(String args[]) throws InterruptedException{
        ScoreKeeper scoreKeeper = new ScoreKeeper();
        scoreKeeper.playGames();

    }
}
