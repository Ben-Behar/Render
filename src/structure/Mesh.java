package structure;

import java.util.List;

import vectors.Flat;
import vectors.Vector;

public class Mesh // Mesh represents a wire frame type structure of a shape.
{
	Vector[][] structure;
	
	private Mesh(Vector[][] structure)
	{
		this.structure = structure;
	}
	
	public List<Flat> toFlats()
	{
		// TODO
		
		
		return null;
	}
	
	public Vector[] toShapes()
	{
		// TODO
		
		return null;
	}
	
	
	
}
