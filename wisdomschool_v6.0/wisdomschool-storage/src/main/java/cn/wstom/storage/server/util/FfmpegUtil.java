package cn.wstom.storage.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class FfmpegUtil {
    private static final String FFMPEG_SYSTEM_PATH = "C:/Users/HP/Desktop/course/ffmpeg/";
    private static final String FILE_SYSTEM_PATH = "C:/Users/HP/Desktop/course/file/fileblocks/";
    private static final String FFMPEGT_PATH = "C:/FFmpeg/ffmpeg-4.2.2-win64-static/bin/ffmpeg.exe";
    private static final String FFPROBE_PATH = "C:/FFmpeg/ffmpeg-4.2.2-win64-static/bin/ffprobe.exe";
    public static final Logger LOGGER = LoggerFactory.getLogger(FfmpegUtil.class);
    public static void transfer(String upFilePath,String codcFilePath) {
        String videoCommend = FFMPEGT_PATH + " -y -i " +FFMPEG_SYSTEM_PATH+ upFilePath + " -c:v libx264 -strict -2 "
                +FILE_SYSTEM_PATH + codcFilePath;

        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(videoCommend);
//            FileUtils.deleteFile(FILE_SYSTEM_PATH+upFilePath);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ( (line = br.readLine()) != null) {
                System.out.println(line);
            }
//            FileUtils.deleteFile(FILE_SYSTEM_PATH+upFilePath);
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
//    public static Map<String, String> getEncodingFormat(String filePath) {
//        String processFLVResult = processFLV(filePath);
//        Map retMap = new HashMap();
//        if (StringUtils.isNotBlank(processFLVResult)) {
//            PatternCompiler compiler = new Perl5Compiler();
//            try {
//                String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
//                String regexVideo = "Video: (.*?), (.*?), (.*?)[,\\s]";
//                String regexAudio = "Audio: (\\w*), (\\d*) Hz";
//
//                Pattern patternDuration = compiler.compile(regexDuration, Perl5Compiler.CASE_INSENSITIVE_MASK);
//                PatternMatcher matcherDuration = new Perl5Matcher();
//                if (matcherDuration.contains(processFLVResult, patternDuration)) {
//                    MatchResult re = matcherDuration.getMatch();
//                    retMap.put("?????????????????????", re.group(1));
//                    retMap.put("????????????", re.group(2));
//                    retMap.put("bitrate ?????? ?????? kb", re.group(3));
//                    System.out.println("?????????????????????  ===" + re.group(1));
//                    System.out.println("????????????        =====" + re.group(2));
//                    System.out.println("bitrate ?????? ?????? kb==" + re.group(3));
//                }
//
//                Pattern patternVideo = compiler.compile(regexVideo, Perl5Compiler.CASE_INSENSITIVE_MASK);
//                PatternMatcher matcherVideo = new Perl5Matcher();
//
//                if (matcherVideo.contains(processFLVResult, patternVideo)) {
//                    MatchResult re = matcherVideo.getMatch();
//                    retMap.put("????????????", re.group(1));
//                    retMap.put("????????????", re.group(2));
//                    retMap.put("?????????", re.group(3));
//                    System.out.println("????????????  ===" + re.group(1));
//                    System.out.println("???????????? ===" + re.group(2));
//                    System.out.println(" ?????????  == =" + re.group(3));
//                }
//
//                Pattern patternAudio = compiler.compile(regexAudio, Perl5Compiler.CASE_INSENSITIVE_MASK);
//                PatternMatcher matcherAudio = new Perl5Matcher();
//
//                if (matcherAudio.contains(processFLVResult, patternAudio)) {
//                    MatchResult re = matcherAudio.getMatch();
//                    retMap.put("????????????", re.group(1));
//                    retMap.put("??????????????????", re.group(2));
//                    System.out.println("????????????   ===" + re.group(1));
//                    System.out.println("??????????????????  ===" + re.group(2));
//                }
//            } catch (MalformedPatternException e) {
//                e.printStackTrace();
//            }
//        }
//        return retMap;
//
//    }


    //  ffmpeg????????????????????????asx???asf???mpg???wmv???3gp???mp4???mov???avi???flv??????
    public static Boolean processFLV(String inputPath) {
        List<String> commend = new ArrayList<String>();

        commend.add(FFPROBE_PATH);//??????????????????????????????????????????
//        commend.add("-print_format json");
        commend.add("-show_format");
//        commend.add("ffmpeg");
        commend.add("-i");
        commend.add(FILE_SYSTEM_PATH+inputPath);

        try {

            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.redirectErrorStream(true);
            Process p = builder.start();

            //1. start
            BufferedReader buf = null; // ??????ffmpeg??????????????????
            String line = null;
            //read the standard output

            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuffer sb = new StringBuffer();
            while ((line = buf.readLine()) != null) {
//                System.out.println(line);
                System.out.println();
                sb.append(line);


                continue;
            }
            int ret = p.waitFor();//?????????????????????????????????????????????????????????????????????????????????????????????
            //1. end
            String lwy=sb.toString();
            boolean status = lwy.contains("Stream #0:0(und): Video: h264");
            if(status){
                System.out.println("??????");
            }else{
                System.out.println("?????????");
            }
            return status;
        } catch (Exception e) {
            LOGGER.error("-- processFLV error, message is {}", e);
            return null;
        }
    }
}


