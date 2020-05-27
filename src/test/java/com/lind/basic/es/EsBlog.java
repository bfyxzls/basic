package com.lind.basic.es;

import io.searchbox.annotations.JestId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class EsBlog implements Serializable {
    private static final long serialVersionUID = 1L;
    @JestId
    private String id;
    private Long blogId;
    private String summary;
    private String content;
    private String title;
    private int gander;
    private int age;
    private String city;
    private String address;

    public EsBlog(Long blogId, String summary, String content, String title, int gander, int age, String city, String address) {
        this.blogId = blogId;
        this.summary = summary;
        this.content = content;
        this.title = title;
        this.gander = gander;
        this.age = age;
        this.city = city;
        this.address = address;
    }
}
