package cn.wstom.storage.server.pojo;


import java.util.List;

/**
 * @author dws
 */
public class AudioInfoList {
    private List<AudioInfo> as;
    private int index;

    public List<AudioInfo> getAs() {
        return this.as;
    }

    public void setAs(final List<AudioInfo> as) {
        this.as = as;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }
}
