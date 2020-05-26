package com.lind.basic.es;

import io.searchbox.annotations.JestId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@NoArgsConstructor
public class EsBlog implements Serializable {
    private static final long serialVersionUID = 1L;
    @JestId
    private String id;
    private Long blogId;
    private String summary;

    private String content;

    private String title;

    public EsBlog(Long blogId, String summary, String content, String title) {
        this.blogId = blogId;
        this.summary = summary;
        this.content = content;
        this.title = title;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return String.format("User[id=%s, title='%s', summary='%s', content='%s']", id, title, summary, content);
    }
}
