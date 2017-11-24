/**
 * 
 */
package hello;

/**
 * @author revanth
 *
 */
public class LoginResponse {
	
	public int userId;
	public String name;
	public String email;
	public String type;
	
	public LoginResponse(int userId, String name, String email, String type) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.type = type;
	}
	
}
