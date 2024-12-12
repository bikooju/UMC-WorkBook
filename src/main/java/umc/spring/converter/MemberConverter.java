package umc.spring.converter;

import umc.spring.domain.Member;
import umc.spring.domain.enums.Gender;
import umc.spring.web.dto.MemberRequestDTO;
import umc.spring.web.dto.MemberResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    //DTO -> Entity
    //사용자가 입력한 데이터가 저장되어야 할 때 (예: 회원가입 요청).
    //DTO는 클라이언트에서 온 데이터만 담고 있기 때문에,
    //이를 엔티티로 변환하여 데이터베이스에 저장 가능하게 만들어야 함.
    public static Member toMember(MemberRequestDTO.JoinDto request) {
        Gender gender = null;

        switch(request.getGender()) {
            case 1:
                gender = Gender.MALE;
                break;
            case 2:
                gender = Gender.FEMALE;
                break;
            case 3:
                gender = Gender.NONE;
                break;
        }

        return Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .address(request.getAddress())
                .specAddress(request.getSpecAddress())
                .gender(gender)
                .name(request.getName())
                .memberPreferList(new ArrayList<>())
                .build();
    }
}

