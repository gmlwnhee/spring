package kr.inhatc.spring.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
//@Entity 디비 객체
//@NoArgsConstructor 인자 없는 argument, 생성자
//@Data getset 생성
@Entity
@SequenceGenerator(
		name="IMAGE_SEQ_GENERATOR",
		sequenceName="IMAGE_SEQ",
		initialValue=1,allocationSize=1
	)
@Table(name = "Images_T")
@NoArgsConstructor
@Data
public class Images {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE
	,generator="IMAGE_SEQ_GENERATOR")
	private int idx;
	
	@Column(unique=true)
	private String userId;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
	
}
