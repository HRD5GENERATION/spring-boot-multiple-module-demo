package com.phearun.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phearun.domain.Article;
import com.phearun.domain.filter.ArticleFilter;
import com.phearun.repository.ArticleRepository;
import com.phearun.utility.Paging;

@RestController
@RequestMapping("/api/v1")
public class ArticleRestController {

	private ArticleRepository articleRepository;
	
	@Autowired
	public ArticleRestController(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	@GetMapping("/articles")
	public Map<String, Object> findAll(ArticleFilter filter, Paging paging){
		List<Article> articles = articleRepository.findAllFilter(filter, paging);
		
		Map<String, Object> response = new HashMap<>();
		
		if(!articles.isEmpty()){
			
			paging.setTotalCount(articleRepository.countAllFilter(filter));
			
			response.put("message", "Success!");
			response.put("status", true);
			response.put("data", articles);
			response.put("paging", paging);
		}else{
			response.put("message", "No article!");
			response.put("status", false);
		}
		return response;
	}
	
	@PostMapping("/articles")
	public boolean save(@RequestBody Article article){
		System.out.println(article);
		return articleRepository.save(article);
	}
	
	@GetMapping("/articles/{id}")
	public Article findOne(@PathVariable("id") Integer id){
		System.out.println(id);
		return articleRepository.findOne(id);
	}
	
	@PutMapping("/articles")
	public boolean update(@RequestBody Article article){
		System.out.println(article);
		return articleRepository.update(article);
	}
	
	@DeleteMapping("/articles/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Integer id){
		System.out.println(id);
		Map<String, Object> response = new HashMap<>();
		if(articleRepository.remove(id)){
			response.put("message", "Removed Successfully!");
			response.put("status", true);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
	}
	
}
