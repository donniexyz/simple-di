package com.github.michaelboyles.simpledi;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A bean that was found by annotation processing.
 */
@Data
@Accessors(fluent = true)
class Bean {
    private final List<Dependency> dependencies = new ArrayList<>();
    private final List<InjectMethod> injectMethods = new ArrayList<>();
    private final String name;
    private final TypeElement typeElement;
    private final ExecutableElement constructor;

    public void addDependency(Dependency dependency) {
        dependencies.add(dependency);
    }

    public void addInjectMethod(InjectMethod injectMethod) {
        injectMethods.add(injectMethod);
    }

    /**
     * Get the fully qualified name of the Bean's concrete class.
     */
    public String getFqn() {
        return typeElement.getQualifiedName().toString();
    }

    /**
     * Get the fully qualified name of the Bean's concrete class, and all superclasses and interfaces.
     */
    public Collection<String> getAllFqns() {
        return Stream.concat(
                Stream.of(typeElement),
                Stream.concat(
                    getAllInterfaces().stream(),
                    getAllSuperclasses().stream()
                )
            )
            .map(type -> type.getQualifiedName().toString())
            .toList();
    }

    private Collection<TypeElement> getAllInterfaces() {
        return typeElement.getInterfaces().stream().map(this::getElement).toList();
    }

    private Collection<TypeElement> getAllSuperclasses() {
        List<TypeElement> superClasses = new ArrayList<>();
        TypeElement current = typeElement;
        while (current.getSuperclass().getKind() == TypeKind.DECLARED) {
            current = getElement(current.getSuperclass());
            superClasses.add(current);
        }
        return superClasses;
    }

    private TypeElement getElement(TypeMirror mirror) {
        return (TypeElement) ((DeclaredType) mirror).asElement();
    }

    public List<Bean> getProvidedBeans() {
        return dependencies.stream()
            .filter(ProviderDependency.class::isInstance)
            .map(ProviderDependency.class::cast)
            .map(ProviderDependency::allBeans)
            .flatMap(List::stream)
            .toList();
    }

    /**
     * Generates a stable string representation of the bean's API, used for fingerprinting.
     * This includes the bean's name, constructor, and any inject methods.
     */
    public String getApiSignature() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bean{name='").append(name).append('\'');
        sb.append(", fqn='").append(getFqn()).append('\'');

        String constructorSignature = constructor.getParameters().stream()
                .map(p -> p.asType().toString()).collect(Collectors.joining(","));
        sb.append(", constructor=(").append(constructorSignature).append(")");

        injectMethods.forEach(im -> sb.append(im.getSignature()));

        return sb.append("}").toString();
    }
}
