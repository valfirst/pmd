/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.rule.performance;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTAssignableExpr.ASTNamedReferenceExpr;
import net.sourceforge.pmd.lang.java.ast.ASTAssignableExpr.AccessType;
import net.sourceforge.pmd.lang.java.ast.ASTAssignmentExpression;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTForStatement;
import net.sourceforge.pmd.lang.java.ast.ASTForeachStatement;
import net.sourceforge.pmd.lang.java.ast.ASTFormalParameter;
import net.sourceforge.pmd.lang.java.ast.ASTInfixExpression;
import net.sourceforge.pmd.lang.java.ast.ASTLoopStatement;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.ast.BinaryOp;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRulechainRule;
import net.sourceforge.pmd.lang.java.symbols.JVariableSymbol;
import net.sourceforge.pmd.lang.java.types.TypeTestUtil;

public class UseStringBufferForStringAppendsRule extends AbstractJavaRulechainRule {

    public UseStringBufferForStringAppendsRule() {
        super(ASTVariableDeclaratorId.class);
    }

    /**
     * This method is used to check whether user appends string directly instead of using StringBuffer or StringBuilder
     * @param node This is the expression of part of java code to be checked.
     * @param data This is the data to return.
     * @return Object This returns the data passed in. If violation happens, violation is added to data.
     */
    @Override
    public Object visit(ASTVariableDeclaratorId node, Object data) {
        if (!TypeTestUtil.isA(String.class, node) || node.hasArrayType()
                || node.getNthParent(3) instanceof ASTForStatement
                || node.getNthParent(3) instanceof ASTForeachStatement) {
            return data;
        }

        // Remember how often the variable has been used on the right hand side
        int usageCounter = 0;

        List<ASTNamedReferenceExpr> possibleViolations = new ArrayList<>();

        for (ASTNamedReferenceExpr usage : node.getLocalUsages()) {
            if ((node.getNthParent(2) instanceof ASTFieldDeclaration
                    || node.getParent() instanceof ASTFormalParameter)
                    && isNotWithinLoop(usage)) {
                // ignore if the field or formal parameter is *not* used within loops
                continue;
            }

            if (usage.getParent() instanceof ASTAssignmentExpression) {
                ASTAssignmentExpression assignment = (ASTAssignmentExpression) usage.getParent();
                // it is either a compound (a += x)
                if (assignment.isCompound()) {
                    usageCounter++;
                }

                List<JVariableSymbol> symbolsOnTheRight = assignment.descendants(ASTInfixExpression.class)
                        .filter(e -> e.getOperator() == BinaryOp.ADD)
                        .children(ASTNamedReferenceExpr.class)
                        .toList(ASTNamedReferenceExpr::getReferencedSym);
                // or maybe a append in some way (a = a + x)
                // or a combination (a += a + x)
                usageCounter += symbolsOnTheRight.stream().filter(e -> e.equals(usage.getReferencedSym())).count();
            }

            if (usage.getAccessType() == AccessType.WRITE) {
                if (usageCounter > 0) {
                    if (isWithinLoop(usage)) {
                        // always report appends within a loop
                        addViolation(data, usage);
                    } else {
                        possibleViolations.add(usage);
                    }
                }
            }
        }

        // only report, if it is used more than once
        // then all usage locations are reported
        if (usageCounter > 1) {
            possibleViolations.forEach(v -> addViolation(data, v));
        }

        return data;
    }

    private boolean isNotWithinLoop(Node name) {
        return name.ancestors(ASTLoopStatement.class).isEmpty();
    }

    private boolean isWithinLoop(Node name) {
        return !isNotWithinLoop(name);
    }
}
