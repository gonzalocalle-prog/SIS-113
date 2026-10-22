// Bug 7 — it compiles and runs, but the label is never shouted.
//   java Bug7IgnoredReturn.java

public class Bug7IgnoredReturn {

    public static void main(String[] args) {
        Tag tag = new Tag("alert");
        tag.shout();
        System.out.println(tag.getText());   // expected: ALERT!
    }
}

class Tag {

    private String text;

    Tag(String text) {
        this.text = text;
    }

    void shout() {
        text.toUpperCase();
        text.concat("!");
    }

    String getText() {
        return text;
    }
}
