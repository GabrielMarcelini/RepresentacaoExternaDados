package br.edu.ifsuldeminas.mch.sd.protobuf;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import br.edu.ifsuldeminas.mch.sd.pojos.Person;

import java.io.FileInputStream;

public class Reader {
    public static Person read(String filename) {
        Schema<Person> schema = RuntimeSchema.getSchema(Person.class);
        Person person = schema.newMessage();

        try (FileInputStream fis = new FileInputStream(filename)) {
            ProtostuffIOUtil.mergeFrom(fis, person, schema);
            return person;
        } catch (Exception e) {
            System.out.println("Objeto n ̃ao pode ser deserializado.");
            e.printStackTrace();
            return null;
        }
    }
}
