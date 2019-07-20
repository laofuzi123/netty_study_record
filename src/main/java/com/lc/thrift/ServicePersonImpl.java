package com.lc.thrift;

import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;

public class ServicePersonImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("Go Client:"+username);
        Person person = new Person();
        person.setUsername(username);
        person.setAge(10);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Go client params:");
        System.out.printf(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
