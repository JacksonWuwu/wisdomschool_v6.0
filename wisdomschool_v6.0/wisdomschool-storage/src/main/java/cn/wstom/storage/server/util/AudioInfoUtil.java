package cn.wstom.storage.server.util;



import cn.wstom.storage.server.enumeration.Constants;
import cn.wstom.storage.server.model.Node;
import cn.wstom.storage.server.pojo.AudioInfo;
import cn.wstom.storage.server.pojo.AudioInfoList;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AudioInfoUtil {
    private static String fileBlocks;
    private static final String ERROR_ARTIST = "\u7fa4\u661f";
    private static final String DEFAULT_LRC = "css/audio_default.lrc";
    private static final String DEFAULT_COVER = "css/audio_default.png";

    public AudioInfoUtil() {
        AudioInfoUtil.fileBlocks = ConfigureReader.instance().getFileBlockPath();
    }

    public static AudioInfoList transformToAudioInfoList(final List<Node> nodes, final String fileId) {
        final AudioInfoList ail = new AudioInfoList();
        final List<AudioInfo> as = new ArrayList<>();
        int index = 0;
        for (final Node n : nodes) {
            final String suffix = n.getFileName().substring(n.getFileName().lastIndexOf(Constants.SEPARATOR_DOT) + 1);
            if (suffix.equalsIgnoreCase("mp3") || suffix.equalsIgnoreCase("ogg") || suffix.equalsIgnoreCase("wav")) {
                final AudioInfo ai = new AudioInfo();
                ai.setUrl("resourceController/getResource.do?fid=" + n.getFileId());
                System.out.println(ai.getUrl());
                ai.setLrc(DEFAULT_LRC);
                ai.setArtist(ERROR_ARTIST);
                ai.setCover(DEFAULT_COVER);
                getAudioArtistAndName(ai, n);
                getLrcAndCover(ai, n, nodes);
                as.add(ai);
                if (!fileId.equals(n.getFileId())) {
                    continue;
                }
                index = as.size() - 1;
            }
        }
        ail.setAs(as);
        ail.setIndex(index);
        return ail;
    }

    private static void getAudioArtistAndName(final AudioInfo ai, final Node n) {
        final File f = new File(AudioInfoUtil.fileBlocks, n.getFilePath());
        ai.setName(getFileName(n.getFileName()).trim());
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(f, "r");
            final byte[] buf = new byte[128];
            raf.seek(raf.length() - 128L);
            raf.read(buf);
            if ("TAG".equalsIgnoreCase(new String(buf, 0, 3))) {
                final String artist = transformCharsetEncoding(buf, 33, 30);
                if (artist.length() > 0) {
                    ai.setArtist(artist);
                }
            }
            final byte[] buf2 = new byte[10];
            raf.seek(0L);
            raf.read(buf2);
            if ("ID3".equalsIgnoreCase(new String(buf2, 0, 3))) {
                final int length = (buf2[6] & 0x7F) * 2097152 + (buf2[7] & 0x7F) * 16384 + (buf2[8] & 0x7F) * 128
                        + (buf2[9] & 0x7F);
                final byte[] buf3 = new byte[length];
                raf.seek(10L);
                raf.read(buf3);
                int flength;
                for (int count = 0; count < length - 1; count = count + 10 + flength) {
                    final String ftitle = new String(buf3, count, 4);
                    flength = buf3[count + 4] * 16777216 + buf3[count + 5] * 65536 + buf3[count + 6] * 256
                            + buf3[count + 7];
                    if ("TPE1".equalsIgnoreCase(ftitle) && flength != 0) {
                        final String artist2 = transformCharsetEncoding(buf3, count + 11, flength - 1);
                        if (artist2.length() > 0) {
                            ai.setArtist(artist2);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void getLrcAndCover(final AudioInfo ai, final Node n, final List<Node> ns) {
        for (final Node e : ns) {
            final String suffix = e.getFileName().substring(e.getFileName().lastIndexOf(Constants.SEPARATOR_DOT) + 1);
            final String nName = getFileName(n.getFileName());
            if (getFileName(e.getFileName()).equals(nName) && "lrc".equalsIgnoreCase(suffix)) {
                ai.setLrc("fileblocks/" + e.getFilePath());
            }
            if ((getFileName(e.getFileName()).equals(nName)
                    || ai.getArtist().equals(getFileName(e.getFileName())))
                    && ("jpg".equals(suffix) || "jpeg".equals(suffix) || "gif".equals(suffix) || "bmp".equals(suffix)
                    || "png".equals(suffix))) {
                ai.setCover("fileblocks/" + e.getFilePath());
            }
        }
    }

    private static String getFileName(final String originName) {
        return (originName.contains(Constants.SEPARATOR_DOT)) ? originName.substring(0, originName.indexOf(Constants.SEPARATOR_DOT)) : originName;
    }

    private static String transformCharsetEncoding(final byte[] buf, final int offset, final int length) {
        try {
            String s = new String(buf, offset, length, StandardCharsets.UTF_8);
            if (s.length() > 0) {
                if (s.equals(new String(s.getBytes(CharsetKit.GBK), CharsetKit.GBK))) {
                    return s;
                }
                s = new String(buf, offset, length, CharsetKit.GBK);
                if (s.equals(new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8))) {
                    return s;
                }
                s = new String(buf, offset, length, StandardCharsets.ISO_8859_1);
                if (s.equals(new String(s.getBytes(CharsetKit.GBK), CharsetKit.GBK))) {
                    return s;
                }
            }
        } catch (UnsupportedEncodingException ex) {
        }
        return "";
    }
}
