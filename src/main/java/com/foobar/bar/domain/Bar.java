package com.foobar.bar.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bar")
public class Bar  implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @SequenceGenerator(name="bar_id_seq", sequenceName="bar_id_seq", allocationSize=1)
  @Column(name = "ID")
  private Long id;

	@Column
	private String bar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

}
