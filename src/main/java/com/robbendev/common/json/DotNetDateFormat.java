package com.robbendev.common.json;

import com.robbendev.common.util.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <p>
 * 处理.net时间戳格式反序列化为LocalDatetime
 * </p>
 *
 * @author robbendev
 * @since 2020/10/8 2:17 下午
 */
@Slf4j
public class DotNetDateFormat extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        try {
            String dateValue = p.getText();
            if (StringUtils.isEmpty(dateValue)) {
                return null;
            }
            long milliseconds = Long.parseLong(dateValue.replace("/Date(", "").replace(")/", ""));
            return LocalDateTime.ofEpochSecond(milliseconds / 1000, 0, ZoneOffset.ofHours(8));
        } catch (SecurityException e) {
            log.warn("反序列化.net日期时间戳格式失败");
            return null;
        }
    }
}
