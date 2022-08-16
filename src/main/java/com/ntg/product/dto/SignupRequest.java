package com.ntg.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	
	private String fullName;
	private String password;
	private String email;
	private String phoneNumber;
	

}
