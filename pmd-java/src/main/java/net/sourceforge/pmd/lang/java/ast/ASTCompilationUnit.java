/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
/* Generated By:JJTree: Do not edit this line. ASTCompilationUnit.java */

package net.sourceforge.pmd.lang.java.ast;

import java.util.List;

import net.sourceforge.pmd.annotation.InternalApi;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.ast.RootNode;
import net.sourceforge.pmd.lang.java.typeresolution.ClassTypeResolver;

// FUTURE Change this class to extend from SimpleJavaNode, as TypeNode is not appropriate (unless I'm wrong)
public class ASTCompilationUnit extends AbstractJavaTypeNode implements RootNode {

    private ClassTypeResolver classTypeResolver;
    private List<Comment> comments;

    @InternalApi
    @Deprecated
    public ASTCompilationUnit(int id) {
        super(id);
    }

    @InternalApi
    @Deprecated
    public ASTCompilationUnit(JavaParser p, int id) {
        super(p, id);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Accept the visitor. *
     */
    @Override
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public boolean declarationsAreInDefaultPackage() {
        return getPackageDeclaration() == null;
    }

    public ASTPackageDeclaration getPackageDeclaration() {
        if (jjtGetNumChildren() > 0) {
            Node n = jjtGetChild(0);
            return n instanceof ASTPackageDeclaration ? (ASTPackageDeclaration) n : null;
        }
        return null;
    }

    public ClassTypeResolver getClassTypeResolver() {
        return classTypeResolver;
    }

    public void setClassTypeResolver(ClassTypeResolver classTypeResolver) {
        this.classTypeResolver = classTypeResolver;
    }
}
