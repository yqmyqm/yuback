package com.yupi.springbootinit.controller;

import com.yupi.springbootinit.common.AiResponse;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.manager.AiManager;
import com.yupi.springbootinit.model.dto.ai.GenChatByAiRequest;
import com.yupi.springbootinit.model.dto.ai.GenPlanByAiRequest;
import com.yupi.springbootinit.model.dto.ai.GenTestByAiRequest;
import com.yupi.springbootinit.model.entity.Chat;
import com.yupi.springbootinit.model.entity.Plan;
import com.yupi.springbootinit.model.entity.Test;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.ChatService;
import com.yupi.springbootinit.service.PlanService;
import com.yupi.springbootinit.service.TestService;
import com.yupi.springbootinit.service.UserService;
import com.yupi.springbootinit.utils.DocUtils;
import com.yupi.springbootinit.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * AI接口
 */
@RestController
@RequestMapping("/ai")
@Slf4j
public class AiController {

    @Autowired
    private AiManager aiManager;
    @Resource
    private UserService userService;
    @Resource
    private ChatService chatService;
    @Resource
    private PlanService planService;
    @Resource
    private TestService testService;
    /**
     * 根据目标生成文字
     *
     * @param genChatByAiRequest
     * @param request
     * @return
     */
    @PostMapping("/genchat")
    public BaseResponse<AiResponse> getChatByAi(GenChatByAiRequest genChatByAiRequest, HttpServletRequest request) {
        String name = genChatByAiRequest.getName();
        String goal = genChatByAiRequest.getGoal();
        //校验
        ThrowUtils.throwIf(StringUtils.isBlank(goal), ErrorCode.PARAMS_ERROR, "目标不能为空");
        ThrowUtils.throwIf(StringUtils.isBlank(name) && name.length() > 100, ErrorCode.PARAMS_ERROR, "名称不能为空或过长");
        User loginUser = userService.getLoginUser(request);

        //用户输入
        StringBuilder userInput = new StringBuilder();
        userInput.append("请你满足我的需求并且生成的格式为字符串，不要表情等内容").append("\n");
        userInput.append("我的需求：").append("\n");
        String userGoal = goal;
        userInput.append(userGoal).append("\n");
        //执行ai
        String result = aiManager.doChat(userInput.toString());
        //插入到数据库
        Chat chatResult = new Chat();
        chatResult.setName(name);
        chatResult.setResult(result);
        chatResult.setUserId(loginUser.getId());
        chatResult.setGoal(userGoal);
        boolean saveResult = chatService.save(chatResult);
        ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "图表保存失败");
        //返回结果
        AiResponse aiResponse = new AiResponse();
        aiResponse.setResult(result);
        aiResponse.setChatId(chatResult.getId());
        return ResultUtils.success(aiResponse);

    }

    /**
     * 生成教案
     */
    @PostMapping("/genplan")
    public BaseResponse<AiResponse> genPlan(@RequestPart("file") MultipartFile multipartFile,GenPlanByAiRequest genPlanByAiRequest, HttpServletRequest request) {
        String subject = genPlanByAiRequest.getSubject();
        String bookName = genPlanByAiRequest.getBookName();
        String classTime = genPlanByAiRequest.getClassTime();
        String topic = genPlanByAiRequest.getTopic();
        //校验
        ThrowUtils.throwIf(StringUtils.isAnyBlank(subject, bookName, classTime, topic), ErrorCode.PARAMS_ERROR, "输入请求不能为空");
        ThrowUtils.throwIf(topic.length() > 100, ErrorCode.PARAMS_ERROR, "课题过长");
        //连接请求
        StringBuilder userInput = new StringBuilder();
        userInput.append("请根据我的需求生成教案").append("\n");
        userInput.append("以下是我的需求：").append("\n");
        userInput.append("专业名称：").append(subject).append("\n");
        userInput.append("教材名：").append(bookName).append("\n");
        userInput.append("总课时：").append(classTime).append("\n");
        userInput.append("课题：").append(topic).append("\n");
        //文件转换为字符串
        String data = DocUtils.docToText(multipartFile);
        userInput.append("以下是参考的教学资源").append("\n");
        userInput.append(data).append("\n");
        //执行ai
        String result = aiManager.doChat(userInput.toString());
        ThrowUtils.throwIf(StringUtils.isBlank(result), ErrorCode.SYSTEM_ERROR, "生成失败");
        //插入数据库
        Plan plan = new Plan();
        plan.setSubject(subject);
        plan.setBookName(bookName);
        plan.setClassTime(classTime);
        plan.setTopic(topic);
        plan.setResult(result);
        boolean saveResult = planService.save(plan);
        ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "保存失败");
        //返回结果
        AiResponse aiResponse = new AiResponse();
        aiResponse.setResult(result);
        aiResponse.setChatId(plan.getId());
        return ResultUtils.success(aiResponse);
    }

    /**
     * 生成练习题
     */
    @PostMapping("/gentest")
    public BaseResponse<AiResponse> genProblem(@RequestPart("file") MultipartFile multipartFile, GenTestByAiRequest genTestByAiRequest, HttpServletRequest request) {
        Integer singleNum = genTestByAiRequest.getSingleNum();
        Integer multipleNum = genTestByAiRequest.getMultipleNum();
        Integer judgeNum = genTestByAiRequest.getJudgeNum();
        Integer shortAnswerNum = genTestByAiRequest.getShortAnswerNum();
        Boolean isGenerateAnswer = genTestByAiRequest.getIsGenerateAnswer();
        String goal = genTestByAiRequest.getGoal();
        ThrowUtils.throwIf(StringUtils.isBlank(goal), ErrorCode.PARAMS_ERROR, "目标不能为空");
        ThrowUtils.throwIf(singleNum < 0 || multipleNum < 0 || judgeNum < 0 || shortAnswerNum < 0, ErrorCode.PARAMS_ERROR, "题目数量不能小于0");
        StringBuilder userInput = new StringBuilder();
        userInput.append("请根据我的需求生成练习题").append("\n");
        userInput.append("以下是我的需求：").append("\n");
        userInput.append("目标：").append(goal).append("\n");
        userInput.append("单选题数量：").append(singleNum).append("\n");
        userInput.append("多选题数量：").append(multipleNum).append("\n");
        userInput.append("判断题数量：").append(judgeNum).append("\n");
        userInput.append("简答题数量：").append(shortAnswerNum).append("\n");
        userInput.append("是否生成答案和解析(0表示不生成，1表示生成)：").append(isGenerateAnswer).append("\n");
        userInput.append("以下是参考的参考资料,请根据学生对知识点掌握情况出题");
        String data = ExcelUtils.excelToCsv(multipartFile);
        userInput.append(data).append("\n");
        String result = aiManager.doChat(userInput.toString());
        ThrowUtils.throwIf(StringUtils.isBlank(result), ErrorCode.SYSTEM_ERROR, "生成失败");
        Test test = new Test();
        test.setGoal(goal);
        test.setSingleNum(singleNum);
        test.setMultipleNum(multipleNum);
        test.setJudgeNum(judgeNum);
        test.setShortAnswerNum(shortAnswerNum);
        test.setIsAnswer(isGenerateAnswer);
        test.setResult(result);
        boolean saveResult = testService.save(test);
        ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "保存失败");
        AiResponse aiResponse = new AiResponse();
        aiResponse.setResult(result);
        return ResultUtils.success(aiResponse);

    }

}
