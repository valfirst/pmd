/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.ast;

import net.sourceforge.pmd.annotation.Experimental;

/**
 * A guarded pattern (JDK17 Preview). This can be found
 * in {@link ASTSwitchLabel}s.
 *
 * <pre class="grammar">
 *
 * GuardedPattern ::= {@linkplain ASTPattern Pattern} "&amp;&amp;" {@linkplain ASTConditionalAndExpression ConditionalAndExpression}
 *
 * </pre>
 *
 * @see <a href="https://openjdk.java.net/jeps/406">JEP 406: Pattern Matching for switch (Preview)</a>
 *
 * @deprecated This is not used with java 19 preview anymore. Only valid for java 18 preview.
*/
@Experimental
@Deprecated
public final class ASTGuardedPattern extends AbstractJavaNode implements ASTPattern {

    private int parenDepth;

    ASTGuardedPattern(int id) {
        super(id);
    }

    ASTGuardedPattern(JavaParser p, int id) {
        super(p, id);
    }


    @Override
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public ASTPattern getPattern() {
        return (ASTPattern) getChild(0);
    }

    public JavaNode getGuard() {
        return getChild(1);
    }

    void bumpParenDepth() {
        parenDepth++;
    }

    @Override
    @Experimental
    public int getParenthesisDepth() {
        return parenDepth;
    }
}
