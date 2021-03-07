package com.example.forum_4_stupid.dtoMapper.interfaces;

import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.model.Users;

public interface UserDTOMapper extends DTOMapper<UserDTO, Users>  {
	
	public UserDTO findbyUsername(Users entity);
	
}
