package com.epam.esm.util;

/**
 * Class which stores parameters for searching {@link com.epam.esm.entity.GiftCertificate} objects.
 */
public class SearchCriteria {
    /** A name of tag. */
    private String tag;
    /** A name of {@link com.epam.esm.entity.GiftCertificate} object. */
    private String name;
    /** A description. */
    private String description;
    /** A parameter sorted by and order. */
    private String sort = "name_asc";

    /**
     * Constructor with all fields.
     *
     * @param tag     the name of tag
     * @param name        the name of {@link com.epam.esm.entity.GiftCertificate} object
     * @param description the description
     * @param sort        the parameter sorted by and order
     */
    public SearchCriteria(String tag, String name, String description, String sort) {
        this.tag = tag;
        this.name = name;
        this.description = description;
        setSort(sort);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
        if (sort != null && !sort.isEmpty()) {
            this.sort = sort;
        }
    }
}
