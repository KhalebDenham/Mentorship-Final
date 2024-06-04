package space.marine.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import space.marine.controller.model.ChapterData;
import space.marine.controller.model.ChapterData.MarineData;
import space.marine.dao.ChapterDao;
import space.marine.dao.MarineDao;
import space.marine.entity.Chapter;
import space.marine.entity.Marine;

@Service
public class ChapterService {

	@Autowired
	private ChapterDao chapterDao;

	@Autowired
	private MarineDao marineDao;

	@Transactional(readOnly = false)
	public ChapterData saveChapter(ChapterData chapterData) {
		Chapter chapter = chapterData.toChapter();
		Chapter dbChapter = chapterDao.save(chapter); // grants a primary key to Chapter

		return new ChapterData(dbChapter);
	}

	@Transactional(readOnly = false)
	public MarineData saveMarine(Long chapterId, MarineData marineData) {
		Chapter chapter = findChapterById(chapterId);
		Long marineId = marineData.getMarineId();
		Marine marine = findOrCreateMarine(chapterId, marineId);

		copyMarineFields(marine, marineData);

		marine.setChapter(chapter);
		chapter.getMarine().add(marine);
		Marine mDb = marineDao.save(marine);

		return new MarineData(mDb);
	}

	private void copyMarineFields(Marine marine, MarineData marineData) {
		marine.setMarineId(marineData.getMarineId());
		marine.setMarineName(marineData.getMarineName());
		marine.setWeapon(marineData.getWeapon());

	}

	public Chapter findOrCreateChapter(Long chapterId) {
		Chapter chapter;
		if (Objects.isNull(chapterId)) {
			chapter = new Chapter();
		} else {
			chapter = findChapterById(chapterId);
		}

		return chapter;
	}

	private Marine findOrCreateMarine(Long chapterId, Long marineId) {
		if (Objects.isNull(marineId)) {
			return new Marine();
		} else {
			return findMarineById(chapterId, marineId);
		}
	}

	private Marine findMarineById(Long chapterId, Long marineId) {
		Marine marine = marineDao.findById(marineId).orElseThrow();

		if (marine.getChapter().getChapterId().equals(marineId)) {
			return marine;
		} else {
			throw new IllegalArgumentException(chapterId + " does not match the given petStoreId");
		}
	}

	private Chapter findChapterById(Long chapterId) {
		return chapterDao.findById(chapterId)
				.orElseThrow(() -> new NoSuchElementException("Chapter with ID=" + chapterId + " was not found."));
	}

	@Transactional(readOnly = true)
	public MarineData retrieveMarineById(Long marineId) {
		Marine marine = findMarineById(marineId);
		return new MarineData(marine);
	}

	private Marine findMarineById(Long marineId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	public List<MarineData> retrieveAllMarines() {
		List<Marine> marines = marineDao.findAll();
		List<MarineData> response = new LinkedList<>();

		for (Marine marine : marines) {
			response.add(new MarineData(marine));
		}

		return response;
	}

	@Transactional(readOnly = false)
	public void deleteMarineById(Long marineId) {
		Marine marine = findMarineById(marineId);
		marineDao.delete(marine);

	}

}
