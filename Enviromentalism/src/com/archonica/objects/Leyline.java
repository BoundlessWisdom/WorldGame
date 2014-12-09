package com.archonica.objects;

import java.util.ArrayList;

import com.archonica.ECastType;
import com.archonica.Tile;
import com.archonica.Updateable;
import com.archonica.sparks.SparkLeyline;
import com.archonica.sparks.SustainedSpark;

import math.VectorMath;

public class Leyline implements Updateable {
	
	//Consider leyline sight mode: Fades out, leylines visible as surface-to-bottom walls.
	
	SparkLeyline cast;
	ECastType type;
	private int power;
	
	public final int midx; //Calculation basepoint coordinates.
	public final int midz;
	
	private float leftx;
	private float rightx;
	public final float zslope;
	public final int slopeSign;
	
	public final boolean vertical;
	private float upperz; 			//For two purposes: vertical leylines, and distance
	private float lowerz;			//calculations.
	
	public Leyline(SparkLeyline cast, ECastType type, int x1, int z1, int x2, int z2) {
		this.cast = cast;
		this.type = type;
		this.midx = x1;
		this.midz = z1;
		
		if (x1 < x2) {
			leftx = x1;
			rightx = x2;
			zslope = (z2 - z1)/(x2 - x1);
			vertical = false;
		} else if (x1 == x2) {
			leftx = x1;
			rightx = x1;
			zslope = 0;
			vertical = true;
			if (z1 < z2) {
				upperz = z2;
				lowerz = z1;
			} else {
				upperz = z1;
				lowerz = z2;
			}
		} else {
			leftx = x2;
			rightx = x2;
			zslope = (z2 - z1)/(x2 - x1);
			vertical = false;
		}
		
		if (leftx == rightx || zslope == 0)
			slopeSign = 0;
		else if (zslope > 0)
			slopeSign = 1;
		else
			slopeSign = -1;
	}
	
	public float powerReceived(Tile t) {
		float distanceFromLeyline;
		
		if (vertical) {
			if (t.z > upperz)
				distanceFromLeyline = VectorMath.abs(t.x - leftx, t.z - upperz);
			else if (t.z < lowerz)
				distanceFromLeyline = VectorMath.abs(t.x - leftx, lowerz - t.z);
			else
				distanceFromLeyline = Math.abs(t.x - leftx);
		} else if (zslope == 0) {
			if (t.x > rightx)
				distanceFromLeyline = VectorMath.abs(t.x - rightx, t.z - midz);
			else if (t.x < leftx)
				distanceFromLeyline = VectorMath.abs(leftx - t.x, t.z - midz);
			else
				distanceFromLeyline = Math.abs(t.z - midz);
		} else if (zslope > 0) {
			float upperz = midz + zslope * (rightx - midx);
			float lowerz = midz + zslope * (leftx - midx);
			if (t.z > upperz + (t.x - rightx) * (-1 / zslope))
				distanceFromLeyline = VectorMath.abs(t.x - rightx, t.z - upperz);
			else if (t.z < lowerz + (t.x - leftx) * (-1 / zslope))
				distanceFromLeyline = VectorMath.abs(leftx - t.x, lowerz - t.z);
			else
				distanceFromLeyline = VectorMath.cProduct2D(1, zslope, t.x - midx, t.z - midz) / VectorMath.abs(1, zslope);
		} else {
			float upperz = midz + zslope * (leftx - midx);
			float lowerz = midz + zslope * (rightx - midx);
			if (t.z > upperz + (t.x - leftx) * (-1 / zslope))
				distanceFromLeyline = VectorMath.abs(leftx - t.x, t.z - upperz);
			else if (t.z < lowerz + (t.x - rightx) * (-1 / zslope))
				distanceFromLeyline = VectorMath.abs(t.x - rightx, lowerz - t.z);
			else
				distanceFromLeyline = VectorMath.cProduct2D(1, zslope, t.x - midx, t.z - midz) / VectorMath.abs(1, zslope);
		}
		
		if (distanceFromLeyline > 1.00001)
			return 0;
		
		distanceFromLeyline -= 0.5f;
		if (distanceFromLeyline < 0)
			distanceFromLeyline = 0;
		
		return power * (1 - distanceFromLeyline);
	}
	
	/************************************************************************************************/
	
	//TODO: Leyline: Determine what logic is necessary.
	ArrayList<SustainedSpark> boosterCasts = new ArrayList<SustainedSpark>();
	{
		boosterCasts.add(cast);
	}
	ArrayList<Integer> xBoosts = new ArrayList<Integer>();
	ArrayList<Integer> zBoosts = new ArrayList<Integer>();
	
	//TODO: Leyline: Write math.
	//xgrowthRate = growthRate
	float growthRate;
	float xgrowthRate;
	float zgrowthRate;
	
	private void grow(long growTime) {
		if (vertical) {
			upperz += zgrowthRate * growTime;
			lowerz -= zgrowthRate * growTime;	
		} else {
			rightx += xgrowthRate * growTime;
			leftx -= xgrowthRate * growTime;
		}
	}
	
	public void update(long dtime) {
		grow(dtime);
	}
	
	public void respond() {
		
	}
}
