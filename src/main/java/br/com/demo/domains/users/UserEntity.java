package br.com.demo.domains.users;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.demo.infrastructure.database.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

  private String username;
  private String password;

  private String tokenHash = UUID.randomUUID().toString();

  public UserEntity(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + ((tokenHash == null) ? 0 : tokenHash.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserEntity other = (UserEntity) obj;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    if (password == null) {
      if (other.password != null)
        return false;
    } else if (!password.equals(other.password))
      return false;
    if (tokenHash == null) {
      if (other.tokenHash != null)
        return false;
    } else if (!tokenHash.equals(other.tokenHash))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "UserEntity [username=" + username + ", password=" + password + ", tokenHash=" + tokenHash + "]";
  }

}
