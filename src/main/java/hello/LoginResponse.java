/**
 * 
 */
package hello;

/**
 * @author revanth
 *
 */
public class LoginResponse {
	
	public String name;
	public String email;
	public String type;
	
	public LoginResponse(String name, String email, String type) {
		super();
		this.name = name;
		this.email = email;
		this.type = type;
	}
	
}
