package com.epam.esm.validator;

import com.epam.esm.util.SearchCriteria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchCriteriaValidatorTest {

    @Test
    void testIsValid() {
        SearchCriteria criteria = new SearchCriteria("tag", "name", "description", "name_asc");
        assertTrue(SearchCriteriaValidator.isValid(criteria));
    }
}