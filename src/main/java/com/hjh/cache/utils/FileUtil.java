package com.hjh.cache.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 文件工具类
 *
 * @author hongjinhui
 * 2022/3/27
 */

@SuppressWarnings({"resource","unused"})
public class FileUtil {

    /**
     * 获取windows/linux的项目根目录
     * @return
     */
    public static String getConTextPath(){
        String fileUrl = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if("usr".equals(fileUrl.substring(1,4))){
            fileUrl = (fileUrl.substring(0,fileUrl.length()-16));//linux
        }else{
            fileUrl = (fileUrl.substring(1,fileUrl.length()-16));//windows
        }
        return fileUrl;
    }

    /**
     * 字符串转数组
     * @param str 字符串
     * @param splitStr 分隔符
     * @return
     */
    public static String[] StringToArray(String str,String splitStr){
        String[] arrayStr = null;
        if(!"".equals(str) && str != null){
            if(str.indexOf(splitStr)!=-1){
                arrayStr = str.split(splitStr);
            }else{
                arrayStr = new String[1];
                arrayStr[0] = str;
            }
        }
        return arrayStr;
    }

    /**
     * 读取文件
     *
     * @param Path
     * @return
     */
    public static String ReadFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 获取文件夹下所有文件的名称 + 模糊查询（当不需要模糊查询时，queryStr传空或null即可）
     * 1.当路径不存在时，map返回retType值为1
     * 2.当路径为文件路径时，map返回retType值为2，文件名fileName值为文件名
     * 3.当路径下有文件夹时，map返回retType值为3，文件名列表fileNameList，文件夹名列表folderNameList
     * @param folderPath 路径
     * @param queryStr 模糊查询字符串
     * @return
     */
    public static HashMap<String, Object> getFilesName(String folderPath , String queryStr) {
        HashMap<String, Object> map = new HashMap<>();
        List<String> fileNameList = new ArrayList<>();//文件名列表
        List<String> folderNameList = new ArrayList<>();//文件夹名列表
        File f = new File(folderPath);
        if (!f.exists()) { //路径不存在
            map.put("retType", "1");
        }else{
            boolean flag = f.isDirectory();
            if(flag==false){ //路径为文件
                map.put("retType", "2");
                map.put("fileName", f.getName());
            }else{ //路径为文件夹
                map.put("retType", "3");
                File fa[] = f.listFiles();
                queryStr = queryStr==null ? "" : queryStr;//若queryStr传入为null,则替换为空（indexOf匹配值不能为null）
                for (int i = 0; i < fa.length; i++) {
                    File fs = fa[i];
                    if(fs.getName().indexOf(queryStr)!=-1){
                        if (fs.isDirectory()) {
                            folderNameList.add(fs.getName());
                        } else {
                            fileNameList.add(fs.getName());
                        }
                    }
                }
                map.put("fileNameList", fileNameList);
                map.put("folderNameList", folderNameList);
            }
        }
        return map;
    }

    /**
     * 以行为单位读取文件，读取到最后一行
     * @param filePath
     * @return
     */
    public static List<String> readFileContent(String filePath) {
        BufferedReader reader = null;
        List<String> listContent = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                listContent.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return listContent;
    }

