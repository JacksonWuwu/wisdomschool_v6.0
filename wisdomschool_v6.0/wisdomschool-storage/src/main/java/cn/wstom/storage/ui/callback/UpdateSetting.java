package cn.wstom.storage.ui.callback;

import cn.wstom.storage.server.pojo.ServerSetting;

public interface UpdateSetting {
    boolean update(final ServerSetting s);
}
