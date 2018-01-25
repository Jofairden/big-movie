package bigmovie.gethandlers;

import bigmovie.subroutines.HttpGETSubroutine;

/**
 * @author Daniel
 * With this abstract class, all the GET handling for specific context is handled in their own class
 */
public abstract class GetHandler {
	
	/**
	 * Handles a given request
	 *
	 * @param getAddr the get address object
	 * @return Whether the request was handled with success
	 */
	public abstract boolean handleRequest(HttpGETSubroutine.GetAddr getAddr, String[] args);

//	public <T extends GetHandler> GetHandler as(T type) {
//		return (type.getClass().cast(this));
//	}
}
