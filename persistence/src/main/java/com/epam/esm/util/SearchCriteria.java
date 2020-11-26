package com.epam.esm.util;

/**
 * Class which stores parameters for searching {@link com.epam.esm.entity.GiftCertificate} objects.
 */
public class SearchCriteria {
    /** A name of tag. */
    private String tagNames;
    /** A name of {@link com.epam.esm.entity.GiftCertificate} object. */
    private String name;
    /** A description. */
    private String description;
    /** A parameter sorted by and order. */
    private String sort;

    /**
     * Constructor with all fields.
     *
     * @param tagNames     the name of tag
     * @param name        the name of {@link com.epam.esm.entity.GiftCertificate} object
     * @param description the description
     * @param sort        the parameter sorted by and order
     */
    public SearchCriteria(String tagNames, String name, String description, String sort) {
        this.tagNames = tagNames;
        this.name = name;
        this.description = description;
        this.sort = sort;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
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
