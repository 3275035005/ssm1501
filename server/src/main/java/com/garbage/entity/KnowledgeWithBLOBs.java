package com.garbage.entity;

import java.io.Serializable;

/**
 * knowledge
 * @author 
 */
public class KnowledgeWithBLOBs extends Knowledge implements Serializable {
    /**
     * 正文
     */
    private String content;

    /**
     * 原理
     */
    private String content1;

    /**
     * 技巧
     */
    private String content2;

    /**
     * 意义
     */
    private String content3;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }
}