package Week4BouncingBalls;

public class Event implements Comparable<Event> {
	protected double time;
	protected Particle a, b;
	protected int countA, countB;
	
    public Event(double t, Particle a, Particle b) {
        this.time = t;
        this.a    = a;
        this.b    = b;
        if (a != null) countA = a.count();
        else           countA = -1;
        if (b != null) countB = b.count();
        else           countB = -1;
    }
	
    public int compareTo(Event that) {
        return Double.compare(this.time, that.time);
    }
	
    public boolean isValid() {
    	// If there were hits in between with these particles
    	// this event is invalid
        if (a != null && a.count() != countA) return false;
        if (b != null && b.count() != countB) return false;
        return true;
    }

}
