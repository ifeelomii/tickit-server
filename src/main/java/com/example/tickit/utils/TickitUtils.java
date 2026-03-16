package com.example.tickit.utils;

import java.time.LocalDateTime;
import java.util.Objects;

public class TickitUtils {
	public static boolean areStringsEqual(String str1, String str2) {
		if (str1 == null && str2 == null) {
			return true;
		} else if (str1 == null || str2 == null) {
			return false;
		} else {
			return str1.equals(str2);
		}
	}

	// Every object being send here for comparison must have equals overriden else
	// this will fail
	public static boolean areObjectsEqual(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		} else if (obj1 == null || obj2 == null) {
			return false;
		} else {
			return obj1.equals(obj2);
		}
	}

	public static <T extends Enum<T>> boolean areEnumsEqual(T enum1, T enum2) {
		if (enum1 == null && enum2 == null) {
			return true;
		} else if (enum1 == null || enum2 == null) {
			return false;
		} else {
			return enum1.equals(enum2);
		}
	}

	public static boolean areIntegerEqual(Integer int1, Integer int2) {
		if (int1 == null && int2 == null) {
			return true;
		} else if (int1 == null || int2 == null) {
			return false;
		} else {
			return int1.equals(int2);
		}
	}

	public static boolean areDoubleEqual(Double double1, Double boulbe2) {
		if (double1 == null && boulbe2 == null) {
			return true;
		} else if (double1 == null || boulbe2 == null) {
			return false;
		} else {
			return double1.equals(boulbe2);
		}
	}

	public static boolean areDatesEqual(LocalDateTime date1, LocalDateTime date2) {
		return Objects.equals(date1, date2);
	}

	public static boolean areLongEqual(Long long1, Long long2) {
		if (long1 == null && long2 == null) {
			return true;
		} else if (long1 == null || long2 == null) {
			return false;
		} else {
			return long1.equals(long2);
		}
	}

	public static boolean areBooleansEqual(Boolean bool1, Boolean bool2) {
		if (bool1 == null && bool2 == null) {
			return true;
		} else if (bool1 == null || bool2 == null) {
			return false;
		} else {
			return bool1.equals(bool2);

		}
	}
}
