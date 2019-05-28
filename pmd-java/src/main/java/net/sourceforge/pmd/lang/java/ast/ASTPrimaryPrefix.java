/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
/* Generated By:JJTree: Do not edit this line. ASTPrimaryPrefix.java */

package net.sourceforge.pmd.lang.java.ast;

import net.sourceforge.pmd.annotation.InternalApi;

public class ASTPrimaryPrefix extends AbstractJavaTypeNode {

    private boolean usesThisModifier;
    private boolean usesSuperModifier;

    @InternalApi
    @Deprecated
    public ASTPrimaryPrefix(int id) {
        super(id);
    }

    @InternalApi
    @Deprecated
    public ASTPrimaryPrefix(JavaParser p, int id) {
        super(p, id);
    }

    public void setUsesThisModifier() {
        usesThisModifier = true;
    }

    public boolean usesThisModifier() {
        return this.usesThisModifier;
    }

    public void setUsesSuperModifier() {
        usesSuperModifier = true;
    }

    public boolean usesSuperModifier() {
        return this.usesSuperModifier;
    }

    /**
     * Accept the visitor. *
     */
    @Override
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
