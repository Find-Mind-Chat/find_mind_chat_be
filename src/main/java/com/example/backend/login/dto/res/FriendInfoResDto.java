package com.example.backend.login.dto.res;

import com.example.backend.login.domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendInfoResDto {

    private String friendUuid;
    private String friendName;
    private String friendProImg;

    public static FriendInfoResDto EntityToDto(Member member) {
        return FriendInfoResDto.builder()
                .friendUuid(member.getUuid())
                .friendName(member.getName())
                .friendProImg(member.getProImg())
                .build();
    }
}
