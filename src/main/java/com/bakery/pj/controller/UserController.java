package com.bakery.pj.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.pj.model.user.AuthenticationRequest;
import com.bakery.pj.model.user.AuthenticationResponse;
import com.bakery.pj.model.user.UserDTO;
import com.bakery.pj.model.user.UserDao;
import com.bakery.pj.security.jwt.CustomUserDetailsService;
import com.bakery.pj.security.jwt.JwtUtil;
import com.bakery.pj.service.S3Service;
import com.bakery.pj.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/user/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		
		
		int userIdCnt = userService.selectByUserId(user.getUserId());
		
	  if(userIdCnt!=0) {
            return new ResponseEntity<String>("아이디가 이미 존재합니다!",
                    HttpStatus.BAD_REQUEST);
        }
		
		UserDao returnUser = userDetailsService.save(user);
		return ResponseEntity.ok(returnUser);
	}
	@PostMapping(value = "/user/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserId(), authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		}
		catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserId());
		String token = jwtUtil.generateToken(userDetails);
		String resfreshToken = jwtUtil.generateRefreshToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(token,resfreshToken));
	}
  @PostMapping("/photo")
    public String execWrite(@RequestParam("file") MultipartFile file) throws IOException {
	  
	  System.out.println("file"+file);
	  s3Service.upload(file);
        



       

        return null;
    }
	@GetMapping("/hellouser")
	public String getUser(Principal principal)
	{
		System.out.println("1111");
		System.out.println(principal.getName());
		return "Hello User";
	}
	
	@GetMapping("/helloadmin")
	public String getAdmin()
	{
		return "Hello Admin";
	}
}
