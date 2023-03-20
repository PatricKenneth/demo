package br.com.demo.domains.person;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.demo.domains.users.UserEntity;
import br.com.demo.infrastructure.database.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_persons")
@Getter
@Setter
public class PersonEntity extends BaseEntity {

  private String name;
  private String documentNumber;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private UserEntity user;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((documentNumber == null) ? 0 : documentNumber.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
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
    PersonEntity other = (PersonEntity) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (documentNumber == null) {
      if (other.documentNumber != null)
        return false;
    } else if (!documentNumber.equals(other.documentNumber))
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "PersonEntity [name=" + name + ", documentNumber=" + documentNumber + ", user=" + user + "]";
  }

}
