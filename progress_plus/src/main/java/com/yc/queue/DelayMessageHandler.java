package com.yc.queue;

import com.yc.apply.entity.Examine;
import com.yc.apply.mapper.ExamineMapper;
import com.yc.task.ExamineTask;
import com.yc.vo.other.DelayMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/12/16 13:46
 */
@Slf4j
@Component
public class DelayMessageHandler {

    @Autowired
    private DelayQueueService delayQueue;
    @Autowired
    private ExamineMapper examineMapper;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private ExamineTask examineTask;


    /**
     * 处理已到期的消息(轮询)
     */
    @Scheduled(fixedDelay = 1000)
    public void handleExpiredMessages() {
        //1 : 扫描任务，并将需要执行的任务加入到任务队列中
        List<DelayMessageVo> messages = delayQueue.getExpiredMessages();
        //2 : 开始处理消息
        if (!messages.isEmpty()) {
            for (DelayMessageVo message : messages) {
                //2.1 : 处理消息:先删除消息,获取当前消息是否已经被其他人消费
                Long remove = delayQueue.remove(message);
                if (remove > 0) {
                    //2.2 : 开启线程异步处理消息:不让处理消息的时间阻塞当前线程
                    threadPoolTaskExecutor.execute(()->{
                        Integer id = message.getId();
                        String content = message.getContent();
                        if(StringUtils.isNotBlank(content) && StringUtils.isNotEmpty(content)){
                            if (content.equals("TASKSTART")) {
                                examineMapper.updateBeginExamineStatus(id);
                            }else if(content.equals("EXAMINESTART")){
                                //科技成果计分汇总 和 考核扣分
                                examineTask.technologicalAchievementsSummary();
                            } else if (content.equals("MISSIONEND")) {
                                examineMapper.updateFinishExamineStatus(id);
                                Map<String, Object> maps = examineMapper.queryExamineDetailTotal(id);
                                Integer totalByZero = (Integer) maps.get("totalByZero");
                                Integer totalByOne = (Integer) maps.get("totalByOne");
                                String passrate = (String) maps.get("passrate");
                                Examine examine = new Examine();
                                examine.setQualified(totalByOne);
                                examine.setUnqualified(totalByZero);
                                examine.setPassrate(passrate);
                                examine.setEid(id);
                                examineMapper.updateById(examine);
                            } else if (content.equals("PUBLICITYEND")) {
                                examineMapper.updatePublicityExamineStatus(id);
                            }
                        }
                    });
                }
            }
        }
    }
}

