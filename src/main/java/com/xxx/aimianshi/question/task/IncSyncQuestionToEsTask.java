package com.xxx.aimianshi.question.task;

import cn.hutool.core.collection.CollUtil;
import com.xxx.aimianshi.question.convert.QuestionConverter;
import com.xxx.aimianshi.question.domain.entity.Question;
import com.xxx.aimianshi.question.domain.es.QuestionEs;
import com.xxx.aimianshi.question.mapper.QuestionMapper;
import com.xxx.aimianshi.question.repository.QuestionEsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class IncSyncQuestionToEsTask {

    private final QuestionMapper questionMapper;

    private final QuestionEsRepository questionEsRepository;

    private final QuestionConverter questionConverter;

    public IncSyncQuestionToEsTask(QuestionMapper questionMapper,
                                   QuestionEsRepository questionEsRepository,
                                   QuestionConverter questionConverter
    ) {
        this.questionMapper = questionMapper;
        this.questionEsRepository = questionEsRepository;
        this.questionConverter = questionConverter;
    }

    /**
     * 每 30 分钟同步一次
     */
    @Scheduled(cron = "0 */30 * * * *")
    public void run() {
        log.info("inc sync question to es start");
        long start = System.currentTimeMillis();

        // 查询近 1h 内的数据
        long FIVE_MINUTES = 30 * 60 * 1000L;
        Date minUpdateTime = new Date(new Date().getTime() - FIVE_MINUTES);
        List<Question> questionList = questionMapper.listQuestionWithDelete(minUpdateTime);

        if (CollUtil.isEmpty(questionList)) {
            log.info("no inc question");
            return;
        }
        // 同步到ES
        List<QuestionEs> questionEsList = questionList.stream()
                .map(questionConverter::toQuestionEs)
                .toList();

        final int batchSize = 500;
        int total = questionEsList.size();
        log.info("inc sync question total size: {}", total);
        for (int i = 0; i < total; i += batchSize) {
            int end = Math.min(i + batchSize, total);
            List<QuestionEs> pageQuestionEsList = questionEsList.subList(i, end);
            log.info("sync question to es, page: {}, total: {}", i, total);
            questionEsRepository.saveAll(pageQuestionEsList);
        }
        log.info("sync question to es, total: {}", total);
        log.info("inc sync question to es end, cost: {}ms", System.currentTimeMillis() - start);
    }
}
