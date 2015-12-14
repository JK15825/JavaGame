import java.awt.*;


public class Logic extends Canvas implements Runnable
{
    private Thread t;
    private  boolean gameRunning;
    private int lastFpsTime;
    private int fps;
    public Logic()
    {
        gameRunning = true;
    }

    @Override
    public void run()
    {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        beforeTime = System.nanoTime(); // calculates the time before we start
        // rendering and updating the game state
        running=true;
        while(running)
        {
            display();
            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;
            if (sleepTime > 0) // is there some time left in this cycle
            {
                try
                {
                    sleep(sleepTime/1000000L); // convert nano -> ms
                }
                catch(InterruptedException ex){}
                overSleepTime =
                        (System.nanoTime() - afterTime) - sleepTime; // how much did
                // we oversleep?
            }
            else // go to here if sleepTime <= 0; took longer than the allotted
            // time to render and update the game
            {
                excess -= sleepTime; // store excess time value
                overSleepTime = 0L;
                if (++noDelays >= NO_DELAYS_PER_YIELD)
                {

                    yield(); // give another thread a chance to run so we dont
                    // take up all the cpu cycles

                    noDelays = 0;
                }

            }
            beforeTime = System.nanoTime();// calculates the time before we start
            // rendering and updating the game state

      /*
       * If frame animation is taking too long, update the game state without
       * rendering it, to get the updates/sec nearer to the required FPS.
       */

            int skips = 0;

            if(renderer.inGame)
            {
                while((excess > period) && (skips < MAX_FRAME_SKIPS))
                {
                    excess -= period;
                    renderer.update(); // update game state but don't render the scene
                    skips++;
                }
            }
            else
            {
                excess=0;
            }

        }
    }

    public void start()
    {
        t = new Thread(this);
        t.start();
    }
    public void tick()
    {

    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
    }

}
