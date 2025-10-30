package br.com.fiap.loja.bean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.modelmapper.ModelMapper;

public class ModelMapperProducer {

    @ApplicationScoped
    @Produces
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
