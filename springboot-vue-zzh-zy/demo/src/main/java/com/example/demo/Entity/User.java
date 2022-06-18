package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private String id;
	private String word;
	private String name;
	private String sex;
	private String phone;
	private String email;
	private String city;
	private String account;
	private String status;
	private String licence;
	private String card;
	private String money;
	private String level;
	private String grade;
}
