package physics;

import vectors.Vector;

public class Impulse
{
	final double start;
	final double length;
	final double end; 
	Vector force; // force per unit of time
	
	public Impulse(Vector force, double start, double length)
	{
		this.force = force;
		this.start = start;
		this.length = length;
		this.end = start+length;
	}
	
	
	/* combine : separate this impulse and that impulse into discrete and separate impulses
	 * 
	 * 
	 * return Impulse[3] : 	[0] = the first occurring Impulse 
	 * 						[1] = the sum of the overlap
	 * 						[2] = the last occurring Impulse
	 * 
	 * !Warnings: possibility of overlap due to doubles being equal without proper cases
	 */
	public Impulse[] combine(Impulse that)
	{
		Vector sum = null;
		Impulse[] results = new Impulse[3];
		if(this.end < that.start) // no overlap & this happens first
		{
			results[0] = this;
			results[1] = null;
			results[2] = that;
		}	
		else if(this.start > that.end) // no overlap & that happens first 
		{
			results[0] = that;
			results[1] = null;
			results[2] = this;
		}
		else if(this.start < that.start) // overlap & this starts first
		{	// cut from this.start to that.start
			results[0] = new Impulse(this.force, this.start, this.end - that.start);
			sum = this.force.plus(that.force);
			if(this.end < that.end) // that trails
			{	
				results[1] = new Impulse(sum, that.start, this.end - that.start);
				results[2] = new Impulse(that.force, this.end, that.end-this.end);
			}
			else if(that.end < this.end)// this trails
			{
				results[1] = new Impulse(sum, that.start, that.end - that.start);
				results[2] = new Impulse(this.force, that.end, this.end-that.end);
			}
		}
		else if(that.start < this.start) // overlap & that starts first
		{
			results[0] = new Impulse(that.force, that.start, that.end - this.start);
			sum = this.force.plus(that.force);
			if(that.end < this.end) // this trails		
			{
				results[1] = new Impulse(sum, this.start, that.end - this.start);
				results[2] = new Impulse(this.force, that.end, this.end-that.end);
			}
			else if(this.end < that.end) // that trails
			{
				results[1] = new Impulse(sum, this.start, this.end - this.start);
				results[2] = new Impulse(that.force, this.end, that.end-this.end);
			}
		}
		else
			System.err.println("IMPULSE::COMBINE::equal start or end points!!");
		return results;
	}
	
	
	
	
	/* getMassVector : return a fractional amount of the delta position based on how much force was exerted by time 'time'
	 * 
	 * return : 'Vector' of delta postion 
	 * UNITS : Acceleration*Mass*time = mv
	 * 
	 */
	public Vector getMassVelocity(double time)
	{		
		if(time < this.start)
			return new Vector(3);
		if(time > this.end)
			return this.force.scale(length);
		return this.force.scale(time-start);
	}
	
	
	
	
	
	
	
	
	
}
