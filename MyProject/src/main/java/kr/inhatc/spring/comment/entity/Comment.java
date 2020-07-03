package kr.inhatc.spring.comment.entity;

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

@Entity
@SequenceGenerator(
		name="COMMENT_SEQ_GENERATOR",
		sequenceName="COMMENT_SEQ",
		initialValue=1,allocationSize=1
	)
@Table(name = "comments")
@NoArgsConstructor
@Data
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE
	,generator="COMMENT_SEQ_GENERATOR")
	private int cno;
    private int bno;
    private String content;
    private String writer;
    
    @Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
    private Date regDate;

}
