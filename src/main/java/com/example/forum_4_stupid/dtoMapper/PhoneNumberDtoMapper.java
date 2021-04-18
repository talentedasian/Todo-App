package com.example.forum_4_stupid.dtoMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.PhoneNumberDTO;
import com.example.forum_4_stupid.dto.PhoneNumberRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.interfaces.DTOClassMapper;
import com.example.forum_4_stupid.dtoMapper.interfaces.PhoneNumberDTOMapper;
import com.example.forum_4_stupid.model.PhoneNumber;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.PhoneService;

@Component
public class PhoneNumberDtoMapper implements PhoneNumberDTOMapper<PhoneNumberDTO, PhoneNumberRequest, PhoneNumber>
		, DTOClassMapper<UserDTO,Users>{
	
	private PhoneService phoneNumberService;
	
	public PhoneNumberDtoMapper(PhoneService phoneNumberService) {
		super();
		this.phoneNumberService = phoneNumberService;
	}

	@Override
	public PhoneNumberDTO save(PhoneNumberRequest request) {
		PhoneNumber phoneNumber = phoneNumberService.addPhoneNumber(request);
		var phoneNumberDTO = new PhoneNumberDTO();
		phoneNumberDTO.setId(phoneNumber.getId());
		phoneNumberDTO.setPhoneNumber(phoneNumber.getPhoneNumber());
		phoneNumberDTO.setUser(mapEntityToDTO(phoneNumber.getUser()));
		
		return phoneNumberDTO;
	}

	@Override
	public void delete(PhoneNumber entity) {
		phoneNumberService.deletePhoneNumber(entity);
	}
	
	@Override
	public PhoneNumberDTO getById(Integer id) {
		PhoneNumber phoneNumber = phoneNumberService.getPhoneNumberById(id);
		var phoneNumberDTO = new PhoneNumberDTO(id, null, null);
		phoneNumberDTO.setId(phoneNumber.getId());
		phoneNumberDTO.setPhoneNumber(phoneNumber.getPhoneNumber());
		phoneNumberDTO.setUser(mapEntityToDTO(phoneNumber.getUser()));
		
		return phoneNumberDTO;
	}

	@Override
	public List<PhoneNumberDTO> getAllPhoneNumbersByUsersUsername(String username) {
		List<PhoneNumberDTO> responseDTO = new ArrayList<>();
		List<PhoneNumber> phoneNumber = phoneNumberService.getAllEmaiLFromUserByUsername(username);
		
		for (PhoneNumber phoneNumbers : phoneNumber) {
			var phoneNumberDTO = new PhoneNumberDTO();
			phoneNumberDTO.setId(phoneNumbers.getId());
			phoneNumberDTO.setPhoneNumber(phoneNumbers.getPhoneNumber());
			phoneNumberDTO.setUser(mapEntityToDTO(phoneNumbers.getUser()));
			responseDTO.add(phoneNumberDTO);
		}
		
		return responseDTO;
	}

	@Override
	public List<PhoneNumberDTO> getAllPhoneNumbersByUsersId(Integer id) {
		List<PhoneNumberDTO> responseDTO = new ArrayList<>();
		List<PhoneNumber> phoneNumber = phoneNumberService.getAllPhoneNumberFromUserByUserId(id);
		
		for (PhoneNumber phoneNumbers : phoneNumber) {
			var phoneNumberDTO = new PhoneNumberDTO();
			phoneNumberDTO.setId(phoneNumbers.getId());
			phoneNumberDTO.setPhoneNumber(phoneNumbers.getPhoneNumber());
			phoneNumberDTO.setUser(mapEntityToDTO(phoneNumbers.getUser()));
			responseDTO.add(phoneNumberDTO);
		}
		
		return responseDTO;
	}

	@Override
	public UserDTO mapEntityToDTO(Users entity) {
		var userDTO = new UserDTO();
		userDTO.setId(entity.getId());
		userDTO.setUsername(entity.getUsername());
		userDTO.setTotalPhoneNumbers(entity.getPhoneNumber().size());
		userDTO.setTotalTodos(entity.getTodos().size());
		return userDTO;
	}

}
