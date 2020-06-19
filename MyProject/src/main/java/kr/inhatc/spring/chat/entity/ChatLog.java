package kr.inhatc.spring.chat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;
/***
 * 채팅 로그
 * 채팅 내용 및 누가 말햇는지 등 모두 기록
 *
 */
@Entity
@SequenceGenerator(
		name="CHAT_SEQ_GENERATOR",
		sequenceName="CHAT_SEQ",
		initialValue=1,allocationSize=1
	)
@Table(name = "chatlog")
@NoArgsConstructor
@Data
@IdClass(ChatLogPK.class)
public class ChatLog {
	@Id
	private int roomId;
	private String sender;
	private String receiver;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE
	,generator="CHAT_SEQ_GENERATOR")
	private int num; 
	
	@Id
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date sendTime;
	
	@Column(length=5000)
	private String content; 
	@Column(length=10)
	private String who;
}
