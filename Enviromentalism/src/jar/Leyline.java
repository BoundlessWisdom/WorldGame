package jar;

import math.VectorMath;

public class Leyline implements Updateable {
	
	//Consider leyline sight mode: Fades out, leylines visible as surface-to-bottom walls.
	
	SustainedCast cast;
	ECastType type;
	private int power;
	
	public final int midx; //Calculation basepoint coordinates.
	public final int midz;
	
	private float leftx;
	private float rightx;
	public final float zslope;
	public final int slopeSign;
	
	public final boolean vertical;
	private float upperz = 0;
	private float lowerz = 0;
	
	public Leyline(SustainedCast cast, ECastType type, int x1, int z1, int x2, int z2) {
		this.cast = cast;
		this.type = type;
		this.midx = x1;
		this.midz = z1;
		
		boolean LtoR;
		
		if (x1 < x2) {
			leftx = x1;
			rightx = x2;
			LtoR = true;
			zslope = (z2 - z1)/(x2 - x1);
			vertical = false;
		} else if (x1 == x2) {
			leftx = x1;
			rightx = x1;
			LtoR = false;
			zslope = 0;
			vertical = true;
			if (z1 < z2) {
				lowerz = z1;
				upperz = z2;
			} else {
				lowerz = z2;
				upperz = z1;
			}
		} else {
			leftx = x2;
			rightx = x2;
			LtoR = false;
			zslope = (z2 - z1)/(x2 - x1);
			vertical = false;
		}
		
		if (leftx == rightx || zslope == 0) {
			slopeSign = 0;
			upperz = z1;
			lowerz = z1;
		} else if (zslope > 0) {
			slopeSign = 1;
			if (LtoR) {
				upperz = z2;
				lowerz = z1;
			} else {
				upperz = z1;
				lowerz = z2;
			}
		} else {
			slopeSign = -1;
			if (LtoR) {
				upperz = z1;
				lowerz = z2;
			} else {
				upperz = z2;
				lowerz = z1;
			}
		}
	}
	
	public float powerReceived(Location l) {
		float distanceFromLeyline;
		
		if (vertical) {
			if (l.z > upperz)
				distanceFromLeyline = VectorMath.abs(l.x - leftx, l.z - upperz);
			else if (l.z < lowerz)
				distanceFromLeyline = VectorMath.abs(l.x - leftx, lowerz - l.z);
			else
				distanceFromLeyline = Math.abs(l.x - leftx);
		} else if (zslope == 0) {
			if (l.x > rightx)
				distanceFromLeyline = VectorMath.abs(l.x - rightx, l.z - midz);
			else if (l.x < leftx)
				distanceFromLeyline = VectorMath.abs(leftx - l.x, l.z - midz);
			else
				distanceFromLeyline = Math.abs(l.z - midz);
		} else if (zslope > 0) {
			if (l.z > upperz + (l.x - rightx) * (-1 / zslope))
				distanceFromLeyline = VectorMath.abs(l.x - rightx, l.z - upperz);
			else if (l.z < lowerz + (l.x - leftx) * (-1 / zslope))
				distanceFromLeyline = VectorMath.abs(leftx - l.x, lowerz - l.z);
			else
				distanceFromLeyline = VectorMath.cProduct2D(1, zslope, l.x - midx, l.z - midz) / VectorMath.abs(1, zslope);
		} else {
			if (l.z > upperz + (l.x - leftx) * (-1 / zslope))
				distanceFromLeyline = VectorMath.abs(leftx - l.x, l.z - upperz);
			else if (l.z < lowerz + (l.x - rightx) * (-1 / zslope))
				distanceFromLeyline = VectorMath.abs(l.x - rightx, lowerz - l.z);
			else
				distanceFromLeyline = VectorMath.cProduct2D(1, zslope, l.x - midx, l.z - midz) / VectorMath.abs(1, zslope);
		}
		
		if (distanceFromLeyline >= 1.0001)
			return 0;
		
		distanceFromLeyline -= 0.5f;
		if (distanceFromLeyline < 0)
			distanceFromLeyline = 0;
		
		return power * (1 - distanceFromLeyline);
	}
	
	private void grow(long growTime) {
		
	}
	
	public void update(long dtime) {
		grow(dtime);
	}
}
