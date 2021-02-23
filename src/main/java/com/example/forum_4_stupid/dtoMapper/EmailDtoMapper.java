package com.example.forum_4_stupid.dtoMapper;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.model.Users;

public class EmailDtoMapper {
	
	private UserDtoMapper userDtoMapper = new UserDtoMapper();
	
	public EmailDTO returnEmail (Email email) {
		var emailDTO = new EmailDTO();
		emailDTO.setId(email.getId());
		emailDTO.setEmail(email.getEmail());
		
		return emailDTO;
	}
	
}