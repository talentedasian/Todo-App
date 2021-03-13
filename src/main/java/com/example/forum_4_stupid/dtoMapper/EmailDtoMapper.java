package com.example.forum_4_stupid.dtoMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.interfaces.EmailDTOMapper;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.service.EmailService;

@Component
public class EmailDtoMapper implements EmailDTOMapper<EmailDTO, EmailRequest, Email>{
	
	@Autowired
	private EmailService emailService;

	@Override
	public void save(EmailRequest request) {
		emailService.addEmail(request);
	}

	@Override
	public void delete(Email entity) {
		emailService.deleteEmail(entity);
	}

	@Override
	public List<EmailDTO> getAllEmailByUsersUsername(String username) {
		List<EmailDTO> responseDTO = new ArrayList<>();
		List<Email> email = emailService.getAllEmaiLFromUserByUsername(username);
		
		for (Email emails : email) {
			var userDTO = new UserDTO();
			userDTO.setId(emails.getUser().getId());
			userDTO.setUsername(emails.getUser().getUsername());
			
			var emailDTO = new EmailDTO();
			emailDTO.setId(emails.getId());
			emailDTO.setEmail(emails.getEmail());
			emailDTO.setUser(userDTO);
			responseDTO.add(emailDTO);
		}
		
		return responseDTO;
	}

	@Override
	public List<EmailDTO> getAllEmailByUsersId(Integer id) {
		List<EmailDTO> responseDTO = new ArrayList<>();
		List<Email> email = emailService.getAllEmailFromUserByUserId(id);
		
		for (Email emails : email) {
			var userDTO = new UserDTO();
			userDTO.setId(emails.getUser().getId());
			userDTO.setUsername(emails.getUser().getUsername());
			
			var emailDTO = new EmailDTO();
			emailDTO.setId(emails.getId());
			emailDTO.setEmail(emails.getEmail());
			emailDTO.setUser(userDTO);
			responseDTO.add(emailDTO);
		}
		
		return responseDTO;
	}
	
	@Override
	public EmailDTO getById(Integer id) {
		Email email = emailService.getEmailById(id);
		var userDTO = new UserDTO();
		userDTO.setId(email.getUser().getId());
		userDTO.setUsername(email.getUser().getUsername());
		
		var emailDTO = new EmailDTO();
		emailDTO.setId(email.getId());
		emailDTO.setEmail(email.getEmail());
		emailDTO.setUser(userDTO);
		
		return emailDTO;
	}

}
