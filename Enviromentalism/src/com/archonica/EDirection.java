package com.archonica;

public enum EDirection {

NORTH(0, 1),
SOUTH(0, -1),
EAST(1, 0),
WEST(-1, 0);

public static final EDirection[] directions = {NORTH, SOUTH, EAST, WEST};

public int x, z;

EDirection(int x, int z) {
	this.x = x;
	this.z = z;
}

}
