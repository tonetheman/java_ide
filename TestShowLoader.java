
public class TestShowLoader {
	public static void main(String[] args) {
		Class cl = TestShowLoader.class;

		System.out.println("The static system classloader is " +
			ClassLoader.getSystemClassLoader());

		System.out.println("My class is : " + cl);
		System.out.println("My class loader is: " + 
			cl.getClassLoader());
		ClassLoader cloader = cl.getClassLoader();
		ClassLoader cloader2 = cloader.getParent();
		System.out.println("the parent of my class loader: "+
			cloader2);
		ClassLoader cloader3 = cloader2.getParent();
		System.out.println("the grandparent of my class loader: " +
			cloader3);

		System.out.println("made an array");
		TestShowLoader[] a = new TestShowLoader[1];
		Class cl2 = a.getClass();
		System.out.println("the array class is: " + cl2);
		ClassLoader acloader = cl2.getClassLoader();
		System.out.println("the class loader for the array: " +
			acloader);

		Thread currentThread = Thread.currentThread();
		ClassLoader threadLoader =
			currentThread.getContextClassLoader();
		System.out.println("current thread: " + currentThread);
		System.out.println("thread context loader: " +
			threadLoader);
	}
}
