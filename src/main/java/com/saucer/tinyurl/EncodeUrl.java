package com.saucer.tinyurl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class EncodeUrl {
	
	private static String UrlRegex = "((http|https)://)?[a-zA-Z]\\w*(\\.\\w+)+(/\\w*(\\.\\w+)*)*(\\?.+)*";
	@Autowired 
	UrlRepository urlRepository;
	
	@RequestMapping("/encode")
	public @ResponseBody String encode(@RequestParam String url) {
		String hash = "400";
		if(url.matches(UrlRegex)) {
			UrlTable urlTable = new UrlTable();
			urlTable.setUrl(url);
			UrlTable stored = urlRepository.save(urlTable);
			Integer id = stored.getId();
			hash = doHashing(id);
			stored.setHash(hash);
			urlRepository.save(stored);
		} 
		return hash;
		
	}
	
	private String doHashing(Integer id) {
		String hashArray = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder hash = new StringBuilder();
		try {
			while(id > 0) {
				Integer remainder = id % hashArray.length();
				hash.append(hashArray.charAt(remainder));
				id /= hashArray.length();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return hash.toString();
	}
	
	@RequestMapping("/{hash}")
	public @ResponseBody ModelAndView decode(@PathVariable(value="hash") String hash) {
		List<UrlTable> urlTableEntry = urlRepository.findByHash(hash);
		if(urlTableEntry.size() > 0) {
			String url = urlTableEntry.get(0).getUrl();
			if(!url.matches("(http|https)")) {
				url = "http://" + url;
			}
			return new ModelAndView("redirect:" + url);//;
		}
		return new ModelAndView("redirect:https://facebook.com");
	}
}
