package com.saucer.tinyurl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UrlRepository  extends CrudRepository<UrlTable, Integer>{
	List<UrlTable> findByHash(String hash);
}
