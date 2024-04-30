package com.example.peer.user.entity;

import java.util.HashMap;
import java.util.Map;

import com.example.peer.common.domain.BaseTimeEntity;

import com.example.peer.schedule.entity.Schedule;
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
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String name;

	private String email;

	private Role role;

	private String phoneNumber;

	private String profileImageUrl;

	private OauthType oauthType;

	private String socialId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mentor_detail_id")
	private MentorDetail mentorDetail;

	@OneToMany(mappedBy = "mentor")
	private List<Schedule> schedules = new ArrayList<>();

	@Builder
	public User(String name, String email, Role role, String phoneNumber, String profileImage, String socialId, OauthType oauthType) {
		this.name = name;
		this.email = email;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.profileImageUrl = profileImage;
		this.socialId = socialId;
		this.oauthType = oauthType;
	}
	public Map<String, Object> getClaims() {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("userId", id);
		dataMap.put("profileImage", profileImageUrl);
		dataMap.put("name", name);
		dataMap.put("email", email);
		dataMap.put("socialId", socialId);
		dataMap.put("oauthType", oauthType);
		return dataMap;

	}

	public void UpdateMentorDetail(MentorDetail mentorDetail) {
		this.mentorDetail = mentorDetail;
	}

	public void UpdatePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void UpdateRole(Role role) {
		this.role = role;
	}

	public void UpdateProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
}
