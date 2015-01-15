package physics;

import java.util.ArrayList;

import vectors.Vector;

public class Physics
{
	class INode
	{
		Impulse impulse;
		INode next;
		
		public INode(Impulse impulse, INode next)
		{
			this.impulse = impulse;
			this.next = next;
		}
		
		
		public void sort(Impulse i) // Sorts each node by start time
		{
			if(i.start < impulse.start) // comes before
			{
				this.next = new INode(this.impulse,this.next); // make next = a clone
				this.impulse = i; // place impulse in current node
			}
			else if (i.start > impulse.start) // comes after
			{
				if(next != null)
					next.sort(i);
				else
					this.next = new INode(i , null);
			}
		}

		public void handle(Impulse i)// sorts and seperates conflicting notes by start time and end time
		{
			if(i.start >= impulse.end) // no over lap // completely after
			{
				if(next != null)
					next.handle(i);
				else
					this.next = new INode(i , null);
			}
			else 
			{
				Impulse[] trio = this.impulse.combine(i);
				
				this.impulse = trio[0];
				if(trio[1] != null)
				{
					this.next = new INode(trio[1],this.next);
				}
				
				this.next.handle(trio[2]); // handle any more possible conflicts 
				
				
				
			}
			
		}

		
		
	}
	
	INode node;
	double mass;
	ArrayList<Impulse> impulses = new ArrayList<Impulse>();
	
	// velocity cannot change without an impulse
	// a sudden change in velocity == high impulse over limit -> 0 time
	
	public Vector getPositionAtTime(final double time)  // DONE NOT TESTED
	{
		Vector lastPosition = new Vector(3);
		Vector lastVelocity = new Vector(3);
		double lastTime = 0;
		INode inode = node;
		while (inode != null)
		{
			Impulse i = inode.impulse;
		//for(Impulse i : impulses)//impulses)  // assume impulses are sorted
			if(i.start >= time) // if this impulse never occurred
			{
				lastPosition = lastPosition.plus(lastVelocity.scale(time - lastTime)); //add velocity to position then return
				return lastPosition;
			}
			else
			{
				double timeBefore = i.start - lastTime; // get time until impulse started
				double timeDuring = (time > i.end) ? i.length : (time - i.start); // get time after the start of the impulse but before the end
				
				// APPLY RESTING VELOCITY
				lastPosition = lastPosition.plus(lastVelocity.scale(timeBefore)); // apply the velocity that occured BEFORE impulse
				lastPosition = lastPosition.plus(lastVelocity.scale(timeDuring)); // apply the velocity that occured DURING impulse 
				
				// APPLY IMPULSE
				lastPosition = lastPosition.plus(i.force.scale(timeDuring * timeDuring / 2 * mass)); // apply the impulse that occured to position
				lastVelocity = lastVelocity.plus(i.force.scale(timeDuring));						 // apply the impulse that occured to velocity 
				lastTime = (time > i.end) ? i.end : time;
			}
			inode=inode.next;
		}
		if(lastTime < time) // if the resting velocity still had lingering effect
			lastPosition = lastPosition.plus(lastVelocity.scale(time - lastTime )); // apply the final resting velocity
		return lastPosition;
	}
	
	
	public Vector getVelocityAtTime(double time)  // DONE NOT TESTED
	{
		Vector lastVelocity = new Vector(3);
		INode inode = node;
		while (inode != null)
		{
			Impulse i = inode.impulse;
		//for(Impulse i : impulses)  // assume impulses are sorted
			{
				if(i.start >= time) // if this impulse never occurred
					return lastVelocity;
				else
					lastVelocity = lastVelocity.plus(i.force.scale((time > i.end) ? i.length : (time - i.start))); 
			}
			inode = inode.next;
		}
		return lastVelocity;
	}
	
	/* applyImpulse
	 * 
	 *  combines the new impulse where necessary into the previously held impulses
	 *  utilizing Impulse.combine
	 * 
	 * 
	 * 
	 *  We need a hashtable style linked list in which to splice the data that was affected by the new impulse
	 * 
	 * 
	 */
	public void applyImpulse(Impulse i)
	{
		this.node.handle(i);	
	}
	
	
	
	
	
	
	//TODO:: Create a wrapper for INode system for simplicity of iteration
	

	
	
}
