package com.news.portal.services;

import com.news.portal.models.News;
import com.news.portal.repositories.NewsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    public News findNewsById(Long id) {
        return newsRepository.findNewsById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("The news with id =" + id + " was not found!"));
    }
}
