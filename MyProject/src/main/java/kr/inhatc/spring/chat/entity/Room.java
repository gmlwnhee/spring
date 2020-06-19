package kr.inhatc.spring.chat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 채팅방 상태 생성
 *
 */
@Entity
@SequenceGenerator(
		name="ROOM_SEQ_GENERATOR",
		sequenceName="ROOM_SEQ",
		initialValue=1,allocationSize=1
	)
@Table(name = "room")
@NoArgsConstructor
@Data
public class Room {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE
	,generator="ROOM_SEQ_GENERATOR")
	private int roomId; 
	private String roomMaker; 

	private String visiter; 
	
	@Column(columnDefinition = "varchar(100) default '아무나 들어와'")
	private String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createdTime;

}
