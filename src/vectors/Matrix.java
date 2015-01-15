package vectors;





public class Matrix 
{
	public static double[][] copy(double[][] original)
	{
		//COMPLETED+TESTED
		double[][] copy = new double[original.length][original[0].length];
		for(int y = 0; y < copy.length; y++)
			for(int x = 0; x < copy[0].length;x++)
				copy[y][x] = original[y][x];
		return copy;
	}
	
	public static double[][] createIdentity(int i)
	{
		//COMPLETED+TESTED
		double[][] tmp = new double[i][i];
		for(int a = 0; a < tmp.length; a++)
			for(int b = 0; b < tmp[a].length; b++)
				tmp[a][b] = ( a==b ? 1 : 0);  // if diag, 1 else 0
		return tmp;
	}	

	public static double[][] createProjectionMatrix(int flatNormAxis,double d)
	{
		
		// sets the homogeneous value to last 
		/*     Projection
		 *	{{1,0, 0 ,0},
		 *	 {0,1, 0 ,0},
		 *	 {0,0, 0 ,0},
		 *	 {0,0,1/d,0};
		 */	
		
		
		double[][] pm = createIdentity(4);
		pm[2][2] = 0; 
		pm[3][2] = 1/d;  
		pm[3][3] = 0;
		return pm;
	}
	
	public static double[][] createRotationMatrix(int a1, int a2, int dim, double theta)
	{
		// totally ignores the right hand rule.
		
		double [][] tmp = createIdentity(dim);
		final double cT = Math.cos(theta);
		final double sT = Math.sin(theta);
		
		tmp[a1][a1] = cT;
		tmp[a2][a2] = cT;
		tmp[a1][a2] = -sT;
		tmp[a2][a1] = sT;
		
		
		return tmp;
	}
	
	
	
	public static double[][] createScalingMatrix(double d, int dim)
	{
		//COMPLETED+TESTED
		double[][] tm = createIdentity(dim);
		for(int a = 0; a < tm.length-1; a++)
			tm[a][a] = d;
		return tm;
	}
		
	public static double[][] createTranslationMatrix(double[] vector)
	{
		// creates a homogeneous translation matrix of n-dimension
		// COMPLETED+TESTED
		double[][] tm = createIdentity(vector.length + 1);
		for(int i = 0 ; i < vector.length; i ++)
			tm[i][vector.length] = vector[i];
		return tm;
	}
	

	
	public static double multiply(double[][] m1 , double[][] m2, int i, int j )
	{
		double sum = 0;
		for(int k = 0 ; k < m2.length ; k++)
			sum += m1[i][k] * m2[k][j];
		return sum;
	}
	
	public static double[][] multiply(double[][] m1, double[][] m2)
	{
		if(m1[0].length == m2.length)
		{
			double[][] matrix = new double[m1.length][m2[0].length];
			for(int y = 0; y < matrix.length; y++)
				for(int x = 0; x < matrix[0].length; x++)
					matrix[y][x] = multiply(m1,m2,y,x);
			return matrix;		
		}
		else
		{
			System.err.println("Matrix: Cannot Multiply Matrix : \n" + toString(m1)+ "\n by \n" + toString(m2));
			return null;
		}
	}
	
	
	
	public static double det(double[][] m)
	{
		// RECURSIVE : DIVIDE AND CONQUER
		if(m.length != m[0].length)// double check if possible
		{
			System.out.println("Determinant taken of a non square matrix");
			return Double.NaN;
		}
		if(m.length == 2) // BAAAAAASE CASE
			return m[0][0]*m[1][1] - m[0][1]*m[1][0];
		
		double[][] tmp = new double[m.length-1][m[0].length-1];
		double total = 0; 
		for(int i = 0 ; i < m[0].length; i++)
		{
			for(int y = 0; y < tmp.length;y++) // Fill tmp for each minor of the main matrix
				for(int x = 0; x < tmp[0].length;x++)
					if(x<i)
						tmp[y][x] = m[y+1][x];
					else if(x>=i)
						tmp[y][x] = m[y+1][x+1];
			
			if(i%2 == 0) // include each determinanted minor
				total += m[0][i]*det(tmp);
			else
				total -= m[0][i]*det(tmp);
			//System.out.println(Matrix.toString(tmp));
		}
		return total;
	}

	public static double[][] toMatrix(double[] array)
	{
		double[][] tmp = new double[array.length][1];
		for(int i = 0 ; i < array.length ; i++)
			tmp[i][0] = array[i];
		return tmp;
	}

	public static String toString(double[][] matrix)
	{
		if(matrix == null)
			return "Matrix is null";
		String s= "";
		for(int y = 0; y < matrix.length; y++)
		{
			for(int x = 0; x < matrix[0].length; x++)
				s += matrix[y][x] + "\t";
			s+= "\n";
		}
		return s;
	}
}
