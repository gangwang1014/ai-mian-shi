package com.xxx.aimianshi.question.repository;

import com.xxx.aimianshi.question.domain.es.QuestionEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionEsRepository extends ElasticsearchRepository<QuestionEs, Long> {

}
