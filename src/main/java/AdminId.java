package main.java;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="admins_ids")
public class AdminsId {
  @Id
  private long adminId;

  public AdminsId() {}

  public AdminsId(long id) {
    this.adminId = id;
  }
}
