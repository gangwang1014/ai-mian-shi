package com.xxx.aimianshi.question.task;

import cn.hutool.core.collection.CollUtil;
import com.xxx.aimianshi.question.convert.QuestionConverter;
import com.xxx.aimianshi.question.domain.entity.Question;
import com.xxx.aimianshi.question.domain.es.QuestionEs;
import com.xxx.aimianshi.question.repository.QuestionEsRepository;
import com.xxx.aimianshi.question.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 全量同步题目到ES
 */
@Component
@Slf4j
public class FullSyncQuestionToEsTask implements CommandLineRunner {

    private final QuestionRepository questionRepository;

    private final QuestionEsRepository questionEsRepository;

    private final QuestionConverter questionConverter;

    public FullSyncQuestionToEsTask(QuestionRepository questionRepository,
                                    QuestionEsRepository questionEsRepository,
                                    QuestionConverter questionConverter
    ) {
        this.questionRepository = questionRepository;
        this.questionEsRepository = questionEsRepository;
        this.questionConverter = questionConverter;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Question> questions = questionRepository.list();
        if (CollUtil.isEmpty(questions)) {
            log.info("question is empty, no content to sync");
            return;
        }
        List<QuestionEs> questionEsList = questions.stream()
                .map(questionConverter::toQuestionEs)
                .toList();
        final int pageSize = 500;
        int total = questions.size();

        log.info("sync question to es start");
        log.info("sync question to es, total: {}, page size: {}", total, pageSize);

        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            List<QuestionEs> pageQuestionEsList = questionEsList.subList(i, end);
            questionEsRepository.saveAll(pageQuestionEsList);
            log.info("sync question to es, page: {}, total: {}", i, total);
        }

        log.info("sync question to es, total: {}", total);
        log.info("sync question to es end");
    }
}
