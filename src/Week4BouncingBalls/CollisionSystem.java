package Week4BouncingBalls;

import java.awt.Color;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

public class CollisionSystem {
	private static final double HZ = 0.5; 

	private MinPQ<Event> pq;
	private double t = 0.0;
	private Particle[] particles;
	
    public CollisionSystem(Particle[] particles) {
        this.particles = particles.clone();  
    }
	
	private void predict(Particle a, double limit) {
		// Add all the possible hits for particle a
		int N = particles.length;
		if(a == null) return;
		for(int i = 0; i < N; i++) {
			double dt = a.timeToHit(particles[i]);
			pq.insert(new Event(t + dt, a, particles[i]));
		}
		
        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtX <= limit) pq.insert(new Event(t + dtX, a, null));
        if (t + dtY <= limit) pq.insert(new Event(t + dtY, null, a));
	}
	
	private void redraw(double limit) {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show();
        StdDraw.pause(20);
        if (t < limit) {
            pq.insert(new Event(t + 1.0 / HZ, null, null));
        }
	}
	
	public void simulate(double limit) {
		pq = new MinPQ<Event>();
		// add all the predictions to all the particles
		for(int i = 0; i < particles.length; i++) {
			predict(particles[i], limit);
		}
		//add a last one without any
		pq.insert(new Event(0, null, null));
		
		while(!pq.isEmpty()) {
			// get the soonest hit
			Event e = pq.delMin();
			if(!e.isValid())
				continue;
			Particle a = e.a;
			Particle b = e.b;
			// Move the particles to the time of that hit
			for(int i = 0; i < particles.length; i++)
				particles[i].move(e.time - t);
			t = e.time;
			
			// compute what happens after that hit
			if      (a != null && b != null) a.bounceOff(b);              
            else if (a != null && b == null) a.bounceOffVerticalWall();   
            else if (a == null && b != null) b.bounceOffHorizontalWall(); 
            else if (a == null && b == null) redraw(limit); 
			
			// create new predictions for the particles involved
	        predict(a, limit);
            predict(b, limit);
		}
	}
	
	 public static void main(String[] args) {

	        StdDraw.setCanvasSize(600, 600);

	        // enable double buffering
	        StdDraw.enableDoubleBuffering();

	        // the array of particles
	        Particle[] particles;

	        // create n random particles
	        if (args.length == 1) {
	            int n = Integer.parseInt(args[0]);
	            particles = new Particle[n];
	            for (int i = 0; i < n; i++)
	                particles[i] = new Particle();
	        }

	        // or read from standard input
	        else {
	            int n = StdIn.readInt();
	            particles = new Particle[n];
	            for (int i = 0; i < n; i++) {
	                double rx     = StdIn.readDouble();
	                double ry     = StdIn.readDouble();
	                double vx     = StdIn.readDouble();
	                double vy     = StdIn.readDouble();
	                double radius = StdIn.readDouble();
	                double mass   = StdIn.readDouble();
	                int r         = StdIn.readInt();
	                int g         = StdIn.readInt();
	                int b         = StdIn.readInt();
	                Color color   = new Color(r, g, b);
	                particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
	            }
	        }

	        // create collision system and simulate
	        CollisionSystem system = new CollisionSystem(particles);
	        system.simulate(10000);
	    }
	
}
