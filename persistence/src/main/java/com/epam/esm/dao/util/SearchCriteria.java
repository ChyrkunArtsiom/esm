package com.epam.esm.dao.util;

public class SearchCriteria {
    private String tagName;
    private String name;
    private String description;
    private String sort;

    public SearchCriteria(String tagName, String name, String description, String sort) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.sort = sort;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
