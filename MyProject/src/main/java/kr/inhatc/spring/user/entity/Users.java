package kr.inhatc.spring.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;
//@Entity 디비 객체
//@NoArgsConstructor 인자 없는 argument, 생성자
//@Data getset 생성
@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class Users {
	
	@Id
	@Column(name = "USER_ID")
	private String id;
	private String pw;
	private String name;
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date joinDate;
	private String enabled;
	private String role;
}
