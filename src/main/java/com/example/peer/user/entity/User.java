package com.example.peer.user.entity;

import com.example.peer.common.domain.BaseTimeEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mentor_detail_id")
	private MentorDetail mentorDetail;

	@Builder
	public User(String name, String email, Role role, String phoneNumber, String profileImageUrl) {
		this.name = name;
		this.email = email;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.profileImageUrl = profileImageUrl;
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
