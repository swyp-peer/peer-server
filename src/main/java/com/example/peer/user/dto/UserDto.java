package com.example.peer.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.peer.user.entity.User;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private String role;

    @JsonProperty("phone_number")
    private String phone_number;

    @JsonProperty("profileImageUrl")
    private String profileImageUrl;

    @JsonProperty("created_date")
    private String created_date;
    
    @JsonProperty("modified_date")
    private String modified_date;
    // 기타 필드 추가

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
