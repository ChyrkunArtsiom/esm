package com.epam.esm.validator;

import com.epam.esm.exception.SortParamNotValidException;
import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchCriteriaValidatorTest {

    @Test
    void testIsValid() {
        SearchCriteria criteria = new SearchCriteria("tag", "name", "description", "name_asc");
        assertTrue(SearchCriteriaValidator.isValid(criteria));
    }

    @Test
    void testInvalidSortingOrder() {
        SearchCriteria criteria = new SearchCriteria("tag", "name", "description", "wrong_sort");
        SortParamNotValidException thrown = assertThrows(SortParamNotValidException.class,
                () -> SearchCriteriaValidator.isValid(criteria));
        assertEquals(criteria.getSort(), thrown.getValue());
    }
}