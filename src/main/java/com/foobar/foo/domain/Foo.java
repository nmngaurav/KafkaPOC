package com.foobar.foo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "foo")
public class Foo implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @SequenceGenerator(name="foo_id_seq", sequenceName="foo_id_seq", allocationSize=1)
  @Column(name = "ID")
  private Long id;

  @Column(name = "FOO")
  private String foo;

  public String getFoo() {
	return foo;
  }

  public void setFoo(String foo) {
	this.foo = foo;
  }

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

}
