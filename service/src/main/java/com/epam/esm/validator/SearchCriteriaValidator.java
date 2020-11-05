package com.epam.esm.validator;

import com.epam.esm.exception.SortParamNotValidException;
import com.epam.esm.util.SearchCriteria;

/**
 * Class validator for {@link SearchCriteria} objects.
 */
public class SearchCriteriaValidator {
    /**
     * Validates {@link SearchCriteria} object.
     *
     * @param criteria the {@link SearchCriteria} object to validate
     */
    public static void isValid(SearchCriteria criteria) {
        isSortValid(criteria.getSort());
    }


    private static void isSortValid(String sort) {
        if (!sort.matches("^(date|name)_(asc|desc)$")) {
            throw new SortParamNotValidException(sort);
        }
    }
}
