package org.example.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    int id;

    @Column(name = "resource_name")
    String resource_name;

//	@Column(name = "project_id")
//	int project_id;

    @Column(name = "designation_id")
    String designation_id;

    @Column(name = "last_modified")
    Date last_modified;



    public int getResource_id() {
        return id;
    }

    public void setResource_id(int id) {
        this.id = id;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public String getDesignation_id() {
        return designation_id;
    }

    public void setDesignation_id(String designation_id) {
        this.designation_id = designation_id;
    }

    public Date getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }
}
