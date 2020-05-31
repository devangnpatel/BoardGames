package checkers.warped;

/**
 * Implements an eye-candied style animation of a Checkers Board
 * to dazzle the eyes and imagination!
 * @author devang
 */
public class Animator implements Runnable {
    Thread animator;
    CheckersWarpedBoard board;
    CheckersWarper warper;

    public Animator(CheckersWarpedBoard board, CheckersWarper warper)
    {
        this.board = board;
        this.warper = warper;
    }
    
    public void start()
    {
        if (animator == null)
        {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void stop()
    {
        if (animator != null)
        {
            animator.stop();
            animator = null;
        }
    }

    public void run()
    {
        while (true)
        {
            board.repaint();
            warper.update();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }
    }
}
