package cn.wstom.storage.server.pojo;

import cn.wstom.storage.server.model.Node;

import java.util.List;

/**
 * 图片预览
 *
 * @author dws
 */
public class PictureViewList {
    private List<Node> pictureViewList;
    private int index;

    public List<Node> getPictureViewList() {
        return this.pictureViewList;
    }

    public void setPictureViewList(final List<Node> pictureViewList) {
        this.pictureViewList = pictureViewList;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }
}
