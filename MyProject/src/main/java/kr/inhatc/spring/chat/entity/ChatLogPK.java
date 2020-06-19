package kr.inhatc.spring.chat.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
public class ChatLogPK implements Serializable {
	private int roomId;
	@GeneratedValue(strategy=GenerationType.SEQUENCE
	,generator="CHAT_SEQ_GENERATOR")
	private int num; 
	private Date sendTime;
}
