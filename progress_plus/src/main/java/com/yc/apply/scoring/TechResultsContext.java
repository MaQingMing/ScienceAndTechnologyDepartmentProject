package com.yc.apply.scoring;

import com.yc.apply.scoring.techresults.AchievementScoringHandler;
import com.yc.apply.scoring.techresults.BookScoringHandler;
import com.yc.apply.scoring.techresults.PaperScoringHandler;
import com.yc.apply.scoring.techresults.ScientificScoringHandler;
import com.yc.enums.TechResultsEnum;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 成果分类 实例化 上下文
 * @author 1943
 */
@Service
public class TechResultsContext {

    private Map<TechResultsEnum, Function<ApplyVo, Result>> functionMap = new HashMap<>();

    @Resource
    private AchievementScoringHandler achievementScoringHandler;
    @Resource
    private BookScoringHandler bookScoringHandler;
    @Resource
    private PaperScoringHandler paperScoringHandler;
    @Resource
    private ScientificScoringHandler scientificScoringHandler;

    /**
     * 初始化 成果分类 处理器
     */
    @PostConstruct
    @SneakyThrows
    public void setFunctionMap(){
        functionMap.put(TechResultsEnum.Achievement, applyVo -> achievementScoringHandler.scoringScore(applyVo));
        functionMap.put(TechResultsEnum.Book,applyVo -> bookScoringHandler.scoringScore(applyVo));
        functionMap.put(TechResultsEnum.Paper,applyVo -> paperScoringHandler.scoringScore(applyVo));
        functionMap.put(TechResultsEnum.Scientific,applyVo -> scientificScoringHandler.scoringScore(applyVo));
    }

    /**
     * 根据申请类型 选择对应的 成果类型处理器
     * @param applyVo
     * @return
     */
    public Result calcPrice(ApplyVo applyVo){
        //判断申请类型
        String type = isTechResultsType(applyVo.getTechResults());
        //获取对应的方法计算并返回
        Function<ApplyVo, Result> applyVoResultFunction = functionMap.get(Enum.valueOf(TechResultsEnum.class,type));
        Result result = applyVoResultFunction.apply(applyVo);
        return result;
    }

    /**
     * 判断申请类型
     * @param type 申请类型
     * @return
     */
    private String isTechResultsType(Integer type){
        switch (type){
            case 1:
                return "Direction";
            case 2:
                return "Transverse";
            case 3:
                return "Achievement";
            case 4:
                return "Paper";
            case 5:
                return "Book";
            case 6:
                return "Invent";
            case 7:
                return "Scientific";
            case 8:
                return "Honor";
            default:
                return "";
        }
    }
}
