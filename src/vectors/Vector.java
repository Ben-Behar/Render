package vectors;

public class Vector  // Vector is a standard array which represents ordinates in n-d space where n is value.length
{
	double value[];
	
	public Vector(int dim)
	{
		value = new double[dim];
	}
	
	public Vector(double[] d)
	{
		value = new double[d.length];
		for(int i = 0 ; i < d.length ; i++)
			value[i] = d[i];
	}
	
	public Vector(Vector tmp)
	{
		value = new double[tmp.value.length];
		for(int i = 0 ; i < tmp.value.length ; i++)
			value[i] = tmp.value[i];
	}
	
	public Vector plus(Vector v)
	{
		Vector tmp = new Vector(v);
		for(int i = 0; i < tmp.value.length; i++)
			tmp.value[i] = tmp.value[i]+this.value[i];
		return tmp;
	}
	
	public Vector minus(Vector v)
	{
		Vector tmp = new Vector(v);
		for(int i = 0; i < tmp.value.length; i++)
			tmp.value[i] = this.value[i]-tmp.value[i];
		return tmp;
	}
	
	public Vector times(Vector v)
	{
		Vector tmp = new Vector(v);
		for(int i = 0; i < tmp.value.length; i++)
			tmp.value[i] = this.value[i]*tmp.value[i];
		return tmp;
	}
	
	public Vector scale(double d)
	{
		Vector tmp = new Vector(this);
		for(int i = 0; i < tmp.value.length; i++)
			tmp.value[i] = this.value[i]*d;
		return tmp;
	}
	
	public Vector by(Vector v)
	{
		Vector tmp = new Vector(v);
		for(int i = 0; i < tmp.value.length; i++)
			tmp.value[i] = this.value[i]/tmp.value[i];
		return tmp;
	}
	
	@Override
	public String toString()
	{
		String s = "Vector :";
		for(double d : value)
			s += ""+d+"|\t";
		return s;
	}




	public static class Geometry
	{
		
		public static Vector proj(Vector on, Vector toBeProjected)
		{ // DONE
		// on (on*toBeProjected/on*on)	
			return on.scale(comp(on,toBeProjected));
		}
		
		public static double comp(Vector on, Vector toBeProjected)
		{ // Done
			return  (Geometry.dotProduct(on, toBeProjected))/(Geometry.dotProduct(on, on));
		}
		
		public static Vector[] getOrthagonalSet(Vector[] v)
		{
			// UNTESTED : Gramm Schmidt process
			// v1 = x1
			// v2 = x2 - proj(v1,x2)
			// v3 = x3 - proj(v1,x3) - proj(v2,x3)
			// ....
			
			int dimensions = v[0].value.length;
			Vector[] ans = new Vector[Math.min(v.length, dimensions)];
			for(int i = 0 ; i < ans.length; i++)
			{
				ans[i] = new Vector(v[i]);
				for(int j = 0; j < i; j++)
					ans[i] = ans[i].minus(proj(ans[j],v[i]));
			}
			return ans;
			
		}
		
		public static double[] getHyperPlaneEquation(Vector[] v)
		{
			// v = a set of vectors that should be representing a plane
			// returns [0] + [1]x + [2]y + ... = 0 
			double[] equation = new double[v[0].value.length+1];
			Vector cp = crossProduct(v);
			for(int i = 0 ; i < cp.value.length; i++)
				equation[i] = cp.value[i];
			equation[equation.length-1] = dotProduct(cp,v[0]);
			return equation;
		}
		
		public static double[] getHyperPlaneEquation(Vector[] v, int dimension)
		{
			// TODO: add a system to go up to a certain dimensional hyper plane regardless of the dimensions of the input Vectors
			return null;
		}
		
		public static Vector crossProduct(Vector[] v)
		{//COMPLETED
			/* |i   j   k   l  ...|
			 * |ax  ay  az  aw ...|
			 * |bx  by  bz  bw ...|
			 * |..  ..  ..  .. ...| */
			int width = v[0].value.length;
			double[] answer = new double[width];
			double[][] tmp = new double[v.length][width-1];
			for(int i = 0 ; i < width; i++)
			{
				for(int y = 0; y < tmp.length;y++) // Fill tmp for each minor of the main matrix
					for(int x = 0; x < tmp[0].length;x++)
						if(x<i)
							tmp[y][x] = v[y].value[x];
						else if(x>=i)
							tmp[y][x] = v[y].value[x+1];
				if(i%2 == 0) // include each determinanted minor
					answer[i] =  Matrix.det(tmp);
				else
					answer[i] = - Matrix.det(tmp);
				//System.out.println(Matrix.toString(tmp));
			}
			return new Vector(answer);
		}

		public static double dotProduct(Vector v1, Vector v2)
		{ // DONE
			double sum = 0;
			for(int i = 0 ; i < v1.value.length ; i++)
				sum += v1.value[i]*v2.value[i];
			return sum;
		}
		
	}
}
