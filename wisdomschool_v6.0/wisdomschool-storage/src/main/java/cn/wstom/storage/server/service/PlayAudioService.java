package cn.wstom.storage.server.service;

import javax.servlet.http.HttpServletRequest;

public interface PlayAudioService {
    String getAudioInfoListByJson(HttpServletRequest request);
}
