package com.example.forum_4_stupid.dtoMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
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
		List<Email> email = getEmailByOwnerUsername(username);
		
		for (Email emails : email) {
			var emailDTO = new EmailDTO();
			emailDTO.setId(emails.getId());
			emailDTO.setEmail(emails.getEmail());
			responseDTO.add(emailDTO);
		}
		
		return responseDTO;
	}

	@Override
	public List<EmailDTO> getAllEmailByUsersId(Integer id) {
		List<EmailDTO> responseDTO = new ArrayList<>();
		List<Email> email = getEmailByUsersId(String.valueOf(id));
		
		for (Email emails : email) {
			var emailDTO = new EmailDTO();
			emailDTO.setId(emails.getId());
			emailDTO.setEmail(emails.getEmail());
			responseDTO.add(emailDTO);
		}
		
		return responseDTO;
	}
	
//	private Email getEmailById(Integer id) {
//		Email email = emailService.get(id);
//		
//		return email;
//	}
//	
	private List<Email> getEmailByUsersId(String id) {
		List<Email> email = emailService.getAllEmailFromUserByUserId(id);
		
		return email;
	}
	
	private List<Email> getEmailByOwnerUsername(String username) {
		List<Email> email =  emailService.getAllEmaiLFromUserByUsername(username);
		
		return email;
	}

	@Override
	public EmailDTO getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
