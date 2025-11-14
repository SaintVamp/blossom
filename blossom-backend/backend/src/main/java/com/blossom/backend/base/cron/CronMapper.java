package com.blossom.backend.base.cron;

import com.blossom.backend.server.plan.pojo.PlanEntity;
import com.blossom.backend.server.todo.pojo.TodoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CronMapper {
    List<PlanEntity> getNoticePlan();

    List<TodoEntity> getNoticeTodo();
}
