package br.edu.ifsuldeminas.mch.sd.messagepack;

import br.edu.ifsuldeminas.mch.sd.pojos.Address;
import br.edu.ifsuldeminas.mch.sd.pojos.Person;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Address address = new Address("Rua Jos ́e", 20, "Por do Sol", "37.130-000", "Alfenas", "MG");
        Person emerson = new Person("Emerson Carvalho", "060.793.477-11", new Date(), address);

        String filename = "person.msgpack";

        // Geração do arquivo (Writer)
        Writer.write(emerson, filename);

        // Recuperação do objeto (Reader)
        Person personRead = Reader.read(filename);
        
        // Impressão dos dados recuperados
        if (personRead != null) {
            System.out.println("--- Dados Recuperados ---");
            System.out.println(personRead);
            System.out.println(personRead.getAddress());
        }
    }
}
