package math;

public class VectorMath {
	public static float cProduct2D(float ... input) {
		if (input.length != 4)
			throw new IllegalArgumentException();
		
		return input[1] * input[4] - input[2] * input[3];
	}
	
	public static float abs(float ... input) {
		int rms = 0;
		for (int i = 0; i < input.length; i++)
			rms += Math.pow(input[i], 2);
		
		return (float) Math.sqrt(rms);
	}
}
