package com.epam.esm.sort;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Indicates that {@link com.epam.esm.entity.GiftCertificate} will be sorted by "name" column.
 */
public class CertificateNameExpression implements ExpressionTemplate {

    @Override
    public Expression buildExpression(Root root) {
        return root.get("name");
    }
}
