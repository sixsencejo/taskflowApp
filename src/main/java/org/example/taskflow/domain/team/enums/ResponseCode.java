package org.example.taskflow.domain.team.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    TEAMS_SEARCH_SUSSES("팀 목록을 조회했습니다."),
    TEAM_SEARCH_SUSSES("팀 정보를 조회했습니다."),
    TEAM_SEARCH_MEMBER_SUSSES("팀 멤버 목록을 조회했습니다."),
    TEAM_CREATE_SUSSES("팀이 성공적으로 생성되었습니다."),
    TEAM_UPDATE_SUSSES("팀 정보가 성공적으로 업데이트되었습니다."),
    TEAM_DELETE_SUSSES("팀이 성공적으로 삭제되었습니다."),
    TEAM_MEMBER_INSERT_SUSSES("멤버가 성공적으로 추가되었습니다."),
    TEAM_MEMBER_DELETE_SUSSES("멤버가 성공적으로 제거되었습니다.");

    private final String message;
}
