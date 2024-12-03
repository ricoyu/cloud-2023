package com.loserico.cloud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;


@Data
@Entity
@Table(name = "order_tbl")
public class OrderEntity {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	private Integer id;
	
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
	
	@Column(name = "status")
	private Integer status;
	
}
