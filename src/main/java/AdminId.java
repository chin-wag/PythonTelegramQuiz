package main.java;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="admins_ids")
public class AdminId {
  @Id
  private long adminId;

  public AdminId() {}
}
