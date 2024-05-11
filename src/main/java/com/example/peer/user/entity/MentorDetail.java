package com.example.peer.user.entity;

import com.example.peer.schedule.entity.ScheduleRule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentorDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mentor_detail_id")
	private Long id;

	private String nickname;

	private String position;

	private String introduction;

	private Boolean isAccepted;

	private String openTalkLink;

	@OneToMany(mappedBy = "mentorDetail")
	private List<KeywordMentorDetail> keywordMentorDetails = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_create_rule_id")
	private ScheduleRule scheduleRule;

	@Builder
	public MentorDetail(String nickname, String position, String introduction, String openTalkLink) {
		this.nickname = nickname;
		this.position = position;
		this.introduction = introduction;
		this.openTalkLink = openTalkLink;
		this.isAccepted = Boolean.TRUE;
	}

	public void UpdateIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public void UpdateScheduleRule(ScheduleRule scheduleRule) {
		this.scheduleRule = scheduleRule;
	}
}
