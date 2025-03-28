package com.yupi.springbootinit.manager;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.lkeap.v20240522.LkeapClient;
import com.tencentcloudapi.lkeap.v20240522.models.ChatCompletionsRequest;
import com.tencentcloudapi.lkeap.v20240522.models.ChatCompletionsResponse;
import com.tencentcloudapi.lkeap.v20240522.models.Message;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AiManager {
    @Resource
    private LkeapClient client;
    public String doChat(String message) {
        final String SYSTEM_PROMPT = "请生成100字以下并且结果不包含特殊字符";
        try{
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChatCompletionsRequest req = new ChatCompletionsRequest();
            req.setModel("deepseek-v3");
            req.setStream(false);
            //系统消息
            Message[] messages = new Message[2];
            Message message0 = new Message();
            message0.setRole("user");
            message0.setContent(message);
            messages[0] = message0;
            //用户消息
            Message message1 = new Message();
            message1.setRole("system");
            message1.setContent(SYSTEM_PROMPT);
            messages[1] = message1;

            req.setMessages(messages);
            // 返回的resp是一个ChatCompletionsResponse的实例，与请求对象对应
            ChatCompletionsResponse resp = client.ChatCompletions(req);
            String content = resp.getChoices()[0].getMessage().getContent();
            // 输出json格式的字符串回包
            return content;
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            log.error("ai对话失败",e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "ai对话失败了");
        }

    }
    public String doPic(String message) {
        try{
            String prompt = message;
            String apiKey = System.getenv("DASHSCOPE_API_KEY");
            ImageSynthesisParam param =
                    ImageSynthesisParam.builder()
                            .apiKey(apiKey)
                            .model("wanx2.1-t2i-turbo")
                            .prompt(prompt)
                            .n(1)
                            .size("1024*1024")
                            .build();

            ImageSynthesis imageSynthesis = new ImageSynthesis();
            ImageSynthesisResult result = null;
            try {
                System.out.println("---sync call, please wait a moment----");
                result = imageSynthesis.call(param);
            } catch (ApiException | NoApiKeyException e){
                throw new RuntimeException(e.getMessage());
            }
            return JsonUtils.toJson(result);
        }catch(ApiException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
