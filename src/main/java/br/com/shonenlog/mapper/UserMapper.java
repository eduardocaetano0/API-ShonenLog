package br.com.shonenlog.mapper;

import br.com.shonenlog.entity.User;
import br.com.shonenlog.controller.request.UserRequest;
import br.com.shonenlog.controller.response.UserResponse;
import lombok.experimental.UtilityClass;


@UtilityClass
public class UserMapper {

    public static User toUser(UserRequest userRequest){
        return User.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();
    }
    public static UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
