package BootDigitalLibrary.code.util;

import BootDigitalLibrary.code.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import BootDigitalLibrary.code.services.PersonService;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Person repeatedPerson = personService.showPersonByName(person.getName());
        if (repeatedPerson != null && person.getId() != repeatedPerson.getId()) {
            errors.rejectValue("name", "", "This name is already used");
        }

        repeatedPerson = personService.showPersonByTg(person.getTg());
        if (repeatedPerson != null && person.getId() != repeatedPerson.getId()) {
            errors.rejectValue("tg", "", "This tg is already used");
        }
    }
}
