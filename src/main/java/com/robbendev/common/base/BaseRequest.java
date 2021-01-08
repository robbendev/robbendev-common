package com.robbendev.common.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author robbendev
 * @since 2020/10/21 10:48 上午
 */
@Data
public class BaseRequest implements Serializable {

    protected int pageNum = 1;

    protected int pageSize = 10;

    private Serializable currentUserId;
}
