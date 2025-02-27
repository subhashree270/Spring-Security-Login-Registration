package in.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.entity.Customer;
import in.ashokit.repo.CustomerRepo;

@RestController
public class CustomerRestController {
	@Autowired
	private CustomerRepo crepo;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<String> loginCheck(@RequestBody Customer c){
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(c.getUname(), c.getPwd()) ;
		try {
			Authentication authenticate = authManager.authenticate(token);
			if(authenticate.isAuthenticated())
				return new ResponseEntity<>("Welcome To Our Portal...",HttpStatus.OK);
			
		}catch(Exception e) {
			//logger
		}
		return new ResponseEntity<>("Invalid Credentials...",HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/register")
	public String registerUser(@RequestBody Customer c) {
		c.setPwd(pwdEncoder.encode(c.getPwd()));
		crepo.save(c);
		return "User registered";
	}

}
