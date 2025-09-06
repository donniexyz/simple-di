package com.github.michaelboyles.simpledi;

import java.util.List;

public interface ISimpleDIContext {
    Object getBeanByName(String name);

    <T> List<T> getBeanByType(Class<T> tClass);
}
