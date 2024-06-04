package space.marine.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Chapter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chapterId;
	
	private String chapterName;
	private String homeworld;
	private String tactics;
	private String leader;
	private String alignment;
	private String description;
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Marine> marine = new HashSet<>();
	
}
