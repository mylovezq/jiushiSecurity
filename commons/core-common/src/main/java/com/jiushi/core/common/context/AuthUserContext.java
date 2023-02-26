package com.jiushi.core.common.context;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.jiushi.core.common.model.PayloadDto;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 请求头认证用户
 */
@Component
public class AuthUserContext {
    private static final InheritableThreadLocal<PayloadDto> JWT_PAYLOAD_CONTEXT = new InheritableThreadLocal<>();
    private static final InheritableThreadLocal<HashMap<String, Collection<String>>> HEADERS = new InheritableThreadLocal<>();

    /**
     * 获取当前请求的JWT payload
     *
     * @return
     */
    public static PayloadDto getJwtPayload() {
        return JWT_PAYLOAD_CONTEXT.get();
    }

    /**
     * 设置当前请求的JWT
     *
     * @param payload
     */
    public static void setJwtPayload(PayloadDto payload) {
        JWT_PAYLOAD_CONTEXT.set(payload);
    }

    @NotNull
    private static PayloadDto getPayloadDto() {
        PayloadDto payloadDto = JWT_PAYLOAD_CONTEXT.get();
        if (Objects.isNull(payloadDto)) {
            payloadDto = new PayloadDto();
        }
        return payloadDto;
    }

    public static HashMap<String, Collection<String>> getHeaders() {
        HashMap<String, Collection<String>> headers= HEADERS.get();
        if (CollUtil.isEmpty(headers)) {
            headers = new HashMap<>(0);
        }
        return headers;
    }

    public static void setHeaders(HashMap<String, Collection<String>> headers) {
        HEADERS.set(headers);
    }

    /**
     * 获取当前请求的用户ID
     *
     * @return
     */
    public static Long getUserId() {
        PayloadDto payloadDto = JWT_PAYLOAD_CONTEXT.get();
        if (null != payloadDto) {
            if (null != payloadDto.getUserId()) {
                return Long.valueOf(payloadDto.getUserId());
            }
        }
        return 0L;
    }



    /**
     * 获取当前请求拥有的角色编码列表
     *
     * @return
     */
    public static List<String> getRoleCodes() {
        PayloadDto payloadDto = JWT_PAYLOAD_CONTEXT.get();
        if (payloadDto != null) {
            return JWT_PAYLOAD_CONTEXT.get().getAuthorities();
        }
        return null;
    }

    /**
     * 获取当前强求的客户端ID
     *
     * @return
     */
    public static String getClientCode() {
        PayloadDto payloadDto = JWT_PAYLOAD_CONTEXT.get();
        if (payloadDto != null) {
            return JWT_PAYLOAD_CONTEXT.get().getClientId();
        }
        return null;
    }

    public static void clear() {
        JWT_PAYLOAD_CONTEXT.remove();
        HEADERS.remove();
    }

    public static List<Long> getOrgIds() {
        PayloadDto payloadDto = JWT_PAYLOAD_CONTEXT.get();
        if (payloadDto != null) {
            return JWT_PAYLOAD_CONTEXT.get().getOrgIds();
        }
        return null;
    }
}
