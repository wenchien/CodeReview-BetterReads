package io.jdevelop.beans;

import java.util.List;

import lombok.Data;

@Data
public class SearchResultBook {
    private String key;
    private String title;
    private List<String> author_name;
    private String cover_i;
    private int first_publish_year;
}
