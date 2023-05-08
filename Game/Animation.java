//Images
import java.awt.Image;

//Arraylist
import java.util.ArrayList;


public class Animation {
    //Timer
    private long animTime;
    private long startTime;
    private long totalDuration;

    //Animation
    private ArrayList<AnimFrame> frames;
    private int currFrameIndex;
    private boolean loop;
    private boolean isActive;


    public Animation (boolean loop) {
        frames = new ArrayList<AnimFrame>();
        totalDuration = 0;
	    this.loop = loop;
	    isActive = false;
    }

    //Add frmaes to Animation
    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    //Starts Animation
    public synchronized void start() {
	    isActive = true;
        animTime = 0;						// reset time animation has run for to zero
        currFrameIndex = 0;					// reset current frame to first frame
	    startTime = System.currentTimeMillis();			// reset start time to current time
    }


    //Updates Animation Frame
    public synchronized void update() {
	    if (!isActive) return;

        //Initialises Timer for Animation
            //Find the current time
        long currTime = System.currentTimeMillis();
            //Find how much time has elapsed since last update
	    long elapsedTime = currTime - startTime;		
        	//Set start time to current time
	    startTime = currTime;
        

        if (frames.size() > 1) {
            //Add elapsed time to amount of time animation has run for
            animTime += elapsedTime;				
            
            //If the time animation has run for > total duration
            if (animTime >= totalDuration) {			
		        if (loop) {
                    //Reset time animation has run for
                    animTime = animTime % totalDuration;

                    //Reset current frame to first frame
                    currFrameIndex = 0;
		        }
		        else { 
                    //Set to false to terminate animation
	                isActive = false;
		        }
            }

	        if (!isActive) {
	            return;
            }

            while (animTime > getFrame(currFrameIndex).endTime) {
                //Set frame corresponding to time animation has run for
                currFrameIndex++;
            }
        }
        
        //END update()
    }

    //Terminates this animation.
    public synchronized void stop() {
	    isActive = true;

        //END stop()
    }

    //Gets this Animation's current image. Returns null if this animation has no images.
    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        }
        else {
            return getFrame(currFrameIndex).image;
        }

        //END getImage()
    }

    //Returns number of frams in animation
    public int getNumFrames() {
	    return frames.size();

        //END getNumFrames()
    }

    //Returns requested frame in the collection
    private AnimFrame getFrame(int i) {
        return frames.get(i);
    }

    //Returns boolean values is animation is active
    public boolean isStillActive() {
	    return isActive;
    }

    //Inner class for the frames of the animation
    private class AnimFrame {

        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }

}

/*
	STUDENT NAME:	Nikosi Lessey
	STUDENT ID:		816027711
	COURSE TITLE:	Game Programming
	COURSE CODE:	COMP 3609

	ASSIGNMENT #1
*/
