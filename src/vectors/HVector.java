package vectors;



public class HVector extends Vector
{
	//double value[];
	double h = 1;
	
	public HVector(double[] d)
	{
		super(d);
		this.h = 1;
	}
	
	public HVector(Vector v)
	{
		super(v);
		this.h=1;
	}
	
	public HVector(HVector tmp)
	{
		super(tmp);
		this.h = tmp.h;
	}

	public void normalize()
	{
		for(int i = 0 ; i < this.value.length ; i++)
			this.value[i] = this.value[i]/this.h;
		this.h = 1;
	}
	
	public void transform(double [][] mat)
	{
		double [][] tmp = Matrix.multiply(mat, this.toMatrix());
		for(int i = 0 ; i < mat.length-1 ; i++)
			this.value[i] = tmp[i][0];
	}
	
	
	// Representation Functions
	public double[] toArray()
	{
		double[] tmp = new double[value.length+1];
		for(int i = 0 ; i < value.length ; i++)
			tmp[i] = this.value[i];
		tmp[value.length] = h;
		return tmp;
	}
	
	public double[][] toMatrix()
	{
		double[][] tmp = new double[value.length+1][1];
		for(int i = 0 ; i < value.length ; i++)
			tmp[i][0] = this.value[i];
		tmp[value.length][0] = h;
		return tmp;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "H="+h;
	}

	

	
}
