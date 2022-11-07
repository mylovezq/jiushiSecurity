package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
public class PayloadDto implements Serializable {
    private static final long serialVersionUID = -274254140748764980L;
    private List<String> aud;
    /**
     * 用户ID
     */
    @JsonProperty(value = "id")
    private String userId;
    private List<String> scope;
    private Boolean active;
    private Long exp;
    private List<String> authorities;
    private String jti;
    private String mobile;
    @JsonProperty(value = "client_id")
    private String clientId;
    @JsonProperty(value = "org_ids")
    private List<Long> orgIds;
}