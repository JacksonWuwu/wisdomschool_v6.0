package cn.wstom.storage.ui.callback;

import cn.wstom.storage.server.enumeration.LogLevel;

public interface GetServerStatus {
    boolean getPropertiesStatus();

    boolean getServerStatus();

    int getPort();

    int getBufferSize();

    LogLevel getLogLevel();

    String getFileSystemPath();

    boolean getMustLogin();
}
