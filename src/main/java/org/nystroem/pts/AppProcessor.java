package org.nystroem.pts;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import spoon.processing.AbstractProcessor;
import spoon.processing.Property;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;

public class AppProcessor extends AbstractProcessor<CtClass<?>> {

    @Property
    String partitionierung;

    @Override public void process(CtClass<?> element) {

        InheritanceType type = null;
        switch (partitionierung.toUpperCase()) {
            case "SINGLE_TABLE":
            type = InheritanceType.SINGLE_TABLE;
                break;
            case "JOINED":
                type = InheritanceType.JOINED;
                break;
            case "TABLE_PER_CLASS":
                type = InheritanceType.TABLE_PER_CLASS;
                break;
            default:
                break;
        }

        if (element.getSimpleName().toUpperCase().equalsIgnoreCase("MOVIE") && type != null) {
            // Creates annotation
            // Creates annotation - @JoinColumn(name="...",nullable=true/false)
            final CtAnnotation relation = getFactory().Core().createAnnotation();
            CtTypeReference<Object> refJoin = getFactory().Core().createTypeReference();
            refJoin.setSimpleName(Inheritance.class.getSimpleName());
            CtPackageReference refJoinPackage = getFactory().Core().createPackageReference();
            refJoinPackage.setSimpleName("javax.persistence");
            refJoin.setPackage(refJoinPackage);
            relation.setAnnotationType(refJoin);
                // Fill fields
                Map<String, Object> elementValues = new HashMap<String, Object>();
                elementValues.put("strategy", type);
                relation.setElementValues(elementValues);

            element.addAnnotation(relation);
        }
        /* if (element.getSimpleName().equalsIgnoreCase("MovieCharacter")) {
            // Creates field.
            final CtTypeReference<Movie> movieRef = getFactory().Code().createCtTypeReference(Movie.class);
            final CtField<Movie> fldmovie = getFactory().Core().<Movie>createField();
            // Creates annotation - @ManyToOne
            final CtAnnotation relationship = getFactory().Core().createAnnotation();
            CtTypeReference<Object> ref = getFactory().Core().createTypeReference();
            ref.setSimpleName(ManyToOne.class.getSimpleName());
            CtPackageReference refPackage = getFactory().Core().createPackageReference();
            refPackage.setSimpleName("javax.persistence");
            ref.setPackage(refPackage);
            relationship.setAnnotationType(ref);
            // Creates annotation - @JoinColumn(name="...",nullable=true/false)
            final CtAnnotation relation = getFactory().Core().createAnnotation();
            CtTypeReference<Object> refJoin = getFactory().Core().createTypeReference();
            refJoin.setSimpleName(JoinColumn.class.getSimpleName());
            CtPackageReference refJoinPackage = getFactory().Core().createPackageReference();
            refJoinPackage.setSimpleName("javax.persistence");
            refJoin.setPackage(refJoinPackage);
            relation.setAnnotationType(refJoin);
                // Fill fields
                Map<String, Object> elementValues = new HashMap<String, Object>();
                elementValues.put("name", MovieCharacter.col_movID);
                elementValues.put("nullable", false);
                relation.setElementValues(elementValues);

            fldmovie.<CtField>setType(movieRef);
            fldmovie.<CtField>addModifier(ModifierKind.PRIVATE);
            fldmovie.<CtField>addAnnotation(relationship);
            fldmovie.<CtField>addAnnotation(relation);
            fldmovie.setSimpleName("movie");

            element.addField(fldmovie);
        } */
    }
    
}