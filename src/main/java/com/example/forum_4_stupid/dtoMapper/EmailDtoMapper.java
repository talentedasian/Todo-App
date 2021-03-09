package com.example.forum_4_stupid.dtoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dtoMapper.interfaces.DTOMapper;
import com.example.forum_4_stupid.dtoMapper.interfaces.PersistentDTOMapper;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.service.EmailService;

@Component
public class EmailDtoMapper implements PersistentDTOMapper<EmailRequest, Email>,DTOMapper<EmailDTO, Email>{
	
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
	public EmailDTO returnEntity(Email entity) {
		var emailDTO = new EmailDTO();
		emailDTO.setId(entity.getId());
		emailDTO.setEmail(entity.getEmail());
		
		return emailDTO;
	}
	
}
