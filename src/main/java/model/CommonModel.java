package model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class CommonModel {

	private String action;
	private ArrayList<HashMap<String, Object>> dataList;
    /** PDF用・EXCEL用 **/
    private String fileName;
    private InputStream inputStream;
    private long contentLength;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public ArrayList<HashMap<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<HashMap<String, Object>> dataList) {
        this.dataList = dataList;
    }

}