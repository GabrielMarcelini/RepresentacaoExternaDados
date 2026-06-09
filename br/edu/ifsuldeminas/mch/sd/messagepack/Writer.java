package br.edu.ifsuldeminas.mch.sd.messagepack;

import org.msgpack.jackson.dataformat.MessagePackFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.edu.ifsuldeminas.mch.sd.pojos.Person;

import java.io.File;

public class Writer {
    public static void write(Person person, String filename) {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
            objectMapper.writeValue(new File(filename), person);
            System.out.println("Objeto serializado com sucesso via MessagePack.");
        } catch (Exception e) {
            System.out.println("Objeto n ̃ao pode ser serializado.");
            e.printStackTrace();
        }
    }
}
