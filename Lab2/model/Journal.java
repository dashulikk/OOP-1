package oop.lab2.model;

import java.util.Objects;

public class Journal extends Paper {
    public Journal(String id,
                   boolean isMulticolor,
                   int size,
                   String title,
                   boolean isMonthly) {
        super(id, isMulticolor, size, title, isMonthly);
        super.setChars(new Chars(true, true));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Journal o = (Journal) obj;

        if (chars == null) {
            if (o.chars != null) return false;
        } else {
            if (chars.isGlossy() != o.chars.isGlossy() || chars.hasSignatureIndex() != o.chars.hasSignatureIndex())
                return false;
        }

        return Objects.equals(id, o.id) && isMonthly == o.isMonthly &&
                size == o.size && Objects.equals(title, o.title) && isMulticolor == o.isMulticolor;
    }
}
