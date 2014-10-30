package com.demo.vo;


/**
 * Very basic Tuple2 implementation in Scala style.
 * 
 * @author vu, ekr
 *
 * @param <T1> - first element of the tuple
 * @param <T2> - second element of the tuple
 */
public class Tuple2<T1,T2> {
	
	public final T1 _1;
	
	public final T2 _2;
	
	public Tuple2(T1 _1, T2 _2) {
		this._1 = _1;
		this._2 = _2;
	}
	/**
	 * Constructor to be used with JPA queries that returns an Object[]
	 * @param objects - the object pair
	 */
	@SuppressWarnings("unchecked")
	public Tuple2(Object[] objects) {
		this._1 = (T1) objects[0];
		this._2 = (T2) objects[1];
	}
	
	/**
	 * Reverses the elements.
	 * @return a new Tuple2
	 */
	public Tuple2<T2, T1> swap() {
	  return new Tuple2<T2, T1>(_2, _1);
	}
	
	/**
	 * aka number of elements
	 * @return - the number of elements 2
	 */
	public int productArity() {
	  return 2;
	}

	/**
	 * Returns the n-th projection of this product if 0 < n <= productArity, otherwise throws an IndexOutOfBoundsException. 
	 * @param n - one based
	 * @return - the untyped element
	 */
	public Object productElement(int n) {
	  Object result = null;
	  switch (n) {
	    case 1:
	      result = _1;
	      break;
	    case 2:
	      result = _2;
	      break;
	    default:
	      throw new IndexOutOfBoundsException("Argument of " + n + " is out of range.");
	  }
	  return result;
	}

	@Override
	public String toString() {
		// work in GWT
		return "Tuple2(" + this._1 + "," + this._2 + ")";
	}

}
