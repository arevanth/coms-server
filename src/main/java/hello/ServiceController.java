/**
 * 
 */
package hello;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.dao.UserRegister;

/**
 * @author revanth
 *
 */

@Controller
public class ServiceController {
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public String inService()
	{
		return("The server is running.");
	}
	
	@ResponseBody 
	@RequestMapping(value="/register", method= RequestMethod.POST)
	public boolean register(@RequestBody UserRegister user)
	{
		Long result = Util.register(user.name, user.email, user.password, user.type);
		
		if(result > 0)
			return true;
		else
			return false;
	}
	
	@ResponseBody 
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoginResponse login(@RequestBody LoginRequest login)
	{
		LoginResponse response = Util.login(login.email, login.password);
		return response;
	}
	
	@ResponseBody 
	@RequestMapping(value = "/saveip", method = RequestMethod.POST)
	public Boolean save(@RequestBody saveIpRequest save)
	{
		return Util.saveIp(save.userId,save.ip);
	}
	
	@ResponseBody 
	@RequestMapping(value = "/getip", method = RequestMethod.POST)
	public List<IPObject> getIP(@RequestBody GetIpRequest request){
		System.out.println(request.type + "-" + request.userId);
		return Util.getAllIp(request);
	}
	
	@ResponseBody
	@RequestMapping(value ="/setcondition", method = RequestMethod.POST)
	public boolean setCondition(@RequestBody ConditionRequest request)
	{
		return Util.setCondition(request);
	}
	
	@ResponseBody
	@RequestMapping(value ="/getcondition", method = RequestMethod.POST)
	public List<Integer> getCondition(@RequestBody ConditionRequest request)
	{
		return Util.getCondition(request);
	}
	
	@ResponseBody
	@RequestMapping(value ="/getemail", method = RequestMethod.POST)
	public List<String> getEmail(@RequestBody String email)
	{
		return Util.getEmail(email);
	}
	
	@ResponseBody
	@RequestMapping(value = "/consent",method = RequestMethod.POST)
	public boolean giveConsent(@RequestBody ConsentRequest consent)
	{
		return Util.giveConsent(consent);
	}
}