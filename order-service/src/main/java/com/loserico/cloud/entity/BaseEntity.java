package com.loserico.cloud.entity;

import com.loserico.common.lang.utils.ReflectionUtils;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity父类，提供creator、createTime、modifier、modifyTime通用字段自动填值
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 * @since 2017-05-23 11:22
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -7833247830642842225L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, unique = true, nullable = false)
	private Long id;
	
	@Column(name = "CREATOR", length = 100, nullable = false)
	private String creator;
	
	/*
	 * 默认映射的数据库字段类型为TIMESTAMP,改为DATETIME
	 */
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME", nullable = false, length = 19)
	private LocalDateTime createTime;
	
	@Column(name = "MODIFIER", length = 100, nullable = false)
	private String modifier;
	
	@Column(name = "MODIFY_TIME", columnDefinition = "DATETIME", nullable = false, length = 19)
	private LocalDateTime modifyTime;
	
	
	/**
	 * 在Entity被持久化之前做一些操作
	 */
	@PrePersist
	protected void onPrePersist() {
		LocalDateTime now = LocalDateTime.now();
		setCreateTime(now);
		setModifyTime(now);
		String username = "getUsernameFromsomewhere";
	}
	
	@PreUpdate
	protected void onPreUpdate() {
		setModifyTime(LocalDateTime.now());
		setModifier("System");
	}
	
	/**
	 * entity之类是支持逻辑删除的
	 */
	@PreRemove
	protected void preRemove() {
		ReflectionUtils.setField("deleted", this, true);
	}
	
}
