package com.epam.esm.sort;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * The interface for selecting parameter {@link com.epam.esm.entity.GiftCertificate} objects will be sorted by.
 */
public interface ExpressionTemplate {
    /**
     * Build expression expression.
     *
     * @param root the {@link Root} object
     * @return the expression
     */
    Expression buildExpression(Root root);
}
