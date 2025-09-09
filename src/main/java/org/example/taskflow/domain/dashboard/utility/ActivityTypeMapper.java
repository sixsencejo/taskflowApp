package org.example.taskflow.domain.dashboard.utility;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ActivityTypeMapper {

    private static final Map<String, String> ACTION_MAP = Map.of(
            "TASK_CREATED", "created_task",
            "TASK_UPDATED", "updated_task",
            "TASK_STATUS_CHANGED", "status_changed_task",
            "TASK_DELETED", "deleted_task",
            "USER_CREATED", "created_user",
            "PROFILE_UPDATED", "updated_profile",
            "COMMENT_CREATED", "created_comment",
            "COMMENT_UPDATED", "updated_comment",
            "COMMENT_DELETED", "deleted_comment"
    );

    public static String toAction(String typeCode) {
        return ACTION_MAP.getOrDefault(typeCode,
                typeCode.toLowerCase().replace("_", "_"));
    }

    public static String toTargetType(String typeCode) {
        if (typeCode.startsWith("TASK_")) return "task";
        if (typeCode.startsWith("USER_") || typeCode.equals("PROFILE_UPDATED")) return "user";
        if (typeCode.startsWith("COMMENT_")) return "comment";
        return null;
    }
}
