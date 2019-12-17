package com.majingji.cms.domain;

import java.io.Serializable;

public class ArticleWithBLOBs extends Article implements Serializable {
    private String content;

    private String summary;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

}