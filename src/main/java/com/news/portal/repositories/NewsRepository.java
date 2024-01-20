package com.news.portal.repositories;

import com.news.portal.models.News;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findNewsById(long id);

    boolean existsByTitleAndPublicationDate(String title, LocalDateTime publicationDate);

    Page<News> findAll(Pageable pageable);

    @Transactional
    @Query(value = "select n from News n where " +
            "((:text is not null and lower(trim(n.title)) like ('%' || lower(trim(:text)) || '%')) or " +
            "(:text is not null and lower(trim(n.description)) like ('%' || lower(trim(:text)) || '%')) or " +
            "(:text is not null and lower(n.text) like ('%' || lower(trim(:text)) || '%')) or " +
            "(:text is null and concat(n.title, n.description, n.text) like ('%'))) and " +

            "((n.publicationDate between :fromDate and :toDate) or " +
            "(cast(:fromDate as timestamp ) is null and n.publicationDate <= :toDate) or " +
            "(cast(:toDate as timestamp )  is null and n.publicationDate >= :fromDate)) and " +

            "((:toNumberComments is not null and size(n.comments) between :fromNumberComments and :toNumberComments) or " +
            "(:toNumberComments is null and size(n.comments) >= :fromNumberComments))")
    Page<News> searchByKey(@Param("text") String text,
                           @Param("fromDate") LocalDateTime fromDate,
                           @Param("toDate") LocalDateTime toDate,
                           @Param("fromNumberComments") Integer fromNumberComments,
                           @Param("toNumberComments") Integer toNumberComments,
                           Pageable pageable);

}
/*
    Этот запрос позволяет выполнять динамический поиск новостей с использованием различных параметров
    ":"  – используется для обозначения параметров

    Условия поиска в тексте:
    lower(trim(...)) приводит текст к нижнему регистру и удаляет пробелы с начала и конца строки.
    like ('%' || lower(trim(:text)) || '%') выполняет поиск подстроки текста внутри строки.

    Условие по дате публикации:
    Проверяется, если дата публикации находится между :fromDate и :toDate.
    Если :fromDate или :toDate равны null, то используется соответствующее условие.

    Условие по количеству комментариев:
    Условие выбора новостей в зависимости от количества комментариев.
    Если :toNumberComments не равен null, используется диапазон, иначе выбираются новости с количеством комментариев больше или равным :fromNumberComments.
 */
