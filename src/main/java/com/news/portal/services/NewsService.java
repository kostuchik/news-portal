package com.news.portal.services;


import com.news.portal.models.News;
import com.news.portal.repositories.NewsRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;

    public Page<News> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    public Page<News> searchByKey(String text, LocalDateTime fromDate,  LocalDateTime toDate,
                                  Integer fromNumberComments, Integer toNumberComments, Pageable pageable) {
        return newsRepository.searchByKey(text, fromDate, toDate, fromNumberComments,
                toNumberComments, pageable);
    }

    public News findNewsById(Long id) {
        return newsRepository.findNewsById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("The news with id =" + id + " was not found!"));
    }

    public News createNews(News news) {
        if (newsRepository.existsByTitleAndPublicationDate(news.getTitle(), news.getPublicationDate()))
            throw new EntityExistsException("The news " + news.getTitle() + " already exists");
        news.setPublicationDate(LocalDateTime.now());

        return saveNews(news);
    }

    public News updateNews(Long id, News editedNews) {
        News news = findNewsById(id);
        news.setTitle(editedNews.getTitle());
        news.setDescription(editedNews.getDescription());
        news.setText(editedNews.getText());

        return saveNews(news);
    }

    public News saveNews(News news) {
        try {
            return newsRepository.save(news);

        } catch (RuntimeException e) {
            log.error("The news {} has not been saved! Error: [{}].", news.getTitle(), e.getLocalizedMessage());
            throw new PersistenceException(String.format("The news %s has not been saved! Error: [%s].",
                    news.getTitle(), e));
        }
    }

    public void deleteNews(Long newsId) {
        if (!newsRepository.existsById(newsId))
            throw new EntityNotFoundException("The news with id = " + newsId + " was not found!");
        newsRepository.deleteById(newsId);
    }
}