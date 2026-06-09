package br.edu.ifsuldeminas.mch.sd.protobuf;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import br.edu.ifsuldeminas.mch.sd.pojos.Person;

import java.io.FileOutputStream;

public class Writer {
    public static void write(Person person, String filename) {
        Schema<Person> schema = RuntimeSchema.getSchema(Person.class);
        // Buffer to hold serialized data
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            ProtostuffIOUtil.writeTo(fos, person, schema, buffer);
            System.out.println("Objeto serializado com sucesso via Protobuf (Protostuff).");
        } catch (Exception e) {
            System.out.println("Objeto n ̃ao pode ser serializado.");
            e.printStackTrace();
        } finally {
            buffer.clear();
        }
    }
}
