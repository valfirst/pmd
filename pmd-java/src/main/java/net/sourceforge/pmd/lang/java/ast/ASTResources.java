/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
/* Generated By:JJTree: Do not edit this line. ASTResources.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */

package net.sourceforge.pmd.lang.java.ast;

import net.sourceforge.pmd.annotation.InternalApi;

public class ASTResources extends AbstractJavaNode {

    @InternalApi
    @Deprecated
    public ASTResources(int id) {
        super(id);
    }

    @InternalApi
    @Deprecated
    public ASTResources(JavaParser p, int id) {
        super(p, id);
    }

    /** Accept the visitor. **/
    @Override
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
/*
 * JavaCC - OriginalChecksum=e83b6cb79b9c5c88242c7dca5255e114 (do not edit this
 * line)
 */
