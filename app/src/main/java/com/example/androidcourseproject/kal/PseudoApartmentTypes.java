package com.example.androidcourseproject.kal;

public class PseudoApartmentTypes {
    private static PseudoApartmentTypes pseudoApartmentTypes;
    private String[] admissibleTypesOfApartments;

    private PseudoApartmentTypes() {
    }

    public static PseudoApartmentTypes getApartmentsTypes() {
        if (pseudoApartmentTypes == null) {
            pseudoApartmentTypes = new PseudoApartmentTypes();
        }
        return pseudoApartmentTypes;
    }

    public String[] getTypesArray() {
        if (admissibleTypesOfApartments == null) {
            admissibleTypesOfApartments = new String[]{"Люкс", "Полулюкс", "Одноместный", "Двуместный", "Трёхместный", "Четырёхместный", "Пятиместный", "Шестиместный"};
        }
        return admissibleTypesOfApartments;
    }
}
