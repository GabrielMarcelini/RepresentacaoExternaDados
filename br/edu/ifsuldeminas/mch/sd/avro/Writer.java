package br.edu.ifsuldeminas.mch.sd.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import br.edu.ifsuldeminas.mch.sd.pojos.Person;

import java.io.File;

public class Writer {
    public static void write(Person person, String filename) {
        try {
            // Avro ReflectData gera o schema automaticamente baseado no POJO
            Schema schema = ReflectData.get().getSchema(Person.class);
            File file = new File(filename);
            
            ReflectDatumWriter<Person> datumWriter = new ReflectDatumWriter<>(Person.class);
            DataFileWriter<Person> dataFileWriter = new DataFileWriter<>(datumWriter);
            
            dataFileWriter.create(schema, file);
            dataFileWriter.append(person);
            dataFileWriter.close();
            
            System.out.println("Objeto serializado com sucesso via Avro.");
        } catch (Exception e) {
            System.out.println("Objeto n ̃ao pode ser serializado.");
            e.printStackTrace();
        }
    }
}
