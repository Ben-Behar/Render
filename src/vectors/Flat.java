package vectors;

public class Flat // its a smaller space of a bigger space : a line or point in 2d; a plane, line, point in 4d
{
	// Flat Dimensions by name
	public static final int point = 0;
	public static final int line  = 1;
	public static final int plane = 2;
	
	
	public double[][] data;
	public final boolean independent;
	
	public Flat(Flat flat)
	{
		this.independent = flat.independent;
		this.data = Matrix.copy(flat.data);
	}
	
	public Flat(int totalDimension, int flatDimension)
	{
		this.independent = false;
		if(totalDimension > flatDimension)
			this.data = new double[totalDimension - flatDimension][totalDimension+1];
		else if(totalDimension == flatDimension)
			System.out.println("ERROR : this flat would end up representing the whole space");
		else
			System.out.println("ERROR : Flat cannot have more dimensions than the space");
	}
	
	// 
	
	/*   matrix of NxM | n = dimensions of flat , m = number of coordinates + 1 // for affine units
	 *   a system of k equations describes a flat of dimension n-k | n = total dimensions ; k = number of equations
	 * 
	 *   so any flat is defined as a Matrix A times a Column Vector = a Column Vector
	 *   flat dim=-1: ?
	 *   flat dim=0 : point
	 *   flat dim=1 : line
	 *   flat dim=2 : plane
	 *   flat dim=3 : space
	 *   flat dim=4 : space-time
	 *   
	 *   
	 *   
	 *   this defines a point (M-N - 1 = 0):
	 *   [1 0 0 x][r1]   [0]
	 *   [0 1 0 y][r2] = [0]
	 *   [0 0 1 z][r3]   [0]
	 *            [-U]
	 *   	
	 *   this defines a plane (M-N - 1 = 2):
	 *   [1 1 1 0][r1]
	 *            [r2]
	 *            [r3]
	 *            [U]
	 * 
	 */
	
	
	// include basic piece wise +-/*
	
	
	
	
	public int getDimension()
	{
		return this.data[0].length - this.data.length - 1;
	}
	
	public int getMaxDimension()
	{
		return this.data[0].length;
	}
	
	
	
	public boolean isPoint()
	{
		return (this.data.length == this.data[0].length);
	}
	
	public boolean isLine()
	{
		return (this.data.length == this.data[0].length-1);
	}
	
}
