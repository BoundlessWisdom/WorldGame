package com.archonica;

public enum ECastType {

AIR("AIR"),
EARTH("EARTH"),
FIRE("FIRE"),
WATER("WATER"),
ICE("ICE"),
CLOUD("CLOUD"),
MAGMA("MAGMA"),
//PLANT("PLANT"),
//NULL("NULL")
;

public final String castName;

ECastType(String name) {
	castName = name;
}

}
