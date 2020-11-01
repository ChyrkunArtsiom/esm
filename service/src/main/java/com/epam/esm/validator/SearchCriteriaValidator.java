package com.epam.esm.validator;

import com.epam.esm.exception.SortParamNotValidException;
import com.epam.esm.dao.util.SearchCriteria;

public class SearchCriteriaValidator {
    public static boolean isValid(SearchCriteria criteria) {
        return isSortValid(criteria.getSort());
    }


    private static boolean isSortValid(String sort) {
        if (sort.matches("^(date|name)_(asc|desc)$")) {
            return true;
        } else {
            throw new SortParamNotValidException(sort);
        }
    }
}
