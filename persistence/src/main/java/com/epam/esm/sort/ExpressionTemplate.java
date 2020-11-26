package com.epam.esm.sort;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public interface ExpressionTemplate {
    Expression buildExpression(Root root);
}
