package com.saucer.tinyurl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;

@Entity
public class UrlTable {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

//	@Index(name = "IDX_MYINDEX")
    @Column(nullable = false)
    private String hash;

    private String url;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
