package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
	private String id;
	private String goodid;
	private String name;
	private String price;
	private String zhekou;
	private String intro;
	private String newo;
	private String fenlei;
	private String size;
	private String yijia;
	private String amount;
	private String imgUrl;
	private String status;
	private String shouchu;
}
