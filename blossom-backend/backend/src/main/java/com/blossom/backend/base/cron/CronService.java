package com.blossom.backend.base.cron;

import com.blossom.backend.base.param.ParamEnum;
import com.blossom.backend.base.param.ParamService;
import com.blossom.backend.server.plan.pojo.PlanEntity;
import com.blossom.backend.server.todo.pojo.TodoEntity;
import com.blossom.backend.server.utils.NoticeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进行定时任务的处理
 */
@Slf4j
@Service
@AllArgsConstructor
public class CronService {
    private final CronMapper cronMapper;
    private final ParamService paramService;

    public void notice() {

        Map<String, String> paramMap = paramService.selectMap(false,
                ParamEnum.NOTICE_IYUU_ENABLED,
                ParamEnum.NOTICE_IYUU_TOKEN,
                ParamEnum.NOTICE_EMAIL_ENABLED,
                ParamEnum.NOTICE_EMAIL_SMTP_PWD,
                ParamEnum.NOTICE_EMAIL_SMTP_USER,
                ParamEnum.NOTICE_EMAIL_SMTP_SERVER,
                ParamEnum.NOTICE_WORK_ENABLED,
                ParamEnum.NOTICE_WORK_AGENT_ID,
                ParamEnum.NOTICE_WORK_MOBILE,
                ParamEnum.NOTICE_WORK_USER_ID,
                ParamEnum.NOTICE_WORK_CORP_ID,
                ParamEnum.NOTICE_WORK_CORP_SECRET,
                ParamEnum.NOTICE_WORK_TOKEN);
        String content = "";
        boolean send_flag = false;
        Map<String, Object> rs1 = noticeTodo();
        Map<String, Object> rs2 = noticePlan();
        if ((boolean) rs1.get("rs")) {
            content += rs1.get("msg");
            send_flag = true;
        }
        if ((boolean) rs2.get("rs")) {
            content += rs2.get("msg");
            send_flag = true;
        }
        if (send_flag) {
            if ("1".equals(paramMap.getOrDefault(ParamEnum.NOTICE_EMAIL_ENABLED.name(),"0")))
                NoticeUtil.sendEmail("任务提醒", content, paramMap.get(ParamEnum.NOTICE_EMAIL_SMTP_SERVER.name()), paramMap.get(ParamEnum.NOTICE_EMAIL_SMTP_USER.name()), paramMap.get(ParamEnum.NOTICE_EMAIL_SMTP_PWD.name()));

            if("1".equals(paramMap.getOrDefault(ParamEnum.NOTICE_WORK_ENABLED.name(),"0")))
                NoticeUtil.sendWork(content, paramMap.get(ParamEnum.NOTICE_WORK_AGENT_ID.name()), paramMap.get(ParamEnum.NOTICE_WORK_MOBILE.name()), paramMap.get(ParamEnum.NOTICE_WORK_USER_ID.name()), paramMap.get(ParamEnum.NOTICE_WORK_CORP_ID.name()), paramMap.get(ParamEnum.NOTICE_WORK_CORP_SECRET.name()), paramMap.get(ParamEnum.NOTICE_WORK_TOKEN.name()));
        }
    }

    public Map<String, Object> noticeTodo() {
        Map<String, Object> result = new HashMap<>();
        List<TodoEntity> todos = cronMapper.getNoticeTodo();
        StringBuilder content = new StringBuilder("【待办事项】\n");
        if (todos.isEmpty()) {
            result.put("rs", false);
            result.put("msg", "");
            return result;
        }
        for (TodoEntity todo : todos) {
            content.append(todo.getTaskName()).append("\n");
        }
        result.put("rs", true);
        result.put("msg", content.toString());
        return result;
    }

    public Map<String, Object> noticePlan() {
        Map<String, Object> result = new HashMap<>();
        List<PlanEntity> plans = cronMapper.getNoticePlan();
        StringBuilder content = new StringBuilder("【计划任务】\n");
        if (plans.isEmpty()) {
            result.put("rs", false);
            result.put("msg", "");
            return result;
        }
        for (PlanEntity plan : plans) {
            content.append(plan.getTitle()).append("\n");
        }
        result.put("rs", true);
        result.put("msg", content.toString());
        return result;
    }
}

