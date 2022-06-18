package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class car {

	private String id;
	private String shopid;
	private String goodid;
	private String name;
	public String getShopid() {
		return shopid;
	}
	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	private String price;
	private String zhekou;
	private String intro;
	private String newo;
	private String fenlei;
	private String size;
	private String yijia;
	private String count;
	private String amount;
	private String imgUrl;
	private String status;
	private String time;
	private String phone;
	private String uname;
	private String address;
}
