package cn.wstom.exam.utils;


import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.spire.doc.FileFormat;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @param    templatePath         模板地址
 * @param    temDir               生成文件路径
 * @param    fileName             生成文件名
 * @param    map                  参数
 *
 * */
public class  WordUtil {
    //文档导出
    public  static   void  exportWord(String templatePath, String filePath, Map<String,Object> map, Configure configure){
        Assert.notNull(templatePath, "模板路径不能为空");
        Assert.notNull(filePath, "导出文件路径不能为空");
        Assert.isTrue(filePath.endsWith(".docx"), "word导出请使用docx格式");
        XWPFTemplate template = XWPFTemplate.compile(templatePath, configure);
        template.render(map);
        try {
            //输出word
            template.writeToFile(filePath);
            template.close();
            XWPFDocument doc = null;
            try {
                doc = new XWPFDocument(new FileInputStream(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String p1="{{@image";
            String p2="{{richText";
            String p3="{{#table";
            String p4="{{otherText";
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            List<XWPFTable> tables = doc.getTables();
            for (XWPFTable table : tables) {
                for (int i = 0; i <table.getNumberOfRows() ; i++) {
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> tableCells = row.getTableCells();
                    for (XWPFTableCell cell : tableCells) {
                        if (KMP(cell.getText(),p2)){
                            exportWord(filePath,filePath,map,configure);
                        }
                        if (KMP(cell.getText(),p1)){
                            exportWord(filePath,filePath,map,configure);
                        }
                    }
                };

            }
            for (XWPFParagraph paragraph : paragraphs) {
                String s = paragraph.getText();
                if (KMP(s,p3)){
                    exportWord(filePath,filePath,map,configure);
                    map.remove("table");
                }
                if (KMP(s,p2)){
                    exportWord(filePath,filePath,map,configure);
                }
                if (KMP(s,p1)){
                    exportWord(filePath,filePath,map,configure);
                }
                if (KMP(s,p4)){
                    exportWord(filePath,filePath,map,configure);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //ascii转换
    public static String asciiToString(int num)
    {
        StringBuilder sbu = new StringBuilder();
        String[] chars = String.valueOf(num).split(",");
        for (String aChar : chars) {
            sbu.append((char) Integer.parseInt(aChar));
        }
        return sbu.toString();
    }
    public static boolean KMP(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int s1len = s1.length;
        int s2len = s2.length;
        int i = 0;
        int j = 0;
        while (i < s1len && j < s2len) {// 不越界
            if (s1[i] == s2[j]) { //匹配成功就进行后移继续匹配
                i++;
                j++;
            } else {//匹配不成功就将i移动到j对应的i的位置，j再从0开始
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == s2len) {
            return true;
        } else {
            return false;
        }
    }

    private static final Pattern p_html = Pattern.compile("<[a-zA-z]{1,9}((?!>).)*>", Pattern.CASE_INSENSITIVE);

    private static final Pattern t_html = Pattern.compile("</[a-zA-z]{1,9}>", Pattern.CASE_INSENSITIVE);

    public static String getTextByHtml(String html) {

        Matcher m_script = p_html.matcher(html);

        html = m_script.replaceAll("");

        Matcher l_script = t_html.matcher(html);

        return l_script.replaceAll("");

    }





   //获取富文本的纯文本
    public static String getHtmlText(String strHtml) {
        //剔出<img>的标签
        return strHtml.replaceAll("<img.*src\\s*=\\s*(.*?)[^>]*?>", "");
    }

   //获取富文本里的图片
    public static List<String> getImageSrc(String htmlStr) {
        List<String> pics = new ArrayList<String>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

   //test
    public static void main(String[] args) {
        //String str = "<ol class=\" list-paddingleft-2\" style=\"list-style-type: decimal;\">\n" +
        //        "    <li>\n" +
        //        "        <p>\n" +
        //        "            public static List&lt;String&gt; getImageSrc(String htmlStr) {<br/> &nbsp; &nbsp;List&lt;String&gt; pics = new ArrayList&lt;String&gt;();<br/> &nbsp; &nbsp;String img = &quot;&quot;;<br/> &nbsp; &nbsp;Pattern p_image;<br/> &nbsp; &nbsp;Matcher m_image;<br/> &nbsp; &nbsp;//String regEx_img = &quot;&lt;img.*src=(.*?)[^&gt;]*?&gt;&quot;; //图片链接地址<br/> &nbsp; &nbsp;String regEx_img = &quot;&lt;img.*src\\\\s*=\\\\s*(.*?)[^&gt;]*?&gt;&quot;;<br/> &nbsp; &nbsp;p_image = Pattern.compile<br/> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;(regEx_img, Pattern.CASE_INSENSITIVE);<br/> &nbsp; &nbsp;m_image = p_image.matcher(htmlStr);<br/> &nbsp; &nbsp;while (m_image.find()) {<br/> &nbsp; &nbsp; &nbsp; &nbsp;// 得到&lt;img /&gt;数据<br/> &nbsp; &nbsp; &nbsp; &nbsp;img = m_image.group();<br/> &nbsp; &nbsp; &nbsp; &nbsp;// 匹配&lt;img&gt;中的src数据<br/> &nbsp; &nbsp; &nbsp; &nbsp;Matcher m = Pattern.compile(&quot;src\\\\s*=\\\\s*\\&quot;?(.*?)(\\&quot;|&gt;|\\\\s+)&quot;).matcher(img);<br/> &nbsp; &nbsp; &nbsp; &nbsp;while (m.find()) {<br/> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;pics.add(m.group(1));<br/> &nbsp; &nbsp; &nbsp; &nbsp;}<br/> &nbsp; &nbsp;}<br/> &nbsp; &nbsp;return pics;}<img src=\"http://localhost:8081/storage/showCondensedPicture?fileId=6a7475b2-57be-437b-ab86-7d0236474e39\" title=\"6a7475b2-57be-437b-ab86-7d0236474e39\" alt=\"6a7475b2-57be-437b-ab86-7d0236474e39\"/>\n" +
        //        "        </p>\n" +
        //        "    </li>\n" +
        //        "    <li>\n" +
        //        "        <p>\n" +
        //        "            <img src=\"http://localhost:8081/storage/showCondensedPicture?fileId=4b6a7dc3-a848-496d-a910-32f879b85cc7\" style=\"\" title=\"4b6a7dc3-a848-496d-a910-32f879b85cc7\"/>\n" +
        //        "        </p>\n" +
        //        "    </li>\n" +
        //        "    <li>\n" +
        //        "        <p>\n" +
        //        "            <img src=\"http://localhost:8081/storage/showCondensedPicture?fileId=4cb930c8-6ad8-48f5-a225-8a209fc69c76\" style=\"\" title=\"4cb930c8-6ad8-48f5-a225-8a209fc69c76\"/>\n" +
        //        "        </p>\n" +
        //        "    </li>\n" +
        //        "    <li>\n" +
        //        "        <p>\n" +
        //        "            <img src=\"http://localhost:8081/storage/showCondensedPicture?fileId=75f72030-757f-4cbb-9a5f-c57129fc15d3\" style=\"\" title=\"75f72030-757f-4cbb-9a5f-c57129fc15d3\"/>\n" +
        //        "        </p>\n" +
        //        "    </li>\n" +
        //        "    <li>\n" +
        //        "        <p>\n" +
        //        "            萨达萨达萨达撒旦撒大撒大撒大撒大撒大撒\n" +
        //        "        </p>\n" +
        //        "    </li>\n" +
        //        "</ol>";
        //
        //String templatePath="D:\\word导出测试\\demo.docx";
        //String content="D:\\word导出测试\\sub.docx";
        //String filePath="D:\\word导出测试\\demo - 副本.docx";
        //Map<String, Object> map = new HashMap<>();
        //List<String> imageSrc = getImageSrc(str);
        //Style style = new Style();
        //style.setFontSize(12);
        //style.setCharacterSpacing(20);
        //map.put("otherText", "<p>sdasdsadadsdsadadsad</p>");
        //map.put("program","{{richText}}\n{{#table}}");
        //String images="";
        //for (int i = 0; i < imageSrc.size(); i++) {
        //    images=images+"{{@image"+i+"}}\n";
        //    map.put("image"+i, Pictures.ofUrl(imageSrc.get(i), PictureType.PNG).size(600, 400).create());
        //}
        ////map.put("program","学生代码：{{richText}}"+"运行截图："+images);
        //map.put("table", Tables.of(new String[][] {
        //        new String[] { "代码:\n{{richText}}" },
        //        new String[] { "运行截图：\n"+images }
        //}).border(TableStyle.BorderStyle.DEFAULT).create());
        //map.put("richText","<p>sdasdsadadsdsadadsad</p>");
        //ConfigureBuilder builder=Configure.builder();
        //builder.bind("richText",new HtmlRenderPolicy());
        //Configure configure = builder.build();
        //exportWord(templatePath,filePath,map,configure);

       // try {
       //    // wordToPdf();
       ////   Word2007ToHtml("D:\\word导出测试","202035020110_20软件工程1班",".docx");
       //     WordToHtml("D:\\word导出测试\\201835020208_20软件工程2班.doc","D:\\word导出测试\\html\\202035020110_20软件工程1班.html");
       //   //  FileUtils.copyFile(new File("D:\\word导出测试\\201835020208_20软件工程2班.doc"),new File("D:\\word导出测试\\html\\201835020208_20软件工程2班.doc"));
       // } catch (Exception e) {
       //     e.printStackTrace();
       // }



    }
   //删除目录
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir
                        (new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        if(dir.delete()) {
            System.out.println("目录已被删除！");
            return true;
        } else {
            System.out.println("目录删除失败！");
            return false;
        }
    }

    /**
     * word转html实现在线预览
     * 转出来效果很好，就是速度太慢了
     * @param wordPath
     * @param htmlPath
     */
    public static Boolean WordToHtml(String wordPath, String htmlPath){
        com.spire.doc.Document doc=new com.spire.doc.Document();
        doc.loadFromFile(wordPath);
        doc.saveToFile(htmlPath, FileFormat.Html);
        doc.dispose();
        return true;
    }




    ///**
    // * 2007版本word转换成html
    // * //速度快，不完美，不好用
    // * @param wordPath word文件路径
    // * @param wordName word文件名称无后缀
    // * @param suffix   word文件后缀
    // * @return
    // * @throws IOException
    // */
    //public static String Word2007ToHtml(String wordPath, String wordName, String suffix)
    //        throws IOException {
    //    ZipSecureFile.setMinInflateRatio(-1.0d);
    //    String htmlPath = wordPath + File.separator + "html"
    //            + File.separator;
    //    String htmlName = wordName + ".html";
    //    String imagePath = htmlPath + "image" + File.separator;
    //    // 判断html文件是否存在
    //    File htmlFile = new File(htmlPath + htmlName);
    //
    //    // word文件
    //    File wordFile = new File(wordPath + File.separator + wordName + suffix);
    //
    //    // 1) 加载word文档生成 XWPFDocument对象
    //    InputStream in = new FileInputStream(wordFile);
    //    //---------------------------------------------------
    //    XWPFDocument document = new XWPFDocument(in);
    //    // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
    //    File imgFolder = new File(imagePath);
    //    //  带图片的word，则将图片转为base64编码，保存在一个页面中
    //
    //    XHTMLOptions options = XHTMLOptions.create().indent(4).setImageManager(new Base64EmbedImgManager());
    //    // 3) 将 XWPFDocument转换成XHTML
    //    // 生成html文件上级文件夹
    //    File folder = new File(htmlPath);
    //    if (!folder.exists()) {
    //        folder.mkdirs();
    //    }
    //    OutputStream out = new FileOutputStream(htmlFile);
    //    XHTMLConverter.getInstance().convert(document, out, options);
    //    return htmlFile.getAbsolutePath();
    //}

    ///**
    // * 2003版本word转换成html
    // * //速度快，还可以
    // * @param wordPath word文件路径
    // * @param wordName word文件名称无后缀
    // * @param suffix   word文件后缀
    // * @return
    // * @throws IOException
    // */
    //public static String Word2003ToHtml(String wordPath, String wordName,String suffix) throws IOException, TransformerException,
    //        ParserConfigurationException {
    //    String htmlPath = wordPath + File.separator + "html"
    //            + File.separator;
    //    String htmlName = wordName + ".html";
    //    final String imagePath = htmlPath + "image" + File.separator;
    //    // 判断html文件是否存在，每次重新生成
    //    File htmlFile = new File(htmlPath + htmlName);
    //    // 原word文档
    //    final String file = wordPath + File.separator + wordName + suffix;
    //    InputStream input = new FileInputStream(new File(file));
    //
    //    //---------------------------------------------------
    //    HWPFDocument wordDocument = new HWPFDocument(input);
    //    WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
    //            DocumentBuilderFactory.newInstance().newDocumentBuilder()
    //                    .newDocument());
    //
    //    wordToHtmlConverter.setPicturesManager((content, pictureType, suggestedName, widthInches, heightInches) -> {
    //        BufferedImage bufferedImage = ImgUtil.toImage(content);
    //        String base64Img = ImgUtil.toBase64(bufferedImage, pictureType.getExtension());
    //        //  带图片的word，则将图片转为base64编码，保存在一个页面中
    //        StringBuilder sb = (new StringBuilder(base64Img.length() + "data:;base64,".length()).append("data:;base64,").append(base64Img));
    //        return sb.toString();
    //    });
    //
    //    // 解析word文档
    //    wordToHtmlConverter.processDocument(wordDocument);
    //    Document htmlDocument = wordToHtmlConverter.getDocument();
    //    // 生成html文件上级文件夹
    //    File folder = new File(htmlPath);
    //    if (!folder.exists()) {
    //        folder.mkdirs();
    //    }
    //
    //    // 生成html文件地址
    //    OutputStream outStream = new FileOutputStream(htmlFile);
    //    DOMSource domSource = new DOMSource(htmlDocument);
    //    StreamResult streamResult = new StreamResult(outStream);
    //    TransformerFactory factory = TransformerFactory.newInstance();
    //    Transformer serializer = factory.newTransformer();
    //    serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
    //    serializer.setOutputProperty(OutputKeys.INDENT, "yes");
    //    serializer.setOutputProperty(OutputKeys.METHOD, "html");
    //    serializer.transform(domSource, streamResult);
    //    outStream.close();
    //    return htmlFile.getAbsolutePath();
    //}


    ///**
    // * word 转pdf  .doc后缀无法使用
    // * @param wordPath 文档路径
    // * @param PdfPath   转后pdf路径
    // */
    //public static void wordToPdf(String wordPath,String PdfPath){
    //    try {
    //       //读取word文档
    //        XWPFDocument document = null;
    //        try (InputStream in = Files.newInputStream(Paths.get(wordPath))) {
    //            document = new XWPFDocument(in);
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //       //将word转成pdf
    //        PdfOptions options = PdfOptions.create();
    //        try (OutputStream outPDF = Files.newOutputStream(Paths.get(PdfPath))) {
    //            PdfConverter.getInstance().convert(document, outPDF, options);
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}



}








