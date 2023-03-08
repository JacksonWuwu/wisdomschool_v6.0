package cn.wstom.storage.server.pojo;

/**
 * 音频信息实体
 *
 * @author dws
 */
public class AudioInfo {

    private String name;
    /**
     * 作者
     */
    private String artist;
    private String url;
    /**
     * 封面
     */
    private String cover;
    /**
     * 字幕
     */
    private String lrc;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(final String cover) {
        this.cover = cover;
    }

    public String getLrc() {
        return this.lrc;
    }

    public void setLrc(final String lrc) {
        this.lrc = lrc;
    }
}
