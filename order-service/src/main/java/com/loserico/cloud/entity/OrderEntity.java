package com.loserico.cloud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;


@Data
@Entity
@Table(name = "`order`")
public class OrderEntity {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	private Long id;
	
	@Column(name = "user_id")
	private String userId;
	
	/**
	 * 商品编号
	 */
	@Column(name = "commodity_code")
	private String commodityCode;
	
	@Column(name = "count")
	private Integer count;
	
	@Column(name = "money")
	private Integer money;

	/*
	 * 默认映射的数据库字段类型为TIMESTAMP,改为DATETIME
	 */
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME", nullable = false, length = 19)
	private LocalDateTime createTime;

	@Column(name = "UPDATE_TIME", columnDefinition = "DATETIME", nullable = false, length = 19)
	private LocalDateTime updateTime;
}
