package com.epam.esm.sort;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public class CertificateDateExpression implements ExpressionTemplate {
    @Override
    public Expression buildExpression(Root root) {
        return root.get("createDate");
    }
}
