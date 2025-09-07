package com.github.michaelboyles.simpledi;

import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A method found via annotation processing which was annotated with {@link jakarta.inject.Inject}.
 */
record InjectMethod(ExecutableElement element, List<Dependency> dependencies) {

    public String getSignature() {
        String params = dependencies.stream().map(Dependency::getTypeSignature).collect(Collectors.joining(","));
        return ", injectMethod=" + element.getSimpleName() + "(" + params + ")";
    }
}
