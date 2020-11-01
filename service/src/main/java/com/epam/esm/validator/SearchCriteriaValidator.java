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
     * @return {@code true} if {@link SearchCriteria} object is valid
     */
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