    /**
     * 读取指定行数据 ，注意：0为开始行
     * @param filePath
     * @param lineNumber
     * @return
     */
    public static String readLineContent(String filePath,int lineNumber){
        BufferedReader reader = null;
        String lineContent="";
        try {
            reader = new BufferedReader(new FileReader(filePath));
            int line=0;
            while(line<=lineNumber){
                lineContent=reader.readLine();
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return lineContent;
    }

    /**
     * 读取从beginLine到endLine数据（包含beginLine和endLine），注意：0为开始行
     * @param filePath
     * @param beginLineNumber 开始行
     * @param endLineNumber 结束行
     * @return
     */
    public static List<String> readLinesContent(String filePath,int beginLineNumber,int endLineNumber){
        List<String> listContent = new ArrayList<>();
        try{
            int count = 0;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String content = reader.readLine();
            while(content !=null){
                if(count >= beginLineNumber && count <=endLineNumber){
                    listContent.add(content);
                }
                content = reader.readLine();
                count++;
            }
        } catch(Exception e){
        }
        return listContent;
    }

    /**
     * 读取若干文件中所有数据
     * @param listFilePath
     * @return
     */
    public static List<String> readFileContent_list(List<String> listFilePath) {
        List<String> listContent = new ArrayList<>();
        for(String filePath : listFilePath){
            File file = new File(filePath);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    listContent.add(tempString);
                    line++;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
        return listContent;
    }

    /**
     * 文件数据写入（如果文件夹和文件不存在，则先创建，再写入）
     * @param filePath
     * @param content
     * @param flag true:如果文件存在且存在内容，则内容换行追加；false:如果文件存在且存在内容，则内容替换
     */
    public static String fileLinesWrite(String filePath,String content,boolean flag){
        String filedo = "write";
        FileWriter fw = null;
        try {
            File file=new File(filePath);
            //如果文件夹不存在，则创建文件夹
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){//如果文件不存在，则创建文件,写入第一行内容
                file.createNewFile();
                fw = new FileWriter(file);
                filedo = "create";
            }else{//如果文件存在,则追加或替换内容
                fw = new FileWriter(file, flag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filedo;
    }

    /**
     * 写文件
     * @param ins
     * @param out
     */
    public static void writeIntoOut(InputStream ins, OutputStream out) {
        byte[] bb = new byte[10 * 1024];
        try {
            int cnt = ins.read(bb);
            while (cnt > 0) {
                out.write(bb, 0, cnt);
                cnt = ins.read(bb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                ins.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断list中元素是否完全相同（完全相同返回true,否则返回false）
     * @param list
     * @return
     */
    private static boolean hasSame(List<? extends Object> list){
        if(null == list)
            return false;
        return 1 == new HashSet<Object>(list).size();
    }

    /**
     * 判断list中是否有重复元素（无重复返回true,否则返回false）
     * @param list
     * @return
     */
    private static boolean hasSame2(List<? extends Object> list){
        if(null == list)
            return false;
        return list.size() == new HashSet<Object>(list).size();
    }

    /**
     * 增加/减少天数
     * @param date
     * @param num
     * @return
     */
    public static Date DateAddOrSub(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
    //https://www.cnblogs.com/chenhuan001/p/6575053.html
    /**
     * 递归删除文件或者目录
     * @param file_path
     */
    public static void deleteEveryThing(String file_path) {
        try{
            File file=new File(file_path);
            if(!file.exists()){
                return ;
            }
            if(file.isFile()){
                file.delete();
            }else{
                File[] files = file.listFiles();
                for(int i=0;i<files.length;i++){
                    String root=files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径
                    deleteEveryThing(root);
                }
                file.delete();
            }
        } catch(Exception e) {
            System.out.println("删除文件失败");
        }
    }
    /**
     * 创建目录
     * @param dir_path
     */
    public static void mkDir(String dir_path) {
        File myFolderPath = new File(dir_path);
        try {
            if (!myFolderPath.exists()) {
                myFolderPath.mkdir();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    //https://blog.csdn.net/lovoo/article/details/77899627
    /**
     * 判断指定的文件是否存在。
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /* 得到文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 删除文件夹及其下面的子文件夹
     *
     * @param dir
     * @throws IOException
     */
    public static void deleteDir(File dir) throws IOException {
        if (dir.isFile())
            throw new IOException("IOException -> BadInputException: not a directory.");
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteDir(file);
                }
            }
        }
        dir.delete();
    }

    /**
     * 复制文件
     *
     * @param src
     * @param dst
     * @throws Exception
     */
    public static void copy(File src, File dst) throws Exception {
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = null;
            }
        }
    }

    /**
     * 从文件路径中提取目录路径，如果文件路径不含目录返回null。
     *
     * @param filePath 文件路径。
     * @return 目录路径，不以'/'或操作系统的文件分隔符结尾。
     */
    public static String extractDirPath(String filePath) {
        int separatePos = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\')); // 分隔目录和文件名的位置
        return separatePos == -1 ? null : filePath.substring(0, separatePos);
    }

    /**
     * 从文件路径中提取文件名, 如果不含分隔符返回null
     *
     * @param filePath
     * @return 文件名, 如果不含分隔符返回null
     */
    public static String extractFileName(String filePath) {
        int separatePos = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\')); // 分隔目录和文件名的位置
        return separatePos == -1 ? null : filePath.substring(separatePos + 1, filePath.length());
    }


    /**
     * 按路径建立文件，如已有相同路径的文件则不建立。
     *
     * @param filePath 要建立文件的路径。
     * @return 表示此文件的File对象。
     * @throws IOException 如路径是目录或建文件时出错抛异常。
     */
    public static File makeFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile())
            return file;
        if (filePath.endsWith("/") || filePath.endsWith("\\"))
            try {
                throw new IOException(filePath + " is a directory");
            } catch (IOException e) {
                e.printStackTrace();
            }

        String dirPath = extractDirPath(filePath); // 文件所在目录的路径

        if (dirPath != null) { // 如文件所在目录不存在则先建目录
            makeFolder(dirPath);
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // log4j.info("Folder has been created: " + filePath);
        // System.out.println("文件已创建: " + filePath);
        return file;
    }

    /**
     * 新建目录,支持建立多级目录
     *
     * @param folderPath 新建目录的路径字符串
     * @return boolean,如果目录创建成功返回true,否则返回false
     */
    public static boolean makeFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
                // System.out.println("新建目录为：" + folderPath);
                // log4j.info("Create new folder：" + folderPath);
            } else {
                // System.out.println("目录已经存在: " + folderPath);
                // log4j.info("Folder is existed：" + folderPath);
            }
        } catch (Exception e) {
            // System.out.println("新建目录操作出错");
            e.printStackTrace();
            // log4j.error("Create new folder error: " + folderPath);
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param filePathAndName 要删除文件名及路径
     * @return boolean 删除成功返回true,删除失败返回false
     */
    public static boolean deleteFile(String filePathAndName) {
        try {
            File myDelFile = new File(filePathAndName);
            if (myDelFile.exists()) {
                myDelFile.delete();
                // log4j.info("File：" + filePathAndName +
                // " has been deleted!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // log4j.error("Error delete file：" + filePathAndName);
            return false;
        }
        return true;
    }


    /**
     * 得到当前项目路径
     */
    public static String getCurrentProjectPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 清空文件内容
     * @param fileName
     */
    public static void clearFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
