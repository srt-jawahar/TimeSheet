package com.foucsr.crmportal.mysql.database.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.foucsr.crmportal.model.audit.DateAudit;
import com.foucsr.crmportal.mysql.database.model.managerapp.KpiAndKraGroup;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveAppliedEntity;
import com.foucsr.crmportal.mysql.database.model.timesheet.LeaveBalanceEntity;

/**
 * Created by FocusR.
 */

@Entity
@Table(name = "USER_LOGIN_DETAILS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        }),
        @UniqueConstraint(columnNames = {
                "employeeId"
            })
})
public class User  extends DateAudit{
    @Id
    @SequenceGenerator(name = "USER_LOGIN_DETAILS_SEQ", sequenceName = "USER_LOGIN_DETAILS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_LOGIN_DETAILS_SEQ")
    private Long id;

    @NotBlank
    @Size(max = 250)
    private String name;
    
    @NotBlank
    @Size(max = 250)
    private String employeeId;

    @NotBlank
    @Size(max = 50)
    private String username;

    
    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(max = 500)
    private String password;
    
    private String is_bulk_upload;
    
    @Column(name = "DATE_FREEZE")
	private Integer date_freeze;
    
	@Column(name="IS_LAPTOP_DETAILS_SUBMITTED")
	private String is_laptop_details_submitted;
   
	public Integer getDate_freeze() {
		return date_freeze;
	}

	public void setDate_freeze(Integer date_freeze) {
		this.date_freeze = date_freeze;
	}

	public char getIs_active() {
		return is_active;
	}

	public void setIs_active(char is_active) {
		this.is_active = is_active;
	}

	public String getIs_bulk_upload() {
		return is_bulk_upload;
	}

	public void setIs_bulk_upload(String is_bulk_upload) {
		this.is_bulk_upload = is_bulk_upload;
	}





	private char is_active;
    
    @Column(name = "reset_token" , length = 2000)
	private String resetToken;
    
    @Column(name = "Avatar_URL" , length = 2000)
   	private String avatarUrl;
    
    
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user")
	private List<LeaveBalanceEntity> leaveBalances;
    
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user")
  	private List<LeaveAppliedEntity> leaveApplies;
    
    private String country_code;
    
    
    
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}





	//@ManyToMany(fetch = FetchType.LAZY)
	@ManyToMany(cascade =CascadeType.ALL)
	//@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "ROLES_MAP_BY_USERS",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }      

    
    public User(String name, String username, String email, String password ,
    		        char is_active) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
       
		this.is_active = is_active;
    }
    
    public User(String employeeId, String  name, String username, String email, String password ,
	        char is_active,String avatarUrl) {
this.name = name;
this.username = username;
this.email = email;
this.password = password;
this.employeeId = employeeId;
//      this.is_super_user= is_super_user;

this.is_active = is_active;
this.avatarUrl = avatarUrl;
}


   
//	public User(Long id, @NotBlank @Size(max = 40) String name, @NotBlank @Size(max = 15) String username,
//			@NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password, String orgname,
//			String vendorID, String agentId, char supplier_flag, char buyer_flag, Set<Role> roles , char is_active) {
//		this.id = id;
//		this.name = name;
//		this.username = username;
//		this.email = email;
//		this.password = password;
//		this.orgname = orgname;
//		this.vendorID = vendorID;
//		this.agentId = agentId;
//		this.supplier_flag = supplier_flag;
//		this.buyer_flag = buyer_flag;
//		this.roles = roles;
//		this.is_active = is_active;
//	}

	


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public char getIs_Active() {
		return is_active;
	}

	public void setIs_Active(char is_active) {
		this.is_active = is_active;
	}

	public List<LeaveBalanceEntity> getLeaveCategories() {
		return leaveBalances;
	}

	public void setLeaveCategories(List<LeaveBalanceEntity> leaveBalances) {
		this.leaveBalances = leaveBalances;
	}

	public List<LeaveAppliedEntity> getLeaveApplies() {
		return leaveApplies;
	}

	public void setLeaveApplies(List<LeaveAppliedEntity> leaveApplies) {
		this.leaveApplies = leaveApplies;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getIs_laptop_details_submitted() {
		return is_laptop_details_submitted;
	}

	public void setIs_laptop_details_submitted(String is_laptop_details_submitted) {
		this.is_laptop_details_submitted = is_laptop_details_submitted;
	}

	

	
	

	
}