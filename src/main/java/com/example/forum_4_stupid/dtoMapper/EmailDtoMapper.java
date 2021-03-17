package com.example.forum_4_stupid.dtoMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.interfaces.DTOClassMapper;
import com.example.forum_4_stupid.dtoMapper.interfaces.EmailDTOMapper;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.EmailService;

@Component
public class EmailDtoMapper implements EmailDTOMapper<EmailDTO, EmailRequest, Email>
		, DTOClassMapper<UserDTO,Users>{
	
	@Autowired
	private EmailService emailService;

	@Override
	public EmailDTO save(EmailRequest request) {
		Email email = emailService.addEmail(request);
		var emailDTO = new EmailDTO();
		emailDTO.setId(email.getId());
		emailDTO.setUser(mapEntityToDTO(email.getUser()));
		emailDTO.setEmail(email.getEmail());
		emailDTO.setUser(mapEntityToDTO(email.getUser()));
		
		return emailDTO;
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
			var emailDTO = new EmailDTO();
			emailDTO.setId(emails.getId());
			emailDTO.setEmail(emails.getEmail());
			emailDTO.setUser(mapEntityToDTO(emails.getUser()));
			responseDTO.add(emailDTO);
		}
		
		return responseDTO;
	}

	@Override
	public List<EmailDTO> getAllEmailByUsersId(Integer id) {
		List<EmailDTO> responseDTO = new ArrayList<>();
		List<Email> email = emailService.getAllEmailFromUserByUserId(id);
		
		for (Email emails : email) {
			var emailDTO = new EmailDTO();
			emailDTO.setId(emails.getId());
			emailDTO.setEmail(emails.getEmail());
			emailDTO.setUser(mapEntityToDTO(emails.getUser()));
			responseDTO.add(emailDTO);
		}
		
		return responseDTO;
	}
	
	@Override
	public EmailDTO getById(Integer id) {
		Email email = emailService.getEmailById(id);
		var emailDTO = new EmailDTO();
		emailDTO.setId(email.getId());
		emailDTO.setEmail(email.getEmail());
		emailDTO.setUser(mapEntityToDTO(email.getUser()));
		
		return emailDTO;
	}

	@Override
	public UserDTO mapEntityToDTO(Users entity) {
		var userDTO = new UserDTO();
		userDTO.setId(entity.getId());
		userDTO.setUsername(entity.getUsername());
		userDTO.setTotalEmails(entity.getEmail().size());
		userDTO.setTotalTodos(entity.getTodos().size());
		return userDTO;
	}


}
