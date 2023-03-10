package cn.wstom.admin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

public class ZipUtils {
    public static void toZip2(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException{

        long start = System.currentTimeMillis();

        ZipOutputStream zos = null ;

        try {

            zos = new ZipOutputStream(out);

            File sourceFile = new File(srcDir);

            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);

            long end = System.currentTimeMillis();

            System.out.println("压缩完成，耗时：" + (end - start) +" ms");

        } catch (Exception e) {

            throw new RuntimeException("zip error from ZipUtils",e);

        }finally{

            if(zos != null){

                try {

                    zos.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }
    }

    /**
     94
     * 递归压缩方法
     95
     * @param sourceFile 源文件
    96
     * @param zos        zip输出流
    97
     * @param name       压缩后的名称
    98
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
    99
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
    100
     * @throws Exception
    101
     */

    private static void compress(File sourceFile, ZipOutputStream zos, String name,

                                 boolean KeepDirStructure) throws Exception{

        byte[] buf = new byte[BUFFER_SIZE];

        if(sourceFile.isFile()){

            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字

            zos.putNextEntry(new ZipEntry(name));

            // copy文件到zip输出流中

            int len;

            FileInputStream in = new FileInputStream(sourceFile);

            while ((len = in.read(buf)) != -1){

                zos.write(buf, 0, len);

            }

            // Complete the entry

            zos.closeEntry();

            in.close();

        } else {

            File[] listFiles = sourceFile.listFiles();

            if(listFiles == null || listFiles.length == 0){

                // 需要保留原来的文件结构时,需要对空文件夹进行处理

                if(KeepDirStructure){

                    // 空文件夹的处理

                    zos.putNextEntry(new ZipEntry(name + "/"));

                    // 没有文件，不需要文件的copy

                    zos.closeEntry();

                }



            }else {

                for (File file : listFiles) {

                    // 判断是否需要保留原来的文件结构

                    if (KeepDirStructure) {

                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,

                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了

                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);

                    } else {

                        compress(file, zos, file.getName(),KeepDirStructure);

                    }



                }

            }

        }

    }

    public static void main(String[] args) {
      //changeFileName("H:\\mystorage\\college\\wstom\\file\\8 - 副本","201835020208_20软件工程2班6666666","201835020208_20软件工程2班");
      String name1 ="201835020208_20软件工程2班6666666";
      String name2 ="201835020208_20软件工程2班6666666";
        String substring = name1.substring(0, 12);
        System.out.println(substring);
    }
    //文件改名
    public static void changeFileName(String path,String oldName,String newName) {
        File file = new File(path);
        String[] files = file.list();
        for(String f :files){
            String fileName=f.substring(0, f.lastIndexOf("."));
            String suffix = f.substring(f.lastIndexOf("."));
            String s1 = fileName.substring(0, 12);
            String s2 = oldName.substring(0, 12);
            if (s1.equals(s2)){
                File targetFile=new File(path+"\\"+fileName+suffix);
                File newFile=new File(path +"\\"+ newName + suffix);
                boolean b = targetFile.renameTo(newFile);
                System.out.println(targetFile);
                System.out.println(b);
                System.out.println(path + newName + suffix);
            }

        }
    }

}
