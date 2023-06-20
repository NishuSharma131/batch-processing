package org.example.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "projects")
public class Project {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "project_id")
	int id;

	@Column(name = "project_name")
	String project_name;

	@Column(name = "allocated_capital")
	Double allocated_capital;

	
//	@Column(name = "last_modified")
//	Date last_modified;

//	@Column(name = "resource_id")
//	int resource_id;
//	@Column(name = "used_capital")//need to delete
//	Double used_capital;

	@Transient
	@Column(name="used_capital")
	Double used_capital;


	public Double getUsed_capital() {
		return used_capital;
	}

	public void setUsed_capital(Double used_capital) {
		this.used_capital = used_capital;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public Double getAllocated_capital() {
		return allocated_capital;
	}

	public void setAllocated_capital(Double allocated_capital) {
		this.allocated_capital = allocated_capital;
	}
//
//	public Date getLast_modified() {
//		return last_modified;
//	}
//
//	public void setLast_modified(Date last_modified) {
//		this.last_modified = last_modified;
//	}





}
