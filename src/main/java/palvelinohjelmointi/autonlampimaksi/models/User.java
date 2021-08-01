package palvelinohjelmointi.autonlampimaksi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data // LOMBOK
@NoArgsConstructor // LOMBOK
@AllArgsConstructor // LOMBOK
@Table(name="usertable")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	
	@Column(name="username", nullable=false, unique=true)
	private String username;
	
	@Column(name="password", nullable=false)
	private String passwordHash;
	
	@Column(name="role", nullable=false)
	private String role;
	
	@ManyToOne
	@JoinColumn(name="enterpriseId")
	private Enterprise enterprise;
	
	public User(String username, String password, String role) {
		this.username = username;
		this.passwordHash = password;
	}
}
