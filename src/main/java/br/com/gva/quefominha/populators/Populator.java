package br.com.gva.quefominha.populators;

public interface Populator<SOURCE, TARGET> {
    TARGET populate(SOURCE source, TARGET target);    
}