package br.edu.ifsuldeminas.mch.sd.messagepack;

import org.msgpack.jackson.dataformat.MessagePackFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.edu.ifsuldeminas.mch.sd.pojos.Person;

import java.io.File;

public class Reader {
    public static Person read(String filename) {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
            // Configurar para acessar os campos privados diretamente, já que as classes POJO não possuem Getters/Setters
            objectMapper.setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.FIELD, com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY);
            objectMapper.setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.SETTER, com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE);
            
            return objectMapper.readValue(new File(filename), Person.class);
        } catch (Exception e) {
            System.out.println("Objeto n ̃ao pode ser deserializado.");
            e.printStackTrace();
            return null;
        }
    }
}
