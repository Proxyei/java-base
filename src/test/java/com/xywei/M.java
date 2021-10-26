package com.xywei;

import org.junit.Test;

public class M {

	@Test
	public void test() {
		try {
			T1 t1 = new T1();
			t1.t1();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
