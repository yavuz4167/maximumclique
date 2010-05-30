package graph.util;

import java.math.BigInteger;

public class Helper {

	public static Long durationToMiliSecond(BigInteger value) {
		return value.divide(BigInteger.valueOf(1000000)).longValue();
	}

}
