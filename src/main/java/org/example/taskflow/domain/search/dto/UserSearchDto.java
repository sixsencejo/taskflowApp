package org.example.taskflow.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.taskflow.domain.user.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDto {
    private Long id;
    private String username;
    private String name;
    private String email;

    public static UserSearchDto from(User user) {
        return UserSearchDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
