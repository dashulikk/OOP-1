package oop.lab2.model;

public abstract class Paper {
    protected final String id;
    protected final boolean isMulticolor;
    protected final int size;
    protected final String title;
    protected final boolean isMonthly;
    protected Chars chars;

    Paper(String id, boolean isMulticolor, int size, String title, boolean isMonthly) {
        this.id = id;
        this.isMulticolor = isMulticolor;
        this.size = size;
        this.title = title;
        this.isMonthly = isMonthly;
    }

    protected void setChars(Chars chars) {
        this.chars = chars;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName().substring(this.getClass().getCanonicalName().lastIndexOf('.') + 1) + String.format("""
                 {
                  id = "%s",
                  isMulticolor = %b,
                  size = %d,
                  title = "%s",
                  isMonthly = %b,
                  isGlossy = %b,
                  hasSignatureIndex = %b }
                """, id, isMulticolor, size, title, isMonthly, chars.isGlossy(), chars.hasSignatureIndex());
    }
}
