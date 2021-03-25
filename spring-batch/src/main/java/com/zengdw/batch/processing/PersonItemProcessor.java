package com.zengdw.batch.processing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 创建一个中间处理器
 * @author zengd
 */
@Slf4j
@Component
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(0, firstName, lastName);

        log.debug("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }
}
