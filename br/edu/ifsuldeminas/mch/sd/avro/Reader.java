package br.edu.ifsuldeminas.mch.sd.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.reflect.ReflectDatumReader;
import br.edu.ifsuldeminas.mch.sd.pojos.Person;

import java.io.File;

public class Reader {
    public static Person read(String filename) {
        Person person = null;
        try {
            File file = new File(filename);
            ReflectDatumReader<Person> datumReader = new ReflectDatumReader<>(Person.class);
            DataFileReader<Person> dataFileReader = new DataFileReader<>(file, datumReader);
            
            if (dataFileReader.hasNext()) {
                person = dataFileReader.next();
            }
            dataFileReader.close();
            return person;
        } catch (Exception e) {
            System.out.println("Objeto n ̃ao pode ser deserializado.");
            e.printStackTrace();
            return null;
        }
    }
}
