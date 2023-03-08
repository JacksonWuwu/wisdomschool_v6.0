package cn.wstom.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/28
 */
@Getter
@Setter
@ToString
public class TweetInfo {
    private String message;
    private int status;
    private List<TweetEntity> tweet;
}
