package com.myway.questmultichoice.service.jpa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

class Test{
	public static void main(String[] args){
		A a = new A();
		B b = new B();
		A c = new B();
		b.m(a);
		b.m(b);
		b.m(c);
	}
}
enum SIZE{BIG,SMALL}
class A{
	void m(){System.out.println("A.m");}
}
class B extends A{
	void m(A a){System.out.println("B.ma");}
	void m(B a){System.out.println("B.mb");}
}

//interface A{
//	int v = 1;
//	void m();
//}
//interface B{
//	int v = 2;
//	void m();
//}
//class D implements A, B{
//	public void m() {
//		int a = ((A)this).v;
//	}
//}
