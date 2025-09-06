package com.github.michaelboyles.simpledi.test;

import jakarta.inject.Singleton;

@Singleton
public record Engine(Turbocharger turbocharger) {
}
