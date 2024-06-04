package space.marine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import space.marine.controller.model.ChapterData;
import space.marine.controller.model.ChapterData.MarineData;
import space.marine.service.ChapterService;

@RestController
@RequestMapping("/space_marine")
@Slf4j
public class MarineController {

	@Autowired
	private ChapterService chapterService;

	@PostMapping("/chapter")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ChapterData createChapter(@RequestBody ChapterData chapterData) {
		log.info("Creating chapter{}", chapterData);

		return chapterService.saveChapter(chapterData);
	}


	@PostMapping("/chapter/{chapterId}/{marineId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MarineData addMarineToChapter(@PathVariable Long chapterId, @RequestBody MarineData marineData) {
		log.info("Adding marine {} to chapter {}", marineData, chapterId);

		return chapterService.saveMarine(chapterId, marineData);

	}

	@GetMapping("/marine")
	public List<MarineData> retrieveAllMarines() {
		log.info("Retrieve all marines called.");
		return chapterService.retrieveAllMarines();

	}

	@GetMapping("/marine/{marineId}")
	public MarineData retrieveMarineById(@PathVariable Long marineId) {
		log.info("Retrieving marine with ID={}", marineId);
		return chapterService.retrieveMarineById(marineId);
	}
}
