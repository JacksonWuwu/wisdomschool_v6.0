package cn.wstom.storage.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ShowPPTService {

    void getCondensedPPT(HttpServletRequest request, HttpServletResponse response);
}
